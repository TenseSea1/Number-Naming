import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        //Choose a number written as a String; e.g., "-123.", "000.000", "05.0000",
        //"205789789078907980708972890.0893460875093998368979739", "10^-0", "10^3000000", and "123 456 789 . 987 654 321".
        //Spaces will be ignored.
        System.out.println(convert("3.141592653589793238462643383279"));
    }

    public static String convert(String num0) {
        num0 = num0.replaceAll(" ", "");
        String num = num0.substring(num0.charAt(0) == '-' ? 1 : 0);
        if (num.matches("0+\\.0*") || num.matches("\\.?0+")) {
            return "zero";
        }
        if (num.matches("\\d+")) {
            return removeDoubles((num0.charAt(0) == '-' ? "negative " : "") + intConvert(num));
        }
        if (num.matches("\\d+\\.0*")) {
            return removeDoubles((num0.charAt(0) == '-' ? "negative " : "") + intConvert(num.substring(0, num.indexOf('.'))));
        }
        if (num.matches("0*\\.\\d+")) {
            return removeDoubles((num0.charAt(0) == '-' ? "negative " : "") + decimalConvert(num.substring(num.indexOf('.') + 1)));
        }
        if (num.matches("\\d+\\.\\d+")) {
            return removeDoubles((num0.charAt(0) == '-' ? "negative " : "") + intConvert(num.substring(0, num.indexOf('.'))) + " and " + decimalConvert(num.substring(num.indexOf('.') + 1)));
        }
        if (num.matches("10\\^-?\\d+")) {
            return removeDoubles((num0.charAt(0) == '-' ? "negative " : "") + powerConvert(num.substring(num.indexOf('^') + 1)));
        }
        return "Error: The input is not a valid number. Please enter a valid number.";
    }

    public static String removeDoubles(String str) {
        str = str.trim();
        while(str.contains("  "))
            str = str.replaceAll("  ", " ");
        return str;
    }

    public static String intConvert(String num) {
        num = num.replaceAll("^0+", "");
        int len = num.length();
        if(len % 3 == 1)
            num = "00" + num;
        if(len % 3 == 2)
            num = "0" + num;
        len = num.length();
        String str = "";
        for (int i = 0; i < len; i += 3) {
            String three = threeConvert(num.substring(i, i + 3));
            str += " " + three + (three.equals("  ") ? "" : illion(len / 3 - 1 - i / 3));
        }
        return str.substring(1);
    }

    public static String decimalConvert(String num) {
        num = num.replaceAll("0+$", "");
        int len = num.length();
        int d = len - len % 3;
        return intConvert(num) +
        switch(len % 3) {
            case 0 -> "";
            case 1 -> " ten";
            default -> " hundred";
        }
        + illion(d / 3) + ((num.matches("0+1") || num.equals("1")) ? "th" : "ths");
    }

    public static String powerConvert(String num0) {
        String num = num0.substring(num0.charAt(0) == '-' ? 1 : 0).replaceAll("^0+", "");
        if(num.equals(""))
            return "one";
        BigInteger bigNum = new BigInteger(num);
        BigInteger mod = bigNum.mod(BigInteger.valueOf(3));
        return switch(mod.intValue()) {
            case 0 -> "one";
            case 1 -> (num0.charAt(0) == '-' ? "one " : "") + "ten";
            default -> "one hundred";
        }
        + illion((bigNum.subtract(mod)).divide(BigInteger.valueOf(3))) + (num0.charAt(0) == '-' ? "th" : "");
    }

    public static String threeConvert(String n) {
        String str = "";
        switch (n.substring(0,1)) {
            case "1" -> str = "one hundred";
            case "2" -> str = "two hundred";
            case "3" -> str = "three hundred";
            case "4" -> str = "four hundred";
            case "5" -> str = "five hundred";
            case "6" -> str = "six hundred";
            case "7" -> str = "seven hundred";
            case "8" -> str = "eight hundred";
            case "9" -> str = "nine hundred";
        }
        String str1 = "";
        String str2 = "";
        String str3 = "";
        if(n.substring(1, 2).equals("1")) {
            switch(n.substring(2, 3)) {
                case "0" -> str1 = "ten";
                case "1" -> str1 = "eleven";
                case "2" -> str1 = "twelve";
                case "3" -> str1 = "thirteen";
                case "4" -> str1 = "fourteen";
                case "5" -> str1 = "fifteen";
                case "6" -> str1 = "sixteen";
                case "7" -> str1 = "seventeen";
                case "8" -> str1 = "eighteen";
                case "9" -> str1 = "nineteen";
            }
            str = str + " " + str1;
        }
        else {
            switch(n.substring(1,2)) {
                case "2" -> str2 = "twenty";
                case "3" -> str2 = "thirty";
                case "4" -> str2 = "fourty";
                case "5" -> str2 = "fifty";
                case "6" -> str2 = "sixty";
                case "7" -> str2 = "seventy";
                case "8" -> str2 = "eighty";
                case "9" -> str2 = "ninety";
            }
            switch(n.substring(2,3)) {
                case "1" -> str3 = "one";
                case "2" -> str3 = "two";
                case "3" -> str3 = "three";
                case "4" -> str3 = "four";
                case "5" -> str3 = "five";
                case "6" -> str3 = "six";
                case "7" -> str3 = "seven";
                case "8" -> str3 = "eight";
                case "9" -> str3 = "nine";
            }
            str = str + " " + str2 + " " + str3;
        }
        return str;
    }

    public static String illion(int n) {
        if(n == 0)
            return "";
        if(n == 1)
            return " thousand";
        String num = Integer.toString(n - 1);
        int len = num.length();
        if(len % 3 == 1)
            num = "00" + num;
        if(len % 3 == 2)
            num = "0" + num;
        len = num.length();
        String str = "";
        for(int i = 0; i < len / 3; i++)
            str = str + prefixConvert(num.substring(3 * i, 3 * i + 3));
        return " " + str + "on";
    }

    public static String illion(BigInteger n) {
        if(n.equals(BigInteger.ZERO))
            return "";
        if(n.equals(BigInteger.ONE))
            return " thousand";
        String num = n.subtract(BigInteger.ONE).toString();
        int len = num.length();
        if(len % 3 == 1)
            num = "00" + num;
        if(len % 3 == 2)
            num = "0" + num;
        len = num.length();
        String str = "";
        for(int i = 0; i < len / 3; i++)
            str = str + prefixConvert(num.substring(3 * i, 3 * i + 3));
        return " " + str + "on";
    }

    public static String prefixConvert(String num) {
        String str = switch(num) {
            case "000" -> "nilli";
            case "001" -> "milli";
            case "002" -> "billi";
            case "003" -> "trilli";
            case "004" -> "quadrilli";
            case "005" -> "quintilli";
            case "006" -> "sextilli";
            case "007" -> "septilli";
            case "008" -> "octilli";
            case "009" -> "nonilli";
            default -> "";
        };
        if(!str.equals(""))
            return str;
        char d1 = num.charAt(2);
        char d2 = num.charAt(1);
        char d3 = num.charAt(0);
        boolean s = false;
        boolean x = false;
        boolean n = false;
        boolean m = false;
        switch(d2) {
            case '1', '6', '7' -> n = true;
            case '2' -> {
                m = true;
                s = true;
            }
            case '3', '4', '5' -> {
                n = true;
                s = true;
            }
            case '8' -> {
                m = true;
                x = true;
            }
            case '0' -> {
                switch(d3) {
                    case '1' -> {
                        n = true;
                        x = true;
                    }
                    case '2', '6', '7' -> n = true;
                    case '3', '4', '5' -> {
                        n = true;
                        s = true;
                    }
                    case '8' -> {
                        m = true;
                        x = true;
                    }
                }
            }
        }
        String p1 = "";
        String p2 = "";
        String p3 = "";
        switch(d1) {
            case '1' -> p1 = "un";
            case '2' -> p1 = "duo";
            case '3' -> {
                if(s == true || x == true)
                    p1 = "tres";
                else
                    p1 = "tre";
            }
            case '4' -> p1 = "quattuor";
            case '5' -> p1 = "quin";
            case '6' -> {
                if(s == true)
                    p1 = "ses";
                else
                    if(x == true)
                        p1 = "sex";
                    else
                        p1 = "se";
            }
            case '7' -> {
                if(m == true)
                    p1 = "septem";
                else
                    if(n == true)
                        p1 = "septen";
                    else
                        p1 = "septe";
            }
            case '8' -> p1 = "octo";
            case '9' -> {
                if(m == true)
                    p1 = "novem";
                else
                    if(n == true)
                        p1 = "noven";
                    else
                        p1 = "nove";
            }
        }
        switch(d2) {
            case '1' -> p2 = "deci";
            case '2' -> p2 = "viginti";
            case '3' -> p2 = "triginta";
            case '4' -> p2 = "quadraginta";
            case '5' -> p2 = "quinquaginta";
            case '6' -> p2 = "sexaginta";
            case '7' -> p2 = "septuaginta";
            case '8' -> p2 = "octoginta";
            case '9' -> p2 = "nonaginta";
        }
        switch(d3) {
            case '1' -> p3 = "centi";
            case '2' -> p3 = "ducenti";
            case '3' -> p3 = "trecenti";
            case '4' -> p3 = "quadringenti";
            case '5' -> p3 = "quingenti";
            case '6' -> p3 = "sescenti";
            case '7' -> p3 = "septingenti";
            case '8' -> p3 = "octingenti";
            case '9' -> p3 = "nongenti";
        }
        str = p1 + p2 + p3;
        if (str.charAt(str.length() - 1) == 'a')
            str = str.substring(0, str.length() - 1) + "i";
        return str + "lli";
    }
}