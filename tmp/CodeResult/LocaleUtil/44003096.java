package android.util;

import java.util.Locale;
import libcore.icu.ICU;

public class LocaleUtil
{
    private static String ARAB_SCRIPT_SUBTAG = "Arab";
    private static String HEBR_SCRIPT_SUBTAG = "Hebr";

    private static int getLayoutDirectionFromFirstChar(Locale paramLocale)
    {
        int i = 0;
        switch (Character.getDirectionality(paramLocale.getDisplayName(paramLocale).charAt(0)))
        {
        default:
        case 1:
        case 2:
        }
        while (true)
        {
            return i;
            i = 1;
        }
    }

    public static int getLayoutDirectionFromLocale(Locale paramLocale)
    {
        String str;
        int i;
        if ((paramLocale != null) && (!paramLocale.equals(Locale.ROOT)))
        {
            str = ICU.getScript(ICU.addLikelySubtags(paramLocale.toString()));
            if (str == null)
                i = getLayoutDirectionFromFirstChar(paramLocale);
        }
        while (true)
        {
            return i;
            if ((str.equalsIgnoreCase(ARAB_SCRIPT_SUBTAG)) || (str.equalsIgnoreCase(HEBR_SCRIPT_SUBTAG)))
                i = 1;
            else
                i = 0;
        }
    }
}

/* Location:                     /home/lithium/miui/chameleon/2.11.16/framework_dex2jar.jar
 * Qualified Name:         android.util.LocaleUtil
 * JD-Core Version:        0.6.2
 */