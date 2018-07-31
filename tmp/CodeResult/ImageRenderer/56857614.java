package attract.renderers;

import attract.core.Renderer;
import attract.image.ImageToolkit;
import java.awt.image.*;

/**
 *
 * @author Rafa?&#x201A; Hirsz
 */
abstract public class ImageRenderer extends Renderer {
    protected BufferedImage image;
    protected ImageToolkit toolkit;

    public ImageRenderer(BufferedImage img) {
        image = img;
        toolkit = new ImageToolkit(image);
    }

    @Override
    public void toFile(String name) {
        toolkit.savePNG(name);
    }
}
