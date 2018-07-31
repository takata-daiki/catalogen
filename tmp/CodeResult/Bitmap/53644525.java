/*
 *  CoreDesigner
 *  Copyright 2007-2009 Christian Lins <christian.lins@web.de>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.coredesigner.io;

import org.coredesigner.Log;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * TODO: Remove usage of this class!
 * @author chris
 */
public class Bitmap
{
  private Color[][] data;
  private long      fileSize = 0;
  private int       height   = 0;
  private int       width    = 0;
  
  public Bitmap(File file)
  {
    loadWindowsBitmap(file);
  }
  
  private void loadWindowsBitmap(File file) // TODO: Abstract this method to accept URLs
  {
    try
    {
      FileInputStream in = new FileInputStream(file);
      
      byte[] buffer4 = new byte[4];
      byte[] buffer2 = new byte[2];
      
      // Read file header (BITMAP_FILE HEADER)
      if(in.read() != 0x42 || in.read() != 0x4D)  // 'BM'
        throw new IOException("Invalid bitmap magic!");
      
      // Read file size
      in.read(buffer4);
      this.fileSize = get32(buffer4, 0);
      
      // Skip 4 reserved bytes
      in.skip(4);
      
      // Read data offset
      in.read(buffer4);           
      long dataOffset = get32(buffer4, 0);
      
      // BITMAP_INFO HEADER
      // Length of info header
      in.read(buffer4);
      
      // Width of the bitmap
      in.read(buffer4);
      this.width = (int)get32(buffer4, 0);
      
      // Height of the bitmap
      in.read(buffer4);
      this.height = (int)get32(buffer4, 0);
      
      // Number of bitplanes
      in.read(buffer2);
      
      // Compression type
      in.read(buffer4);
      
      // Size of the image
      in.read(buffer4);
      
      // Horizontal resolution
      in.read(buffer4);
      
      // Vertical resolution
      in.read(buffer4);
      
      // Number of used colors
      in.read(buffer4);
      
      // Number of the important colors
      in.read(buffer4);
      
      // Create color data...
      // .. but first jump to data offset
      in.close();
      in = new FileInputStream(file);
      in.skip(dataOffset);
      this.data = new Color[(int)width][(int)height];
      for(int y = (int)this.height-1; y >= 0 ; y--)
      {
        for(int x = 0; x < this.width; x++)
        {
          short r, g, b;
          in.read(buffer4, 0, 3); // Read 3 bytes
          b = get8(buffer4, 0);   // BGR-Format
          g = get8(buffer4, 1);
          r = get8(buffer4, 2);
          
          this.data[x][y] = new Color(r, g, b);
        }
      }
      
      Log.get().info("Bitmap loaded(" + this.fileSize + ").");
    }
    catch(IOException e)
    {
      System.err.println(e.getMessage());
    }
  }
  
  public Color get(float x, float y)
  {
    int xi = (int)Math.ceil(x * width);
    int yi = (int)Math.ceil(y * height);
    
    //xi = (int)Math.min(width - 1, xi);
    //xi = (int)Math.max(0, xi);
   
    //yi = (int)Math.min(height - 1, yi);
    //yi = (int)Math.max(0, yi);
    
    return this.data[xi % width][yi % height];
  }
  
  public BufferedImage getImage()
  {
    BufferedImage image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
    
    for(int x = 0; x < width; x++)
    {
      for(int y = 0; y < height; y++)
      {
        image.setRGB(x, y, this.data[x][y].getRGB());
      }
    }
    
    return image;
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  public int getHeight()
  {
    return this.height;
  }
  
  // The following methods are copyied from the JNode source which is under GPL
  // so we have to release this class under GPL as well.
  
  /**
   * Gets an unsigned 8-bit byte from a given offset
   * @param offset
   * @return int
   */
  public static short get8(byte[] data, int offset)
  {
    return (short) (data[offset] & 0xFF);
  }

  /**
   * Sets an unsigned 8-bit byte at a given offset
   * @param offset
   */
  public static void set8(byte[] data, int offset, int value)
  {
    data[offset] = (byte)(value & 0xFF);
  }

  /**
   * Gets an unsigned 16-bit word from a given offset
   * @param offset
   * @return int
   */
  public static int get16(byte[] data, int offset)
  {
    int b1 = data[offset] & 0xFF;
    int b2 = data[offset+1] & 0xFF;
    return (b2 << 8) | b1;
  }

  /**
   * Sets an unsigned 16-bit word at a given offset
   * @param offset
   */
  public static void set16(byte[] data, int offset, int value)
  {
    data[offset]    = (byte)(value & 0xFF);
    data[offset+1]  = (byte)((value >> 8) & 0xFF);
  }

  /**
   * Gets an unsigned 32-bit word from a given offset
   * Can't read from blocks bigger in size than 2GB (32bit signed int)
   *
   * @param offset
   * @return long
   */
  public static long get32(byte[] data, int offset)
  {
    int b1 = data[offset] & 0xFF;
    int b2 = data[offset+1] & 0xFF;
    int b3 = data[offset+2] & 0xFF;
    int b4 = data[offset+3] & 0xFF;
    return (b4 << 24) | (b3 << 16) | (b2 << 8) | b1;
  }

  /**
   * Sets an unsigned 32-bit word at a given offset
   * @param offset
   */
  public static void set32(byte[] data, int offset, long value)
  {
    data[offset]   = (byte)(value & 0xFF);
    data[offset+1] = (byte)((value >> 8) & 0xFF);
    data[offset+2] = (byte)((value >> 16) & 0xFF);
    data[offset+3] = (byte)((value >> 24) & 0xFF);
  }
}
