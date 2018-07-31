package org.bitbucket.servletty.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author oivanov
 */
public final class IOUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(IOUtils.class);

    private IOUtils() {
        // visibility
    }

    public static void close(Closeable stream) {
        if (null == stream)
            return;
        try {
            stream.close();
        } catch (IOException e) {
            if (LOGGER.isTraceEnabled())
                LOGGER.trace("Unable to close stream " + stream, e);
        }
    }

    public static void loadProperties(Properties properties, URL url) {
        InputStream is = null;
        try {
            is = url.openStream();
            properties.load(is);
        } catch (IOException e) {
            LOGGER.error("Unable to load properties from " + url.toString(), e);
        } finally {
            IOUtils.close(is);
        }
    }
}
