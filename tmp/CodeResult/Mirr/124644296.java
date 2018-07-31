import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class CF96C {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[] arr = br.readLine().toCharArray();
		int[] mir = new int[arr.length];
		for(int j = 0; j < mir.length; j++){		
			int mirr = 0;
			for(int i = 0; i < 8; i++){
				mirr |= (0x1&(arr[j]>>i))<<(7-i);
			}
			mir[j] = (char)mirr;
		}
		int[] res = new int[arr.length]; 
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < 256; j++){
				int r = ((i-1<0?0:(int)mir[i-1])+256*j)-(256+mir[i]); 
				if(r>=0){
					res[i] = r;
					System.out.println(r);
					break;
				}
			}
		}
	}
}
