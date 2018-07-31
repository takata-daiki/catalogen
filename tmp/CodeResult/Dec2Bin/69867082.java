package ru.snilit;

public class Translate {

    static String dec2bin(Integer arg) {
        String result = "";
        short q = 0;
        while (arg > 0) {
            q = (short) (arg % 2);
            arg /= 2;
            result = q + result;
        }
        return result;
    }

    static String dec2hex(float arg) {
        String result = "";
        short q = 0;
        Integer c = 2;
        while (c > 0) {
            q = (short) (16 * (arg / 16 - Math.floor(arg / 16)));
            c = (int) Math.floor(arg / 16);
            arg = c;
            if (q < 10) {
                result = q + result;
            } else {
                switch (q) {
                    case 10:
                        result = "a" + result;
                        break;
                    case 11:
                        result = "b" + result;
                        break;
                    case 12:
                        result = "c" + result;
                        break;
                    case 13:
                        result = "d" + result;
                        break;
                    case 14:
                        result = "e" + result;
                        break;
                    case 15:
                        result = "f" + result;
                        break;
                }
            }
        }
        return result;
    }

    public static void main(String argums[]) {
        System.out.println(dec2bin(90367));
        System.out.println(dec2hex(90367));
    }
}
