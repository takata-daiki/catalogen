package ce1002.E1.s101502019;
import java.util.*;
public class E11 {
		public static void main(String[] args){
		int c = 0,csum = 0;
		double p = 0,dsum = 0;
		Scanner input = new Scanner(System.in);
		System.out.print("Number of candy = ");
		c = input.nextInt();
		int c1 = c;
		System.out.print("How many candy per warping paper = ");
		p = input.nextDouble();
		while((c*p)+c >= 1){ 
			c = (int)(c*p);
			csum = csum + c;
			p = ((double)c*p - (int)c*p);
			dsum = dsum + p;
		}
		System.out.println("Number of candy which will be eaten = "+(int)(c1+csum+dsum));
	}
}
