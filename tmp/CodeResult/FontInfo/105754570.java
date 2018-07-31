/**
 * swing-revival:
 * Swing Revival Toolkit
 *
 * Copyright (c) 2009 by Alistair A. Israel.
 *
 * This software is made available under the terms of the MIT License.
 * See LICENSE.txt.
 *
 * Created Oct 8, 2009
 */
package swing.revival.assembly.model;

import static java.awt.Font.PLAIN;
import swing.revival.annotations.Font;
import swing.revival.util.StringUtils;

/**
 *
 * @author Alistair A. Israel
 */
public class FontInfo {

    private final String name;

    private final int style;

    private final Integer size;

    /**
     * @param name
     *        the font name
     * @param style
     *        the font style
     * @param size
     *        the font size
     */
    public FontInfo(final String name, final int style, final int size) {
        if (StringUtils.hasLength(name)) {
            this.name = name;
        } else {
            this.name = null;
        }
        this.style = style;
        this.size = Integer.valueOf(size);
    }

    /**
     * @param name
     *        the font name
     * @param style
     *        the font style
     */
    public FontInfo(final String name, final int style) {
        this.name = name;
        this.style = style;
        this.size = null;
    }

    /**
     * @param name
     *        the font name
     */
    public FontInfo(final String name) {
        this(name, PLAIN);
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @return the style
     */
    public final int getStyle() {
        return style;
    }

    /**
     * @return the size
     */
    public final Integer getSize() {
        return size;
    }

    /**
     * @param fontAnnotation
     *        the {@link Font} annotation
     * @return {@link FontInfo}
     */
    public static FontInfo fromAnnotation(final Font fontAnnotation) {
        if (fontAnnotation.size() == -1) {
            return new FontInfo(fontAnnotation.name(), fontAnnotation.style().intValue());
        } else {
            return new FontInfo(fontAnnotation.name(), fontAnnotation.style().intValue(),
                    fontAnnotation.size());
        }
    }

}
