/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

/**
 *
 * @author Tiago
 */
public class Encryptor {

    public static String encrypt(String target) {
            
            int key = 2;
            StringBuilder msgCifrada = new StringBuilder();

            try {
                for (char character : target.toCharArray()) {
                    msgCifrada.append(Character.toString((char) (character + key)));
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            return msgCifrada.toString();
    }

    public static String decrypt(String target) {
        
            int key = 2;
            StringBuilder msgCifrada = new StringBuilder();

            try {
                for (char character : target.toCharArray()) {
                    msgCifrada.append(Character.toString((char) (character - key)));
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            return msgCifrada.toString();
    }
}
