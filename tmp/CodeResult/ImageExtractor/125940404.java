package com.ajaxoid.imageprocessing.server;

import com.ajaxoid.domain.shared.media.MediaInfo;
import com.ajaxoid.metadata.shared.dto.ImageConfiguration;
import org.apache.commons.lang.StringUtils;
import org.im4java.core.IM4JavaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by IntelliJ IDEA.
 * User: andrius
 * Date: 11.9.23
 * Time: 17.59
 */
@Service
public class ImageExtractor {
    protected static final Logger logger = LoggerFactory.getLogger(ImageExtractor.class);
    public static final String FILE_SEPARATOR = "/";
    @Autowired
    ThumbnailGenerator thumbnailGenerator;

    @Value("${file.upload.path}")
    public String UPLOAD_DIRECTORY;

    public MediaInfo createMediaInfo(InputStream stream, String mediaInfoId, String originalFilename, ImageConfiguration sizes) throws IOException, InterruptedException, IM4JavaException {
        // only store 1k files in a directory
        Integer id = Integer.parseInt(mediaInfoId.substring(2));
        String subdir = StringUtils.leftPad("" + (id / 1000), 5, "0") + FILE_SEPARATOR + mediaInfoId + FILE_SEPARATOR;
        String mediaFileDir = UPLOAD_DIRECTORY + FILE_SEPARATOR + subdir + FILE_SEPARATOR;
        Path uploadedImage = copyStream(stream, mediaFileDir);
        MediaInfo info = new MediaInfo();
        info.setDirectory(subdir);
        info.setExInfo(thumbnailGenerator.extractExif(uploadedImage.toString()));
        info.setFilename(uploadedImage.getName(uploadedImage.getNameCount() - 1).toString());
        info.setOrigin(originalFilename);
        info.setSize(Files.size(uploadedImage));
        thumbnailGenerator.storeImage(info, sizes);
        info.setId(mediaInfoId);
        return info;
    }

    private Path copyStream(InputStream stream, String mediaFileDir) throws IOException {
        Path path = Files.createDirectories(Paths.get(mediaFileDir));

        Path uploadedImage = path.resolve("uploaded.jpg");

        if (!Files.exists(path)) {
            throw new IllegalStateException("Upload directory '" + path + "' could not be created");
        }
        Files.copy(stream, uploadedImage);
        stream.close();
        return uploadedImage;
    }
}
