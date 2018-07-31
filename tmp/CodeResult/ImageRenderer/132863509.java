package elements;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Logger;

public class ImageRenderer extends DefaultTableCellRenderer {
	private static final Logger	logger		= Logger.getLogger(ImageRenderer.class);

	java.net.URL				imageURL;
	ImageIcon					img;
	String						imgdefpath	= ResEnums.getImgPath();

	public ImageRenderer() {
		super();
	}

	@Override
	public void setValue(Object value) {
		String path = (!(value instanceof Integer)) ? imgdefpath
				+ (String) value : imgdefpath
				+ (String.valueOf(value));

		// path=path.
		setIcon(new ImageIcon(path));
		// URL url = bla.class.getResource(value.toString());
		// if (url != null) {
		// setIcon(new ImageIcon(url));

		// logger.info(path);

		// }
	}

	// LEGACY-FUN METHOD: WATCH THIS!!!

	public ImageIcon getImageByName(String name) {

		String imgLocation = imgdefpath + name + ".jpg";

		ImageIcon img = new ImageIcon(imgLocation);

		if (new File(imgLocation).isFile())
			return img;

		logger.info(imgLocation);

		File dir = new File(imgdefpath);

		for (String s : dir.list()) {
			if (s.contains("."))
				continue;
			else {
				imgLocation = imgdefpath + s + "\\" + name + ".jpg";
				logger.info(imgLocation);
				img = new ImageIcon(imgLocation);
			}
			if (new File(imgLocation).isFile())
				return img;
			else {
				for (String s1 : new File(imgdefpath + s).list()) {
					if (s1.contains("."))
						continue;
					else {
						imgLocation = imgdefpath + s + "\\" + s1
								+ "\\" + name + ".jpg";
						logger.info(imgLocation);
						img = new ImageIcon(imgLocation);
					}
					if (new File(imgLocation).isFile())
						return img;
					else {
						for (String s2 : new File(imgdefpath + s
								+ "\\" + s1).list()) {
							if (!s2.contains("."))
								continue;
							else {

								imgLocation = (imgdefpath + s + "\\"
										+ s1 + "\\" + s2 + "\\"
										+ name + ".jpg");
								logger.info(imgLocation);
								img = new ImageIcon(imgLocation);
							}
						}
					}
				}

			}
		}
		logger.info(imgLocation);

		return img;

	}

}