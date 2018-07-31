package kid;

/**
 * child is a class to represent his age 
 * @author PavaN
 *
 */
public class child 

{
	/**
	 * test is a method without any parameters
	 */
	public void test()
	{
		/**
		 * Display the string
		 */
		System.out.println("child don't have any activities");
	}
	/**
	 * test is another method having one parameter
	 * @param age
	 * this age parameter represents child's age
	 */
	public void test(int age)
	
	{
		/**
		 * Display the child's age
		 */
		System.out.println("child's age is : "+age);
	}
	
	/**
	 * isYoung is a boolean function, tests this child is Young
	 * @return true if this child age is lessthan 25 false otherwise
	 */
	
	public boolean isYoung()
	{
		int age = 0;
		return age < 25;
	}
	
}


 