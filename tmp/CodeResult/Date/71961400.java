//
// Date 18/02/13


//This is initialisation of the program: MainClass ClassDecl*
class ProductTestMini
{
//This is the MainClass -> { public static void main( String[] id {satement} }
public static void main(String[] a)

{
//This a print statement with an expression; The application of the method; product is applied to the input data; called product init 
System.out.println(((new Product()).init()).product(10));
}
}
class Product
{
int[] data;



//This returns the length of how many values are in the list
public int getLength()
{
return data.length;
}


public Product init()
{
//This is the data that is in the list; The list consists of 5 values
int index;
data = new int [5];
data[0] = 5;
data[1] = 9;
data[2] = (0-4);
data[3] = 3;
data[4] = 10;
return this;
}


//This method consists of 5 variables; i, n, smallest, mynumber and product. i is the initial value, n is the current value and the product is the product of the initial and the current value.
    public int product(int myno) 
    {

        int i;
        int mynumber; 
	    int n;
        int smallest;
	    int product;
        mynumber = myno; // input a number
	    i = 1;
	    smallest = data[0];
        product = 0;
	
//The method consists of a while loop which checks to see if the value in "i" is smaller than the length. i.e. the list has more than 1 element.
//This method finds the smallest value in the list and multiplies the number entered. 
	while(i < (data.length)) {
		
                n = data[i];
                
		if(n < smallest)
                {
                smallest = n;
                }
                
                else
                {
    
                }
                
                product = smallest * mynumber;

	   
             i = i + 1;
	}
	 return product;
    }
}

