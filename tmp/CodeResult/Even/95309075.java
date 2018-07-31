/* Even Fibonacci numbers */
/*
 * Each new term in the Fibonacci sequence is generated
 *  by adding the previous two terms. By starting with 1 
 *  and 2, the first 10 terms will be: 1, 2, 3, 5, 8, 13, 
 *  21, 34, 55, 89, ...By considering the terms in the 
 *  Fibonacci sequence whose values do not exceed four 
 *  million, find the sum of the even-valued terms.
 */
package number;

public class Fibonacci {
  static int f(int n){
		if(n <= 2)
			return n;
		else
			return f(n-1) + f(n-2);
	}
	public static void main(String[] args) {
		int i;
		for(i = 1; f(i) <= 4000000; i++);
		System.out.println("f(" + (i-1) + ") = " + f(i-1));
		int sum = 0;
		for(int k = 1; k <= i-1; k++)
			if(f(k)%2 == 0)
				sum += f(k);
		System.out.println(sum);
	}
}
