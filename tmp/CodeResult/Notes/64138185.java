package com.gmail.jafelds.ppedits.enums;

/**
 * @author wolfhome
 *
 */
public enum Notes
{
	NONE ('0', "Nothing"),
	TAP ('1', "Tap Note"),
	HOLD_HEAD ('2', "Hold Head"),
	HOLD_TAIL ('3', "Hold/Roll End"),
	ROLL_HEAD ('4', "Roll Head"),
	MINE ('M', "Mine"),
	LIFT ('L', "Lift Note"),
	FAKE ('F', "Fake Note");
	
	private final char symbol;
	private final String kind;
	
	Notes(char t, String k)
	{
		symbol = t;
		kind = k;
	}
	
	public char getType()
	{
		return symbol;
	}
	public String getKind()
	{
		return kind;
	}
	
	public static Notes getEnum(String k)
	{
		for (Notes n : values())
		{
			if (n.getKind().equals(k))
			{
				return n;
			}
		}
		return null;
	}
	
	public static Notes getEnum(char s)
	{
		for (Notes n : values())
		{
			if (n.getType() == s)
			{
				return n;
			}
		}
		return null;
	}
}
