/*
 * Count.java
 */

public class Count {

    public static void main(String[] argv) {
        for (int i=1; i <= 10; i++) {
            System.out.print(i);
            if (i<10) {
                System.out.print(",");
            } else {
                System.out.println("");
            }
        }
    }

}