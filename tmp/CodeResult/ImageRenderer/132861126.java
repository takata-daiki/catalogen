package elements;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Logger;

import system.ImageMaster;
import system.Pathfinder;

public class ImageRenderer extends DefaultTableCellRenderer {
	private static final Logger	logger		= Logger.getLogger(ImageRenderer.class);

	java.net.URL				imageURL;
	ImageIcon					img;
	static String						imgdefpath	= Pathfinder
													.getDefaultImageLocation();

	private int size;

	public ImageRenderer(int size) {
		super();
		this.size=size;
	}

	@Override
	public void setValue(Object value) {
		String s = (String) value;

		s = s.replace("<Image>", "");

		s = s.replace("</Image>", "");

		String path = imgdefpath + s;
		// path=path.
		logger.trace(path);

		ImageIcon ii =
			ImageMaster.getImageByName(s);
		//= new ImageIcon(path);
		Image i = ii.getImage().getScaledInstance(size, size,
				Image.SCALE_SMOOTH);
		setIcon(new ImageIcon(i));
		// URL url = bla.class.getResource(value.toString());
		// if (url != null) {
		// setIcon(new ImageIcon(url));

		// logger.info(path);

		// }
	}

	// LEGACY-FUN METHOD: WATCH THIS!!!

	

}