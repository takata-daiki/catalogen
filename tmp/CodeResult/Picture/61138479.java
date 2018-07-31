package utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * A Picture object represents a color 2D image. It maintains a Pixel object for
 * each pixel in the image.
 * 
 * A jpeg image is encoded as a 2 dimensional grid of pixel values. Each pixel
 * has a Red, Green, and Blue component, each of which can have a value ranging
 * from 0 to 255. For example, the color white is represented by the value 255
 * for R, G, and B components of the pixel, and the color black by 0 for all
 * three. All other permutations correspond to color and grey values (when a
 * pixel has the same value for all of its R, B, and G components, it is a
 * greyscale color).
 * 
 * You can think of the image as being stored as a 2-dimensional grid of Pixel
 * objects. However, unlike 2-D array accesses using row and column indices,
 * pixels are accessed using their (x,y) coordinate value in the grid, where
 * (0,0) is the pixel in the upper left corner, and the x and y values are
 * increasing to the right and down:
 * 
 * <pre>
 *                                3     x-axis &lt;br/&gt;
 *              (0,0)  *----------|------------------------&gt;
 *                     |           
 *                     |
 *           y-axis    |          _
 *                   2 -  	    | | Pixel (3, 2)
 *                     |          -
 *                     |
 *                     |
 *                     |
 *                     \/
 * </pre>
 * 
 * For example, to set the pixel in the upper left corner to WHITE I'd do the
 * following:
 * 
 * <pre>
 * Pixel pix = picture.getPixel(0, 0);
 * pix.setRed(255);
 * pix.setBlue(255);
 * pix.setGreen(255);
 * pix.setPixel(0, 0, pix);
 * </pre>
 * 
 * @author Richard Wicentowski and Tia Newhall (2005)
 * @author modified by Keith Vander Linden (July2006)
 * 
 * @author Jonathan Lovelace (modifier)
 * @author Josh Vroom (modifier) (Added dim(), negative(), flipVertical(), and
 *         shift() (horizontally 50%).)
 * 
 * @calvin.class CS108LA
 * @calvin.assignment HoTJ Lab #11
 * @calvin.date 16 November 2006
 * @calvin.semester 06FA
 * 
 */
public final class Picture implements Serializable {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 6855528690949053775L;
	/**
	 * How many pixels are being averaged by blur().
	 */
	private static final int BLUR_PIXEL_COUNT = 9;
	/**
	 * The maximum value for any component.
	 */
	private static final int MAX_INTENSITY = 255;
	/**
	 * If the image is in color.
	 */
	public static final int COLOR = BufferedImage.TYPE_INT_RGB;
	/**
	 * If the image is grayscale.
	 */
	public static final int GRAY = BufferedImage.TYPE_BYTE_GRAY;
	/**
	 * Color by default.
	 */
	private static final int DEFAULT_IMG_TYPE = BufferedImage.TYPE_INT_RGB;
	/**
	 * We dim or brighten by an increment of 10.
	 */
	private static final int DIM_BRIGHTEN_INCR = 10;
	/**
	 * What type is the image?
	 */
	private int imageType;
	/**
	 * The image as a BufferedImage.
	 */
	private transient BufferedImage bufferedImage;
	/**
	 * The raster part of the image (?).
	 */
	private transient WritableRaster raster;

	// private int imageWidth, imageHeight;

	// private Picture copy;

	/**
	 * Construct a new Picture object with no image or type.
	 */
	public Picture() {
		imageType = DEFAULT_IMG_TYPE;
		bufferedImage = null; // NOPMD
		raster = null; // NOPMD
	}

	/**
	 * Construct an image object with the given width and height and the default
	 * type.
	 * 
	 * @param width the width of the image
	 * @param height the height of the image
	 */
	public Picture(final int width, final int height) {
		this(width, height, DEFAULT_IMG_TYPE);
	}

	/**
	 * Construct an image object with the given width, height and type.
	 * 
	 * @param width the width of the image
	 * @param height the height of the image
	 * @param type the type of the image
	 */
	public Picture(final int width, final int height, final int type) {
		bufferedImage = new BufferedImage(width, height, type);
		raster = bufferedImage.getRaster();
		imageType = type;
	}

	/**
	 * Construct an image object with the image loaded from the given filename.
	 * 
	 * @param filename the filename of the image to load
	 * @throws FileNotFoundException on file not found
	 */
	public Picture(final String filename) throws FileNotFoundException {
		this(filename, DEFAULT_IMG_TYPE);
	}

	/**
	 * Construct an image object with the image loaded from the given filename
	 * and with the given type.
	 * 
	 * @param filename the filename to load the image from
	 * @param type
	 *            (COLOR or GRAY)
	 * @throws FileNotFoundException on file not found
	 */
	public Picture(final String filename, final int type) throws FileNotFoundException {
		imageType = (type == COLOR || type == GRAY ? type : DEFAULT_IMG_TYPE);
		load(filename);
	}

	/**
	 * @return the image type
	 */
	public int getImageType() {
		return imageType;
	}

	/**
	 * @return the image's width
	 */
	public int getWidth() {
		return bufferedImage.getWidth();
	}

	/**
	 * @return the image's height
	 */
	public int getHeight() {
		return bufferedImage.getHeight();
	}

	/**
	 * @return the image as a WriteableRaster
	 */
	public WritableRaster getRaster() {
		return raster;
	}

	/**
	 * Load a new image from the given file.
	 * 
	 * @param filename the filename to load an image from
	 * @throws FileNotFoundException when the file doesn't exist
	 */
	public void load(final String filename) throws FileNotFoundException {
		final File file = new File(filename);
		if (!file.exists()) {
			throw new FileNotFoundException("File not found: " + filename);
		}
		final Image image = new ImageIcon(filename).getImage();
		bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				imageType);
		final Graphics pen = bufferedImage.getGraphics();
		pen.drawImage(image, 0, 0, null);
		pen.dispose();

		raster = bufferedImage.getRaster();
	}

	/**
	 * @return a new Picture object equal to this Picture object
	 */
	public Picture copy() {
		final Picture pict = new Picture(getWidth(), getHeight(), getImageType());
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				pict.setPixel(x, y, getPixel(x, y));
			}
		}
		return pict;
	}

	/**
	 * Get the pixel object for the specified x,y location.
	 * 
	 * @param xCoord the X coordinate
	 * @param yCoord the Y coordinate
	 * @return the pixel at that location
	 */
	public Pixel getPixel(final int xCoord, final int yCoord) {
		int[] pixelArray = null;

		pixelArray = raster.getPixel(xCoord, yCoord, pixelArray);

		return new Pixel(pixelArray);
	}

	/**
	 * Set the pixel object at x,y to the given pixel. The x,y coordinate must
	 * be within the bounds of the image itself.
	 * 
	 * @param xCoord
	 *            the X coordinate of the pixel
	 * @param yCoord
	 *            the Y coordinate of the pixel
	 * @param pixel
	 *            the new pixel
	 */
	public void setPixel(final int xCoord, final int yCoord, final Pixel pixel) {
		raster.setPixel(xCoord, yCoord, pixel.getComponents());
	}

	/**
	 * Flip the image horizontally.
	 */
	public void flipHorizontal() {
		for (int x = 0; x < getWidth() / 2; x++) {
			for (int y = 0; y < getHeight(); y++) {
				final Pixel pix1 = getPixel(x, y);
				final Pixel pix2 = getPixel(getWidth() - x - 1, y);
				setPixel(x, y, pix2);
				setPixel(getWidth() - x - 1, y, pix1);
			}
		}
	}

	/**
	 * Brighten the image.
	 */
	public void brighten() {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				final Pixel pix = getPixel(x, y);
				pix.setRed(pix.getRed() + DIM_BRIGHTEN_INCR);
				pix.setGreen(pix.getGreen() + DIM_BRIGHTEN_INCR);
				pix.setBlue(pix.getBlue() + DIM_BRIGHTEN_INCR);
				setPixel(x, y, pix);
			}
		}
	}

	/**
	 * Dim the image.
	 */
	public void dim() {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				final Pixel pix = getPixel(x, y);
				pix.setRed(pix.getRed() - DIM_BRIGHTEN_INCR);
				pix.setGreen(pix.getGreen() - DIM_BRIGHTEN_INCR);
				pix.setBlue(pix.getBlue() - DIM_BRIGHTEN_INCR);
				setPixel(x, y, pix);
			}
		}
	}

	/**
	 * Reverse video.
	 */
	public void negative() {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				final Pixel pix = getPixel(x, y);
				pix.setRed(MAX_INTENSITY - pix.getRed());
				pix.setGreen(MAX_INTENSITY - pix.getGreen());
				pix.setBlue(MAX_INTENSITY - pix.getBlue());
				setPixel(x, y, pix);
			}
		}
	}

	/**
	 * Flip the image vertically.
	 */
	public void flipVertical() {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight() / 2; y++) {
				final Pixel pix1 = getPixel(x, y);
				final Pixel pix2 = getPixel(x, getHeight() - y - 1);
				setPixel(x, y, pix2);
				setPixel(x, getHeight() - y - 1, pix1);
			}
		}
	}

	/**
	 * Shift the image halfway to the right or left.
	 */
	public void shift() {
		final Picture pictureCopy = copy();
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				setPixel(x, y, pictureCopy.getPixel((x + (getWidth() / 2)) % getWidth(),
						y));
			}
		}
	}

	/**
	 * blur the image.
	 */
	public void blur() {
		final Picture pictureCopy = copy(); // NOPMD
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				final Pixel pix10 = pictureCopy.getPixel(MathExtension.modulus((x - 1),
						getWidth()), y);
				final Pixel pix11 = pictureCopy.getPixel(x, y);
				final Pixel pix12 = pictureCopy.getPixel((x + 1) % getWidth(), y);
				final Pixel pix20 = pictureCopy.getPixel(MathExtension.modulus((x - 1),
						getWidth()), (y + 1) % getHeight());
				final Pixel pix21 = pictureCopy.getPixel(x, (y + 1) % getHeight());
				final Pixel pix22 = pictureCopy.getPixel((x + 1) % getWidth(), (y + 1)
						% getHeight());
				pix11.setBlue((pictureCopy.getPixel(
						MathExtension.modulus((x - 1), getWidth()),
						MathExtension.modulus((y - 1), getHeight())).getBlue()
						+ pictureCopy.getPixel(x,
								MathExtension.modulus((y - 1), getHeight())).getBlue()
						+ pictureCopy.getPixel((x + 1) % getWidth(),
								MathExtension.modulus((y - 1), getHeight())).getBlue()
						+ pix10.getBlue()
						+ pix11.getBlue()
						+ pix12.getBlue()
						+ pix20.getBlue() + pix21.getBlue() + pix22.getBlue())
						/ BLUR_PIXEL_COUNT);
				pix11.setGreen((pictureCopy.getPixel(
						MathExtension.modulus((x - 1), getWidth()),
						MathExtension.modulus((y - 1), getHeight())).getGreen()
						+ pictureCopy.getPixel(x,
								MathExtension.modulus((y - 1), getHeight())).getGreen()
						+ pictureCopy.getPixel((x + 1) % getWidth(),
								MathExtension.modulus((y - 1), getHeight())).getGreen()
						+ pix10.getGreen()
						+ pix11.getGreen()
						+ pix12.getGreen()
						+ pix20.getGreen() + pix21.getGreen() + pix22.getGreen())
						/ BLUR_PIXEL_COUNT);
				pix11.setRed((pictureCopy.getPixel(
						MathExtension.modulus((x - 1), getWidth()),
						MathExtension.modulus((y - 1), getHeight())).getRed()
						+ pictureCopy.getPixel(x,
								MathExtension.modulus((y - 1), getHeight())).getRed()
						+ pictureCopy.getPixel((x + 1) % getWidth(),
								MathExtension.modulus((y - 1), getHeight())).getRed()
						+ pix10.getRed()
						+ pix11.getRed()
						+ pix12.getRed()
						+ pix20.getRed() + pix21.getRed() + pix22.getRed())
						/ BLUR_PIXEL_COUNT);
				setPixel(x, y, pix11);
			}
		}
	}
}
