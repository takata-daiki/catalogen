/*
 * Author: Sean Moore
 * Last Updated: 4/25/13
 * Purpose: Container for all the block objects
 */

// Imports and Packages
package TLTTC;
import java.util.*;

public class BlockList 
{
    private CTCController _controller;
    private Hashtable<Integer, BlockViewModel> _blockList;
    
    BlockList (CTCController controller)
    {
        _controller = controller;
        _blockList = new Hashtable<Integer, BlockViewModel>();
    }
    
    public boolean contains ( Integer bID )
    {
        return _blockList.containsKey(bID);
    }
    
    public BlockViewModel getBlock ( Integer bID )
    {
        if (contains(bID))
        {
            return _blockList.get(bID);
        }
        else
        {
            return null; // Or something else
        }
    }
}