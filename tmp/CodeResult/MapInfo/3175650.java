/*---------------------------------------------------------------------------*
* Map information
*----------------------------------------------------------------------------*
* 18/02/2009 - Shane Gough
* 
* Initial version
*---------------------------------------------------------------------------*/
package flipsky.games.fsui.games;

/** MapInfo
 *
 */
public class MapInfo {
  //--- Instance variables
  private String m_name;        // Basic name of the map
  private String m_description; // Description of the map
  private int    m_width;       // Width of the map (in cells)
  private int    m_height;      // Height of the map (in cells)
  
  //-------------------------------------------------------------------------
  // Getters/Setters
  //-------------------------------------------------------------------------

  /** Get the name of the map
   */
  public final String getName() {
    return m_name;
    }
  
  /** Get a description of the map
   */
  public final String getDescription() {
    return m_description;
    }
  
  /** Get the width of the map (in cells)
   */
  public final int getWidth() {
    return m_width;
    }
  
  /** Get the height of the map (in cells)
   */
  public final int getHeight() {
    return m_height;
    }
  
  }
