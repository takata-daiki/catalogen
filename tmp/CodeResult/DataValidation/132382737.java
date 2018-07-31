import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class DataValidation {

	public String inputDataO;
	public String inputDataC;
	public String inputDatav;
	public String inputDataP;
	public String inputDataW;
	public String inputDataMpg;
	public String inputDataMpy;
	
	public void enterData() throws IOException
	{
		BufferedReader inFromUser =new BufferedReader(new InputStreamReader(System.in));
		Boolean loopP = true;
		//Promt the user to enter the number of people
		System.out.println("Enter the number of people:");
		//Run the loop until the user enters a valid data.
		//If the user enters 0 or does not enter any value the by default it will be considered as 1.
		while (loopP) 
		{
			int checkDataP = 0;
		        try 
		        {
					inputDataP = inFromUser.readLine();
					//if not value is entered
					if (inputDataP.equals(""))
						{
							inputDataP = "1";
							loopP = false;
							System.out.println("Number of people is considered to be 1");
							
						}
					else
						{
						checkDataP = Integer.parseInt(inputDataP);
						if (checkDataP >= 0)
						{
							//if the value entered is 0 then make it 1.
							if (checkDataP == 0)
							{
								inputDataP = "1";
								System.out.println("Number of people is considered to be 1");
							}
						loopP = false;
						}
						else
						{
							//if the value entered is negative number then ask the user to enter a valid value.
							System.out.println("Enter a valid number:");
						}

						}	
				} 
		        catch (Exception e) 
		        {
					System.out.println("Enter a valid number ");
				}
			
		}
		//Run the loop until the user enters a valid weight.
		Boolean loopW = true;
		while (loopW) 
		{
			System.out.println("Enter the weight:");
			inputDataW = inFromUser.readLine();
			loopW = validateData(inputDataW, loopW);
		}
		//Run the loop until the user enters a valid data for miles per gallon(mpg).
		Boolean loopMpg = true;
		while (loopMpg) 
		{
			System.out.println("Enter Miles Per Gallon:");
			inputDataMpg = inFromUser.readLine();
			loopMpg = validateData(inputDataMpg, loopMpg);
		}
		//Run the loop until the user enters a valid data for miles per year(mpy).
		Boolean loopMpy = true;
		while (loopMpy) 
		{
			System.out.println("Enter the Miles Per Year:");
			inputDataMpy = inFromUser.readLine();
			loopMpy = validateData(inputDataMpy, loopMpy);
		}
		
	}

	//this is a common function used to validate the data entered for weight, mpg and mpy.
	public Boolean validateData(String inputDatav, Boolean loopv)
	{
		float checkDataFlt;
		try
		{
			checkDataFlt = Float.parseFloat(inputDatav);
			if (checkDataFlt > 0)
			{
			loopv = false;
			}
		else
			{
			System.out.println("Enter a valid number:");
			}
		}
		catch (Exception e) 
        {
			System.out.println("Enter a valid number ");
		}
		return loopv;
	}
	
}
