/*
 * Copyright (C) 2010 Parleys.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.parleys.server.domain;

import com.parleys.server.domain.types.HorizontalAlign;
import com.parleys.server.domain.types.VerticalAlign;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Stephan Janssen
 */
public class BackgroundImage implements Serializable, Cloneable {

    private static final long serialVersionUID = -595761814444505190L;

    // This image is used as a background when viewing the related space.
    private String backgroundImageURL;

    // When set to true the linked presentations will reuse this background image.
    private boolean isBackgroundInherited = false;

    // The background image vertical align.
    private String verticalAlign = VerticalAlign.MIDDLE.name();

    // The background image horizontal align.
    private String horizontalAlign = HorizontalAlign.CENTER.name();

    // Need we resize image or display as is?
    private boolean resize = false;

    // Make sence if resize=true. If resizeToFit is true there is no space while displaying image.
    // If maintainAspectRatio=false and resize=true resizing to fit is always performed.
    // If maintainAspectRatio=true and resize=true and resizeToFit=true the part of the image will be behind scene
    private boolean resizeToFit = false;

    // If resize=true should we maintain image aspect ratio?
    private boolean maintainAspectRatio = true;

    /**
     * Default constructor.
     */
    public BackgroundImage() {
    }

    /**
     * @param target Is source to copy fields value from.
     */
    public BackgroundImage(final BackgroundImage target) {
        super();
        this.backgroundImageURL = target.getBackgroundImageURL();
        this.horizontalAlign = target.getHorizontalAlign();
        this.isBackgroundInherited = target.isBackgroundInherited();
        this.maintainAspectRatio = target.isMaintainAspectRatio();
        this.resize = target.isResize();
        this.resizeToFit = target.isResizeToFit();
        this.verticalAlign = target.getVerticalAlign();
    }

    /**
     * @return the backgroundImageURL
     */
    @JsonProperty
    public final String getBackgroundImageURL() {
        return backgroundImageURL;
    }

    /**
     * @param backgroundImageURL the backgroundImageURL to set
     */
    public final void setBackgroundImageURL(final String backgroundImageURL) {
        this.backgroundImageURL = backgroundImageURL;
    }

    /**
     * @return the isBackgroundInherited
     */
    @JsonProperty
    public final boolean isBackgroundInherited() {
        return isBackgroundInherited;
    }

    /**
     * @param isBackgroundInherited the isBackgroundInherited to set
     */
    public final void setBackgroundInherited(final boolean isBackgroundInherited) {
        this.isBackgroundInherited = isBackgroundInherited;
    }

    /**
     * @return the verticalAlign
     */
    @JsonProperty
    public final String getVerticalAlign() {
        return verticalAlign;
    }

    /**
     * @param verticalAlign the verticalAlign to set
     */
    public final void setVerticalAlign(final String verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    /**
     * @return the horizontalAlign
     */
    @JsonProperty
    public final String getHorizontalAlign() {
        return horizontalAlign;
    }

    /**
     * @param horizontalAlign the horizontalAlign to set
     */
    public final void setHorizontalAlign(final String horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }

    /**
     * @return the resize
     */
    @JsonProperty
    public final boolean isResize() {
        return resize;
    }

    /**
     * @param resize the resize to set
     */
    public final void setResize(final boolean resize) {
        this.resize = resize;
    }

    /**
     * @return the resizeToFit
     */
    @JsonProperty
    public final boolean isResizeToFit() {
        return resizeToFit;
    }

    /**
     * @param resizeToFit the resizeToFit to set
     */
    public final void setResizeToFit(final boolean resizeToFit) {
        this.resizeToFit = resizeToFit;
    }

    /**
     * @return the maintainAspectRatio
     */
    @JsonProperty
    public final boolean isMaintainAspectRatio() {
        return maintainAspectRatio;
    }

    /**
     * @param maintainAspectRatio the maintainAspectRatio to set
     */
    public final void setMaintainAspectRatio(final boolean maintainAspectRatio) {
        this.maintainAspectRatio = maintainAspectRatio;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((backgroundImageURL == null) ? 0 : backgroundImageURL.hashCode());
        result = prime * result + ((horizontalAlign == null) ? 0 : horizontalAlign.hashCode());
        result = prime * result + (isBackgroundInherited ? 1231 : 1237);
        result = prime * result + (maintainAspectRatio ? 1231 : 1237);
        result = prime * result + (resize ? 1231 : 1237);
        result = prime * result + (resizeToFit ? 1231 : 1237);
        result = prime * result + ((verticalAlign == null) ? 0 : verticalAlign.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BackgroundImage)) {
            return false;
        }
        final BackgroundImage other = (BackgroundImage) obj;
        if (backgroundImageURL == null) {
            if (other.backgroundImageURL != null) {
                return false;
            }
        } else if (!backgroundImageURL.equals(other.backgroundImageURL)) {
            return false;
        }
        if (horizontalAlign == null) {
            if (other.horizontalAlign != null) {
                return false;
            }
        } else if (!horizontalAlign.equals(other.horizontalAlign)) {
            return false;
        }
        if (isBackgroundInherited != other.isBackgroundInherited) {
            return false;
        }
        if (maintainAspectRatio != other.maintainAspectRatio) {
            return false;
        }
        if (resize != other.resize) {
            return false;
        }
        if (resizeToFit != other.resizeToFit) {
            return false;
        }
        if (verticalAlign == null) {
            if (other.verticalAlign != null) {
                return false;
            }
        } else if (!verticalAlign.equals(other.verticalAlign)) {
            return false;
        }
        return true;
    }
}
