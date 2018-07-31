/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bcit.cst.comp2526.assign4.a00811101;


/**
 * A distance and direction from one location to another location.
 *
 * @author leon
 */
public class Dimension
{
    /**
     * the tangent value of PI/8;
     */
    private final static double TAN_PI_1_8 = Math.tan(Math.PI / 8);

    /**
     * the tangent value of 3/8 PI;
     */
    private final static double TAN_PI_3_8 = Math.tan(Math.PI * 3 / 8);

    /**
     * the tangent value of 5/8 PI;
     */
    private final static double TAN_PI_5_8 = Math.tan(Math.PI * 5 / 8);

    /**
     * the tangent value of 7/8 PI;
     */
    private final static double TAN_PI_7_8 = Math.tan(Math.PI * 7 / 8);

    /**
     * The starting location.
     */
    private final Location from;

    /**
     * The destination.
     */
    private final Location to;

    /**
     * Construct a Dimension between the specified points.
     *
     * @param fromLocation -the location to start from.
     * @param toLocation   - the location to end at
     */
    public Dimension(final Location fromLocation,
                     final Location toLocation)
    {
        if(fromLocation == null)
        {
            throw new IllegalArgumentException("fromLocation cannot be null");
        }
        if(toLocation == null)
        {
            throw new IllegalArgumentException("toLocation cannot be null");
        }
        //if need to check the two Location's rectange are equal.
        from = fromLocation;
        to = toLocation;
    }

    /**
     * Get the distance between the rows.
     *
     * @return The distance between the rows.
     */
    public int getRowDistance()
    {
        return to.getRow() - from.getRow();
    }

    /**
     * Get the distance between the columns.
     *
     * @return The distance between the columns.
     */
    public int getColumnDistance()
    {
        return to.getColumn() - from.getColumn();
    }

    /**
     * Get the absolute distance between the columns.
     *
     * @return The absolute distance between the columns.
     */
    public int getAbsoluteRowDistance()
    {
        return Math.abs(getRowDistance());
    }

    /**
     * Get the absolute distance between the columns.
     *
     * @return The absolute distance between the columns.
     */
    public int getAbsoluteColumnDistance()
    {
        return Math.abs(getColumnDistance());
    }

    /**
     * Get the direction between the locations.
     *
     * @return The direction between the locations, may be null.
     */
    public Direction getStrictDirection()
    {
        final Direction retval;
        final double slope;
        final boolean rowcomp;
        final boolean colcomp;
        rowcomp = to.getRow() > from.getRow();
        colcomp = to.getColumn() > from.getColumn();
        if(from.getRow() == to.getRow() && from.getColumn() == to.getColumn())
        {
            retval = Direction.NONE;
        }
        else
        {
            if(getColumnDistance() == 0)
            {
                retval = (rowcomp) ?
                         Direction.SOUTH :
                         Direction.NORTH;
            }
            else
            {
                slope = (double)getRowDistance() / getColumnDistance();

                if(slope == 0)
                {
                    retval = (colcomp) ?
                             Direction.EAST :
                             Direction.WEST;
                }
                else if(Math.abs(slope - 1) < 0.0000001)
                {
                    retval = (colcomp) ?
                             Direction.SOUTH_EAST :
                             Direction.NORTH_WEST;
                }
                else if(Math.abs(slope + 1) < 0.0000001)
                {
                    retval = (colcomp) ?
                             Direction.NORTH_EAST :
                             Direction.SOUTH_WEST;
                }
                else
                {
                    retval = null;
                }
            }
        }
        return retval;

    }

    /**
     * Get the general(non-cardinal) direction between the locations.
     *
     * @return The general (non-cardinal) direction between the locations, may be null.
     */
    public Direction getGeneralDirection()
    {
        Direction dir = getStrictDirection();
        final boolean colcomp;
        colcomp = to.getColumn() > from.getColumn();
        if(dir == null)
        {
            final double slope;
            slope = (double)getRowDistance() / getColumnDistance();
            if(slope > 0)
            {
                dir = (colcomp) ?
                      Direction.SOUTH_EAST :
                      Direction.NORTH_WEST;
            }
            else
            {
                dir = (colcomp) ?
                      Direction.NORTH_EAST :
                      Direction.SOUTH_WEST;
            }
        }
        return dir;
    }

    /**
     * Get the closest direction between the locations.
     *
     * @return The closest direction between the locations, may be null.
     */
    public Direction getClosestDirection()
    {
        Direction dir = getStrictDirection();

        final double slope;
        final boolean rowcomp;
        final boolean colcomp;
        rowcomp = to.getRow() > from.getRow();
        colcomp = to.getColumn() > from.getColumn();
        slope = (double)getRowDistance() / getColumnDistance();

        if(dir == null && Math.abs(slope) != 2 && Math.abs(slope) != 0.5)
        {

            if(slope >= TAN_PI_7_8 && slope <= TAN_PI_1_8)
            {
                dir = (colcomp) ?
                      Direction.EAST :
                      Direction.WEST;
            }
            else if(slope > TAN_PI_1_8 && slope <= TAN_PI_3_8)
            {
                dir = (colcomp) ?
                      Direction.SOUTH_EAST :
                      Direction.NORTH_WEST;
            }
            else if(slope > TAN_PI_3_8 || slope < TAN_PI_5_8)
            {
                dir = (rowcomp) ?
                      Direction.SOUTH :
                      Direction.NORTH;
            }
            else
            {
                dir = (colcomp) ?
                      Direction.NORTH_EAST :
                      Direction.SOUTH_WEST;
            }
        }
        return dir;
    }

}
