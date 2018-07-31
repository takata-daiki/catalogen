package com.android.internal.telephony.cat;

public enum TextAlignment
{
    private int mValue;

    static
    {
        CENTER = new TextAlignment("CENTER", 1, 1);
        RIGHT = new TextAlignment("RIGHT", 2, 2);
        DEFAULT = new TextAlignment("DEFAULT", 3, 3);
        TextAlignment[] arrayOfTextAlignment = new TextAlignment[4];
        arrayOfTextAlignment[0] = LEFT;
        arrayOfTextAlignment[1] = CENTER;
        arrayOfTextAlignment[2] = RIGHT;
        arrayOfTextAlignment[3] = DEFAULT;
    }

    private TextAlignment(int paramInt)
    {
        this.mValue = paramInt;
    }

    public static TextAlignment fromInt(int paramInt)
    {
        TextAlignment[] arrayOfTextAlignment = values();
        int i = arrayOfTextAlignment.length;
        int j = 0;
        TextAlignment localTextAlignment;
        if (j < i)
        {
            localTextAlignment = arrayOfTextAlignment[j];
            if (localTextAlignment.mValue != paramInt);
        }
        while (true)
        {
            return localTextAlignment;
            j++;
            break;
            localTextAlignment = null;
        }
    }
}

/* Location:                     /home/lithium/miui/chameleon/2.11.16/framework_dex2jar.jar
 * Qualified Name:         com.android.internal.telephony.cat.TextAlignment
 * JD-Core Version:        0.6.2
 */