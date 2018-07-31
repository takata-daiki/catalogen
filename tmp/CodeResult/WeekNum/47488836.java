import java.util.*;
import java.util.Collections;
import java.io.*;

public class GroupCoupler
{
    public ArrayList<String> group;
    public int weeks;

    public GroupCoupler (ArrayList<String> grp)
    {
        group = grp;
    }

    public GroupCoupler (File fl)
    {
        group = new ArrayList<String> ();

        try {
            Scanner sn = new Scanner (fl);

            while (sn.hasNextLine ()) {
                group.add (sn.nextLine ());
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public boolean setWeeks (int wks)
    {
        if (wks < group.size ()) {
            weeks = wks;
            return true;
        } else {
            return false;
        }
    }

    public Roster calculateCouples ()
    {
        Roster roster = new Roster ();
        final int sizeGroupHalf = group.size () / 2;

        for (int weekNum = 1; weekNum <= weeks; weekNum++) {
            Week week = new Week ();
            List<String> grp = rotateGroupTail (weekNum);

            for (int std = 0; std < sizeGroupHalf; std++) {
                week.add (new Couple (grp.get (std), grp.get (grp.size () - 1 - std)));
            }

            roster.add (week);
        }

        return roster;
    }

    private List<String> rotateGroupTail (int num)
    {
        String init = group.get (0);
        List<String> tail = (ArrayList<String>) group.clone();
        tail = tail.subList (1, group.size ());
        Collections.rotate (tail, num);
        tail.add (0, init);
        return tail;
    }
}
