import java.util.Scanner;

public class Even

{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please enter an integer: ");
		int usrInt = scan.nextInt();
		
		if (usrInt%2==0)
		
			System.out.println("Integer is an even number");
		else
			System.out.println("Integer is an odd number");
		
		
		scan.close();
	}
	}