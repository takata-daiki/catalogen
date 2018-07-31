/*
 * @author Bernard Bou
 * Created on 16 mars 2005
 * Filename : Resources.java
 */
package wnsqlbuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Resources
{
    // key-value map

    private static Map<String, String> theResourceMap = new TreeMap<String, String>();

    // tables

    static public String[] theTableKeys = null;

    static public String[] theXTableKeys = null;

    // constraints

    static public final String[] thePKConstraintsKeys = Resources.concat("pk"); //$NON-NLS-1$

    static public final String[] theUConstraintsKeys = Resources.concat("unq"); //$NON-NLS-1$

    static public final String[] theIConstraintsKeys = Resources.concat("index"); //$NON-NLS-1$

    static public final String[] theKConstraintsKeys = Resources.concat(Resources.theUConstraintsKeys, Resources.theIConstraintsKeys);

    static public final String[] theFKConstraintsKeys = Resources.concat("fk"); //$NON-NLS-1$

    static public final String[] theConstraintsKeys = Resources.concat(Resources.thePKConstraintsKeys, Resources.theKConstraintsKeys,
            Resources.theFKConstraintsKeys);

    static public final String[] theNoPKConstraintsKeys = Resources.concat("no-pk"); //$NON-NLS-1$

    static public final String[] theNoUConstraintsKeys = Resources.concat("no-unq"); //$NON-NLS-1$

    static public final String[] theNoIConstraintsKeys = Resources.concat("no-index"); //$NON-NLS-1$

    static public final String[] theNoKConstraintsKeys = Resources.concat(Resources.theNoIConstraintsKeys, Resources.theNoUConstraintsKeys);

    static public final String[] theNoFKConstraintsKeys = Resources.concat("no-fk"); //$NON-NLS-1$

    static public final String[] theNoConstraintsKeys = Resources.concat(Resources.theNoFKConstraintsKeys, Resources.theNoKConstraintsKeys,
            Resources.theNoPKConstraintsKeys);

    // initialize

    static public void addResource(final ResourceBundle thisBundle)
    {
        for (String thisKey : Collections.list(thisBundle.getKeys()))
            Resources.theResourceMap.put(thisKey, thisBundle.getString(thisKey));
    }

    // concat arrays

    static public String[] concat(final String... thisArray)
    {
        final List<String> thisResult = new ArrayList<String>();
        for (final String thisString : thisArray)
        {
            thisResult.add(thisString);
        }
        return thisResult.toArray(new String[] {});
    }

    static public String[] concat(final String[]... theseStringArrays)
    {
        final List<String> thisResult = new ArrayList<String>();
        for (final String[] thisStringArray : theseStringArrays)
        {
            for (final String thisString : thisStringArray)
            {
                thisResult.add(thisString);
            }
        }
        return thisResult.toArray(new String[] {});
    }

    // join

    static public String join(final Collection<String> theseStrings)
    {
        if (theseStrings == null)
            return null;
        final List<String> thisList = new ArrayList<String>(theseStrings);
        Collections.sort(thisList);
        final StringBuffer thisBuffer = new StringBuffer();
        for (final String thisString : thisList)
        {
            thisBuffer.append(thisString);
            thisBuffer.append(' ');
        }
        return thisBuffer.toString();
    }

    // keys

    static public List<String> getKeysWithOp(final String thisString)
    {
        final List<String> thisResult = new ArrayList<String>();
        for (String thisKey : Resources.theResourceMap.keySet())
        {
            final String[] theseFields = thisKey.split("\\."); //$NON-NLS-1$
            final String thisOpKey = theseFields[theseFields.length - 1];
            if (thisOpKey.startsWith(thisString))
            {
                thisResult.add(thisKey);
            }
        }
        return thisResult;
    }

    // values

    static public List<String> getValuesWithOp(final String thisString)
    {
        final List<String> thisResult = new ArrayList<String>();
        for (String thisKey : Resources.theResourceMap.keySet())
        {
            final String[] theseFields = thisKey.split("\\."); //$NON-NLS-1$
            final String thisOpKey = theseFields[theseFields.length - 1];
            if (thisOpKey.startsWith(thisString))
            {
                thisResult.add(Resources.getString(thisKey));
            }
        }
        return thisResult;
    }

    static public List<String> getValuesWithTableKeyAndOp(final String thatTableKey, final String thatOpKey)
    {
        final List<String> thisResult = new ArrayList<String>();
        for (String thisKey : Resources.theResourceMap.keySet())
        {
            final String[] theseFields = thisKey.split("\\."); //$NON-NLS-1$
            final String thisTableKey = theseFields[0];
            final String thisOpKey = theseFields[theseFields.length - 1];
            if (thisTableKey.equals(thatTableKey) && thisOpKey.startsWith(thatOpKey))
            {
                thisResult.add(Resources.getString(thisKey));
            }
        }
        return thisResult;
    }

    static public void dumpAll()
    {
        for (String thisKey : Resources.theResourceMap.keySet())
        {
            final String thisValue = Resources.getString(thisKey);
            Logger.traceexec(thisKey + " = " + thisValue); //$NON-NLS-1$
            if (thisValue.indexOf("!") != -1 || thisValue.indexOf("%") != -1) //$NON-NLS-1$//$NON-NLS-2$
            {
                System.out.println(thisKey + "+" + thisValue);//$NON-NLS-1$
            }
        }
    }

    static public void dumpKeys(String thisRegExp)
    {
        for (String thisKey : Resources.theResourceMap.keySet())
        {
            if (!thisKey.matches(thisRegExp))
                continue;

            final String thisValue = Resources.getString(thisKey);
            Logger.traceexec(thisKey + " = " + thisValue); //$NON-NLS-1$
            if (thisValue.indexOf("!") != -1 || thisValue.indexOf("%") != -1) //$NON-NLS-1$//$NON-NLS-2$
            {
                System.out.println(thisKey + "+" + thisValue);//$NON-NLS-1$
            }
        }
    }

    // tables

    static public List<String> getTables()
    {
        return Resources.getValuesWithOp("table"); //$NON-NLS-1$
    }

    static public List<String> getTables(final String... thoseTableKeys)
    {
        if (thoseTableKeys == null || thoseTableKeys.length == 0)
            return Resources.getTables();

        final List<String> thisResult = new ArrayList<String>();
        for (final String thisKey : Resources.getKeysWithOp("table")) //$NON-NLS-1$
        {
            // add if it matches one of the input table keys
            for (final String thatTableKey : thoseTableKeys)
            {
                final String[] theseFields = thisKey.split("\\."); //$NON-NLS-1$
                final String thisTableKey = theseFields[0];
                if (thisTableKey.equals(thatTableKey))
                {
                    thisResult.add(Resources.getString(thisKey));
                }
            }
        }
        return thisResult;
    }

    // views

    static public List<String> getViews()
    {
        return Resources.getValuesWithOp("view"); //$NON-NLS-1$
    }

    // property factory

    public static Properties makeProps(final String... theseStrings)
    {
        final Properties theseProps = new Properties();
        for (int i = 0; i < theseStrings.length; i += 2)
        {
            final String thisKey = theseStrings[i];
            final String thisValue = theseStrings[i + 1];
            theseProps.put(thisKey, thisValue);
        }
        return theseProps;
    }

    // expand template

    static public String expandTemplate(final String thisKey, final String thisTable)
    {
        final String thisString = Resources.getString(thisKey);
        return Resources.expand(thisString, Resources.makeProps("table", thisTable)); //$NON-NLS-1$
    }

    // get value

    static public String getString(final String thisKey)
    {
        try
        {
            String thisString = Resources.theResourceMap.get(thisKey);
            // System.out.println(thisKey+ ">" + thisString);
            thisString = Resources.expandPercent(thisString);
            // System.out.print("<");
            return thisString;
        }
        catch (final MissingResourceException e)
        {
            System.err.println("NO KEY:"+thisKey); //$NON-NLS-1$
            throw e; //  '!' + thisKey + '!';
        }
        catch (final NullPointerException e)
        {
            System.err.println("NO KEY:"+thisKey); //$NON-NLS-1$
            throw e; //  '!' + thisKey + '!';
        }
    }

    static private String expandPercent(final String thisString)
    {
        final String REGEX = "%[^%]*%"; //$NON-NLS-1$
        final Pattern thePattern = Pattern.compile(REGEX);

        // percents
        int thisPercentCount = 0;
        for (int p = 0; (p = thisString.indexOf('"', p + 1)) != -1;)
        {
            thisPercentCount++;
        }
        if (thisPercentCount % 2 != 0)
            return null;

        // macro map
        final Map<String, String> thisMap = new HashMap<String, String>();
        final Matcher thisMatcher = thePattern.matcher(thisString); // get a matcher object
        while (thisMatcher.find())
        {
            final String thisMatch = thisMatcher.group();
            final String thisKey = thisMatch.substring(1, thisMatch.length() - 1);
            // System.out.println(" *" + thisKey);
            if (!thisMap.containsKey(thisKey))
            {
                final String thisValue = Resources.getString(thisKey);
                thisMap.put(thisKey, thisValue);
            }
        }

        // macro substitution
        String thisResult = thisString;
        for (final String thisKey : thisMap.keySet())
        {
            final String thisValue = thisMap.get(thisKey);
            thisResult = thisResult.replaceAll("%" + thisKey + "%", thisValue); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return thisResult;
    }

    static public String expand(final String thisString, final String... theseStrings)
    {
        return Resources.expand(thisString, Resources.makeProps(theseStrings));
    }

    static public String expand(final String thisString, final Properties theseProps)
    {
        final String REGEX = "\\$[^\\$]+\\$"; //$NON-NLS-1$
        final Pattern thePattern = Pattern.compile(REGEX);

        // match
        String thisResult = thisString;
        final Matcher thisMatcher = thePattern.matcher(thisString); // get a matcher object
        while (thisMatcher.find())
        {
            final String thisMatch = thisMatcher.group();
            final String thisKey = thisMatch.substring(1, thisMatch.length() - 1);
            final String thisValue = theseProps.getProperty(thisKey);
            if (thisValue == null)
            {
                System.err.println("[" + thisMatch + "] -> " + thisValue); //$NON-NLS-1$//$NON-NLS-2$
                throw new NoSuchElementException(thisMatch);
            }
            thisResult = thisResult.replaceAll("\\$" + thisKey + "\\$", thisValue); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return thisResult;
    }
}
