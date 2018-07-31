import java.util.*;

class problem_036
{
	
	public static void main(String[] args)
	{
		ArrayList<Integer> decimalPalindrome = new ArrayList<Integer>();
		int sum = 0;
		for (Integer i = 1; i < 1000000; i++)
			if (isPalindrome(Integer.toString(i)))
				decimalPalindrome.add(i);

		for (int j = 0; j < decimalPalindrome.size()-1; j++)
			if (isPalindrome(dec2bin(decimalPalindrome.get(j))))
				sum += decimalPalindrome.get(j);

		System.out.println(sum);
	}

	public static boolean isPalindrome(String bemenet)
	{
		String sz = bemenet; String w = "";
		for (int i = 0; i < sz.length(); i++)
		{
			 w = sz.substring(i, i + 1) + w;
		}
		
		if (sz.equals(w))
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	public static String dec2bin(int bemenet)
	{
		int base = 2; int maradek = 0;
		int be = bemenet; String kimenet = "";
		while (be > 0)
		{
			maradek = be % base;
			be = be / base;
			kimenet = Integer.toString(maradek) + kimenet;
		}	
		return kimenet;

	}
}