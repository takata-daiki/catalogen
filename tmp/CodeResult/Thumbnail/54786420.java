/*
 * Copyright 2009 Adam Jurzyk <a.jurzyk at gmail.com>.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.ajurzyk.mpdplayer.gui.thumbs;

import java.util.List;
import javax.swing.JButton;
import org.ajurzyk.mpdplayer.datamodel.Artist;
import org.bff.javampd.MPDSong;

/**
 * Abstract class for extending by specific Thumbnail implementation. 
 * @author Adam Jurzyk <a.jurzyk at gmail.com>
 */
abstract class Thumbnail extends JButton
{
  private static final long serialVersionUID = -7013721679882973000L; 
  /**
   * Get artist connected to this thumbnail.
   * @return artist connected to this thumbnail
   */
  public abstract Artist getArtist();

  /**
   * Get thumbnail name.
   * @return thumbnail name
   */
  public abstract String getThumbnailName();

  /**
   * Get list of MPDSong connected with this thumbnail.
   * @return list of MPDSong connected with this thumbnail
   */
  public abstract List<MPDSong> getTracks();

  /**
   * Get prefered file name for this thumbnail.
   * @return file name for this thumbnail
   */
  public abstract String getPreferedImageName();

}
