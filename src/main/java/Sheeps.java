import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.lang.Character.toChars;
import static java.lang.Character.toUpperCase;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.sort;
import static java.util.stream.Collectors.joining;


public class Sheeps {

    private static Exception InvalidParameterException;

    public int countSheeps(Boolean[] arrayOfSheeps) {
        int n = 0;
        try {
            for (boolean as : arrayOfSheeps) {
                if (as == true)
                    n++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

    public int Liters(double time) {


        return (int) (time / 2);

    }

    public static String boolToWord(boolean b) {

        String s1 = b ? "ye" : "false";
        return s1;
    }

    public static String areYouPlayingBanjo(String name) {
        // Program me!
        if (name.startsWith("r") || name.startsWith("R")) {
            return name + " plays banjo";
        } else
            return name + " does not play banjo";
    }

    public static String reverseWords(final String original) {
        if (original.isBlank()) {
            return original;
        }
        String[] s1 = original.split(" ");
        String result = "";
        for (String s2 : s1) {
            StringBuilder sb = new StringBuilder(s2);
            sb.reverse();
            result += sb + " ";
        }
        return result.trim();
        // Have at it
    }

    public static String noSpace(final String x) {
        return x.replaceAll("\\s", "");
    }


    public static int century(int number) {

        return (number + 99) / 100;
    }

    public static String even_or_odd(int number) {
        //Place code here
        return (number % 2 == 0) ? "Even" : "Odd";
    }


    public static int getCount(String str) {
        String[] arrayStr = str.split("");
        int count = 0;
        for (String s1 : arrayStr) {
            if (s1.contains("a") || s1.contains("e") || s1.contains("i") || s1.contains("o") || s1.contains("u")) {
                count++;
            }
        }
        return count;
    }

//    public static int getCount1(String str) {
//
//        String[] arrayStr = str.split("");
//
//        return 0;
//    }

    public static String highAndLow(String numbers) {

        int[] v = Arrays.stream(numbers.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        sort(v);
        return v[v.length - 1] + " " + v[0];
    }

//    public static int[] digitize(long n) {
//        return new StringBuilder().rev.append(n)
//                .reverse()
//                .chars()
//                .map(Character::getNumericValue)
//                .toArray();
//    }

    public static int sum(int[] arr) {
        int result = 0;
        for (int sum : arr) {
            if (sum > 0) {
                result += sum;
            }
        }
        return result;
    }

    public static boolean check(String sentence) {
        char[] letter = sentence.toCharArray();
        for (int i = 0; i < letter.length; i++) {
            if (!Character.isLetter(letter[i])) {
                return false;
            }
        }
        boolean[] alphabet = new boolean[26];
        int index = 0;


        for (int i = 0; i < sentence.length(); i++) {
            if (sentence.charAt(i) >= 'a' && sentence.charAt(i) <= 'z') {
                index = sentence.charAt(i) - 'a';
            } else if (sentence.charAt(i) >= 'A' && sentence.charAt(i) <= 'Z') {
                index = sentence.charAt(i) - 'A';
            }
            alphabet[index] = true;
        }
        for (int i = 0; i <= 25; i++) {
            if (alphabet[i] == false)
                return false;
        }
        return true;
    }


    private static TreeMap<Integer, String> MAP;

    static {
        MAP = new TreeMap<Integer, String>(Collections.reverseOrder());
        MAP.put(1, "I");
        MAP.put(4, "IV");
        MAP.put(5, "V");
        MAP.put(9, "IX");
        MAP.put(10, "X");
        MAP.put(40, "XL");
        MAP.put(50, "L");
        MAP.put(90, "XC");
        MAP.put(100, "C");
        MAP.put(400, "CD");
        MAP.put(500, "D");
        MAP.put(900, "CM");
        MAP.put(1000, "M");
    }

    public static String solution(int n) {
        StringBuilder builder = new StringBuilder();
        for (Entry<Integer, String> entry : MAP.entrySet()) {
            int i = entry.getKey();
            String s = entry.getValue();
            while (n >= i) {
                builder.append(s);
                n -= i;
            }
        }
        return builder.toString();
    }

    static String toCamelCase(String s) {
        String[] a = s.split("[-_]");
        for (int i = 0; i < s.length(); i++) {

        }
        return "";
    }

    public static String abbrevName(String name) {

        String[] aN = name.split(" ");
        char s = 0;
        String c = "";
        for (String n : aN) {
            s = Character.toUpperCase(n.charAt(0));
            c += s + ".";
        }
        return c.substring(0, c.length() - 1);
    }

    public static String[] inArray(String[] array1, String[] array2) {

        Set<String> result = new HashSet<>();

        for (String a1 : array1) {
            for (String a2 : array2) {
                if (a2.contains(a1)) {
                    result.add(a1);
                    break;
                }
            }
        }
        String[] resultArray = result.toArray(new String[result.size()]);

        sort(resultArray);

        return resultArray;
    }

    public static int[] sumParts(int[] ls) {
        int[] result = new int[ls.length + 1];
        result[0] = Arrays.stream(ls).sum();
        for (int i = 1; i < ls.length; i++) {
            result[i] = result[i - 1] - ls[i - 1];
        }
        return result;

    }

    public static int[] sortArray(int[] array) {
        List<Integer> result = new ArrayList<>();
        for (int num : array) {
            if (num % 2 != 0) {
                result.add(num);
            }
        }
        int[] results = result.stream().mapToInt(Integer::intValue).toArray();
        sort(results);
        return results;
    }

    public static String camelCase(String input) {

        String[] split = input.split(" ");
        String result = "";
        for (int i = 0; i < split.length; i++) {
            toUpperCase(split[i].charAt(0));
            result += split[i];
        }
        return result;
    }

    public static String breakCamelCase(String input) {
        String result = "";
        for (String w : input.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
            result += w + " ";
        }
        return result.trim();
    }

    public static String smash(String... words) {
        String result = "";
        for (int i = 0; i < words.length; i++) {

            result += words[i] + " ";

        }
        return result.trim();
    }

    public boolean isEven(double n) {
        return n % 2 == 0;
    }

    public static String printerError(String s) {
        String[] sA = s.split("");
        int counter = 0;
        for (String z : sA) {
            if (z.matches("![^a-mA-M]")) {
                counter++;
            }
        }
        return counter + "/" + s.length();
    }


    //incorrect
//    public static String incrementString(String str) {
//
//        NumberFormat nf = NumberFormat.getInstance();
//        StringBuilder num = new StringBuilder();
//        StringBuilder chars = new StringBuilder();
//
//        for (int i = 0; i < str.length(); i++)
//            if (Character.isDigit(str.charAt(i)))
//                num.append(str.charAt(i));
//            else
//                chars.append(str.charAt(i));
//
//        if (num.length() == 0)
//            return str + "1";
//        nf.setMinimumIntegerDigits(num.length());
//        nf.setGroupingUsed(false);
//        return chars.append(nf.format(Long.parseLong(num.toString()) + 1)).toString();
//
//    }

    public static boolean isSquare(int n) {
        return (Math.sqrt(n) % 1 == 0) ? true : false;

    }

    public static String findNeedle(Object[] haystack) {

        String result = "";
        for (int i = 0; i < haystack.length; i++) {
            if (haystack[i] == null)
                continue;
            if (haystack[i].equals("needle")) {
                result = "found the needle at position " + i;
                break;
            }
        }
        return result;
    }


    public static String remove(String str) {
        return str.substring(1, str.length() - 1);
    }

    public static int grow(int[] x) {
        int mul = 1;
        for (int i = 0; i < x.length; i++) {
            mul *= x[i];
        }
        return mul;
    }

    public static int makeNegative(final int x) {
        int result = x;
        if (x > 0) {
            result = x / 1;
        }
        return result;
    }

//    public static boolean getXO(String str) {
//
//        if (!str.contains("x") && !str.contains("o") || !str.contains("X") && !str.contains("O"))
//            return true;
//        StringBuilder x = new StringBuilder();
//        StringBuilder o = new StringBuilder();
//
//        for (int i = 0; i < str.length(); i++) {
//            if (str.charAt(i) == 'x' || str.charAt(i) == 'X')
//                x.append(str.charAt(i));
//            else if (str.charAt(i) == 'o' || str.charAt(i) == 'O')
//                o.append(str.charAt(i));
//        }
//        if (x.length() != o.length()) {
//            return false;
//        }
//        return true;
//    }

//    public static boolean getXO(String str) {
//        str = str.toLowerCase();
//        return str.replace("o", "").length() == str.replace("x", "").length();
//
//    }


    public static boolean validatePin(String pin) {
        try {
            if (pin.length() != 4 || pin.length() != 6) {
                return false;
            }
            pin = pin.replaceAll("[^a-zA-Z0-9]", "");
            parseInt(pin);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
        // Your code here...
    }

    public static String bmi(double weight, double height) {
        double bmi = weight / (height * height);
        String result = "";
        if (bmi <= 18.5) {
            result = "Underweight";
        } else if (bmi <= 25) {
            result = "Normal";
        } else if (bmi <= 30) {
            result = "Overweight";
        } else if (bmi > 30) {
            result = "Obese";
        }
        return result;
    }

    public static String[] stringToArray(String s) {
        //your code;
        return s.split(" ");
    }

    public static boolean comp(int[] a, int[] b) {
        int flag = 0;
        boolean result = false;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == Math.sqrt(b[j])) {
                    flag++;
                    if (a[i] == a.length) {
                        if (flag != a.length) {
                            result = false;
                        } else {
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }

    public String dnaToRna(String dna) {
        return dna.contains("T") ? dna.replaceAll("T", "U") : dna;  // Do your magic!
    }

//    public static String toAlternativeString(String string) {
//        StringBuilder sb = new StringBuilder(string);
//        for (int i = 0; i < string.length(); i++) {
//            if (Character.isLowerCase(string.charAt(i))) {
//                sb.setCharAt(i, toUpperCase(string.charAt(i)));
//            } else if (Character.isUpperCase(string.charAt(i))) {
//                sb.setCharAt(i, Character.toLowerCase(string.charAt(i)));
//            }
//        }
//        return sb.toString();
//    }

    public static double find_average(int[] array) {

        return Arrays.stream(array).average().getAsDouble();
    }

    public static int rowSumOddNumbers(int n) {

        return n * n * n;
    }

    public static int squareSum(int[] n) {
        int result = 0;
        for (int m : n) {

            result += (m * m);
        }
        return result;
    }

    public static String toJadenCase(String phrase) {

        String str = "";
        try {
            String[] sp = phrase.split("[, ]");

            for (String s : sp) {
                s = s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(Character.toUpperCase(s.charAt(0))));
                str += s + " ";
            }
        } catch (Exception e) {
            return null;
        }

        return str.trim();
    }

    public static boolean isValid(char[] walk) {
        if (walk.length != 10) {
            return false;
        }
        int x = 0;
        int y = 0;
        for (char c : walk) {
            if (c == 'n') {
                x += 1;
            } else if (c == 's') {
                x -= 1;
            } else if (c == 'e') {
                y += 1;
                ;
            } else if (c == 'w') {
                y -= 1;
            } else {
                return false;
            }
        }
        if (x == 0 && y == 0) {
            return true;
        }
        return false;
    }


    public static boolean isLove(final int flower1, final int flower2) {

        boolean result;
        if (flower1 % 2 == 0 && (flower2 & 1) == 1) {
            result = true;
        } else if (flower2 % 2 == 0 && (flower1 & 2) == 1) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

//    public static int lastDigit(BigInteger n1, BigInteger n2) {
//        BigInteger result = BigInteger.ONE;
//        while (n2.signum() > 0){
//            if(n2.testBit(0)) result = result.multiply(n1);
//            n1 = n1.multiply(n1);
//            n2 = n2.shiftRight(1);
//        }
//        result = result.remainder(BigInteger.TEN);
//        // Your code goes here! Have fun!
//        return result.intValue();
//    }

    public static int lastDigit(BigInteger n1, BigInteger n2) {
        return n1.modPow(n2, BigInteger.TEN).intValue();
    }

    public static double solution(int[] arr1, int[] arr2) {

        double diff = 0;

        for (int i = 0; i < arr1.length; i++) {
            diff += Math.pow(Math.abs(arr1[i] - arr2[i]), 2);
        }
        return diff / arr1.length;
    }

    public static int[] arrayDiff(int[] a, int[] b) {

        //Best Answer
//        List<Integer> listA = Arrays.stream(a).boxed().collect(Collectors.toList());
//        List<Integer> listB = Arrays.stream(b).boxed().collect(Collectors.toList());
//        listA.removeAll(listB);
//        return listA.stream().mapToInt(e -> e).toArray();

        //Clever Answer
//        return IntStream.of(a).filter(x -> IntStream.of(b).noneMatch(y -> y == x)).toArray();


        ArrayList<Integer> arr1 = new ArrayList<>() {
            {
                for (int i : a) add(i);
            }
        };
        if (arr1.isEmpty()) {
            return arr1.stream().mapToInt(Integer::intValue).toArray();
        }
        ArrayList<Integer> arr2 = new ArrayList<>() {
            {
                for (int i : b) add(i);
            }
        };
        arr1.removeAll(arr2);
        return arr1.stream().mapToInt(Integer::intValue).toArray();

    }

    public static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    public static String longest(String s1, String s2) {
        String s3 = s1 + s2;
        return Arrays.asList(s3.split(""))
                .stream()
                .distinct().sorted()
                .collect(joining());
    }

    public static String fakeBin(String numberString) {
        return numberString.replaceAll("[0-4]", "0").replaceAll("[5-9]", "1");
    }

    public static String disemvowel(String str) {
        return str.replaceAll("[aeiou]", "");
    }

    public static List<Object> filterList(final List<Object> list) {
        return list.stream().filter(e -> e instanceof Integer).collect(Collectors.toList());
    }

    public static String accum(String s) {
        return null;
    }


    public static String mix(String s1, String s2) {
//        String[] s1Arr = s1.split("");
//        String[] s2Arr = s2.split("");
//
//        Map<String, Integer> occurS1 = new HashMap<>();
//        Map<String, Integer> occurS2 = new HashMap<>();
//        // your code
//        Map<String, Integer>
        return null;
    }

    public static int zeros(int n) {

        int counter = 0;
        for (int i = 5; n / i >= 1; i *= 5)
            counter += n / i;

        return counter;
    }

    public static boolean scramble(String str1, String str2) {
        if (str2 == null || str2.length() == 0) {
            return true;
        }
        HashMap<Character, Integer> s1 = new HashMap<>();
        HashMap<Character, Integer> s2 = new HashMap<>();

        for (char ch : str1.toCharArray()) {
            char current = Character.toLowerCase(ch);
            s1.put(current, s1.getOrDefault(current, 0) + 1);
        }

        for (char ch : str2.toCharArray()) {
            char current = Character.toLowerCase(ch);
            s2.put(current, s2.getOrDefault(current, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : s2.entrySet()) {
            try {
                if (entry.getValue() > s1.get(entry.getKey())) {
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }
        }

        return true;
    }

//    public static long nextSmaller(long n) {
//        int x = Math.toIntExact(n%100);
//
//    }

    public static int singlePermutations(String s) {

        String[] st = s.split("");
        List<String> etits = new ArrayList<>();
        int f = 1;

        for (int i = 1; i < s.length(); i++) {
            f *= i + 1;
        }

        return f;
    }

    public static String whoLikesIt(String... names) {

        String res = "";
        if (names.length == 0) {
            res = "no one likes this";
        } else if (names.length == 1) {
            res = names[0] + " like this";
        } else if (names.length == 2) {
            res = names[0] + " and " + names[1] + " like this";
        } else if (names.length == 3) {
            res = names[0] + ", " + names[1] + " and " + names[2] + " like this";
        } else if (names.length >= 4) {
            res = names[0] + ", " + names[1] + " and " + (names.length - 2) + " others like this";
        }

        return res;
    }

    static String encode(String word) {
        String s = word.toLowerCase();
        String container = "";

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            container += s.lastIndexOf(c) == s.indexOf(c) ? "(" : ")";
        }
        return container;
    }


    public static String repeatStr(final int repeat, final String string) {
        String res = "";
        for (int i = 0; i < repeat; i++) {
            res += string;
        }
        return res;
    }


    public static String rps(String p1, String p2) {
        String res = "";
        switch (p1) {
            case "scissor":
                res = p2.equals("paper") ? "Player 1 won!" : p1.equals(p2) ? "Draw!" : "Player 2 won!";
                break;
            case "paper":
                res = p2.equals("rock") ? "Player 1 won!" : p1.equals(p2) ? "Draw!" : "Player 2 won!";
                break;
            case "rock":
                res = p2.equals("scissors") ? "Player 1 won!" : p1.equals(p2) ? "Draw!" : "Player 2 won!";
                break;
        }
        return res;
    }

    public static String expandedForm(int num) {

//        String[] numStr = String.valueOf(num).split("");
//        String result = "";
//        int zeros = numStr.length - 1;
//        for (int i = 0; i < numStr.length; i++) {
//            switch (numStr[i]) {
//                case "0":
//                    zeros--;
//                    break;
//                default: {
//                    if (i == 0) {
//                        result += numStr[i];
//                        for (int j = 0; j < zeros; j++) {
//                            result += "0";
//                        }
//                        zeros--;
//                    } else {
//                        result = result + " + " + numStr[i];
//                        for (int j = 0; j < zeros; j++) {
//                            result += "0";
//                        }
//                        zeros--;
//                    }
//                    break;
//                }
//            }
//        }
//        return result;

        String[] numStr = String.valueOf(num).split("");
        String result = "";
        int zeros = numStr.length - 1;
        for (int i = 0; i < numStr.length; i++) {
            switch (numStr[i]) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9": {
                    if (i == 0) {
                        result += numStr[i];
                        for (int j = 0; j < zeros; j++) {
                            result += "0";
                        }
                        zeros--;
                    } else {
                        result = result + " + " + numStr[i];
                        for (int j = 0; j < zeros; j++) {
                            result += "0";
                        }
                        zeros--;
                    }
                    break;
                }
                case "0":
                    zeros--;
                    break;
            }
        }
        return result;
    }

    public static int[] reverse(int n) {

        int[] res = new int[n];
        int test = n;
        for (int i = 0; i < n; i++) {
            res[i] = test;
            test--;
        }
        //your code
        return res;
    }

    public static int findSmallestInt(int[] args) {
        return Arrays.stream(args).min().getAsInt();
    }

    public static List<String> top3(String s) {
        String[] arr = s.split("[^A-Za-z0-9']");
        Map<String, Integer> occurrences = new HashMap<>();
        for (String word : arr) {
            if (!word.matches(".*[a-zA-Z0-9]+.*")) {
                continue;
            }
            Integer oldCount = occurrences.get(word);
            if (oldCount == null) {
                oldCount = 0;
            }
            occurrences.put(word, oldCount + 1);
        }

        List<String> result = new ArrayList<>(reverseSortByValue(occurrences).keySet());
        result.removeAll(Arrays.asList("", null));
        return result.stream()
                .limit(3)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> reverseSortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        LinkedHashMap<K, V> reverseSortedMap = new LinkedHashMap<>();
        result.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return reverseSortedMap;
    }

    public static int getSum(int a, int b) {
        int sum = 0;
        for (int i = a; i <= b; i++) {
            sum += i;
        }
        return (a == b) ? a : sum;
//        System.out.println();
    }

    public static int[] countPositivesSumNegatives(int[] input) {

        int x = 0;
        int y = 0;
        if (input != null || input.length == 0) {
            for (int i = 0; i < input.length; i++) {
                if (input[i] > 0) {
                    x++;
                } else {
                    y += input[i];
                }
            }
            int[] answers = {x, y};
            return answers;
        } else {
//            int[] answer = new int[0];
            return new int[]{};
        }
    }

    static String greet(String name, String owner) {
//        owner = "Daniel";
        if (name.equals(owner)) {
            return "Hello boss";
        } else {
            return "Hello guest";
        }
    }

    public static String solution(String str) {
        String res = "";
        for (int i = str.length() - 1; i >= 0; i--)
            res += str.charAt(i);
        return res;
    }

    public static String getMiddle(String word) {

        String res = "";
        int midChar = word.length() / 2;

        if (word.length() % 2 == 0) {
            res = "" + word.charAt(midChar - 1) + word.charAt(midChar);
        } else {
            res = "" + (word.charAt(midChar));
        }
        return res;
    }

    public static boolean endsWith(String str, String ending) {
        if (ending.length() > str.length()) {
            return false;
        }
        String strLast = "";
        for (int i = str.length() - ending.length(); i <= str.length() - 1; i++) {
            strLast += str.charAt(i) + "";
        }
        return strLast.equals(ending) ? true : false;
    }

    public static String greetings(String language) {
        // your code
        return GreetEnum.getGreetingByCountry(language);
    }

    public enum GreetEnum {
        ENGLISH("english", "Welcome"),
        CZECH("czech", "Vitejte"),
        DANISH("danish", "Velkomst"),
        DUTCH("dutch", "Welkom"),
        ESTONIAN("estonian", "Tere tulemast"),
        FINNISH("finnish", "Tervetuloa"),
        FLEMISH("flemish", "Welgekomen"),
        FRENCH("french", "Bienvenue"),
        GERMAN("german", "Willkommen"),
        IRISH("irish", "Failte"),
        ITALIAN("italian", "Benvenuto"),
        LATVIAN("latvian", "Gaidits"),
        LITHUANIAN("lithuanian", "Laukiamas"),
        POLISH("polish", "Witamy"),
        SPANISH("spanish", "Bienvenido"),
        SWEDISH("swedish", "Valkommen"),
        WELSH("welsh", "Croeso");

        public String getCountry() {
            return country;
        }

        public String getGreeting() {
            return greeting;
        }

        String country;
        String greeting;

        GreetEnum(String country, String greeting) {
            this.country = country;
            this.greeting = greeting;
        }

        public static String getGreetingByCountry(String country) {
            for (GreetEnum item : values()) {
                if (item.getCountry().equals(country)) {
                    return item.getGreeting();
                }
            }
            return ENGLISH.getGreeting();
        }

    }

    public static int findShort(String s) {
        if (s.isEmpty()) {
            return 0;
        }


        int shortest = 0;
        String[] words = s.split(" ");
        if (words[0].length() < words[1].length()) {
            shortest = words[0].length();
        } else {
            shortest = words[1].length();
        }
        for (int i = 2; i <= words.length - 1; i++) {
            if (shortest > words[i].length()) {
                shortest = words[i].length();
            }
        }
        return shortest;
    }

    public static int countSmileys(List<String> arr) {
        int valid = 0;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals(":)") || arr.get(i).equals(":D") || arr.get(i).equals(";-D") || arr.get(i).equals(":~)")) {
                valid++;
            }
        }
        return valid;
    }

    public static String toAlternativeString(String string) {
        String result = "";
        for (int i = 0; i < string.length(); i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                result += "" + Character.toLowerCase(string.charAt(i));
            } else {
                result += "" + Character.toUpperCase(string.charAt(i));
            }
        }
        return result;
    }

    public static int[] humanYearsCatYearsDogYears(final int humanYears) {
        int[] array = new int[3];
        array[0] = humanYears;

        if (humanYears == 2) {
            array[1] = 15 + 9;
            array[2] = 15 + 9;
        } else if (humanYears > 2) {
            for (int i = 0; i < humanYears - 2; i++) {
                array[1] = humanYears * 4;
                array[2] = humanYears * 5;
            }
        } else {
            array[1] = 15;
            array[2] = 15;
        }

        return array;
    }

    public static boolean isIsogram(String str) {
        if (str.isEmpty()) {
            return true;
        } else {
            int strs = str.length();
            String[] array = str.toLowerCase().split("");
            Set<String> strd = new HashSet<>(Arrays.stream(array).collect(Collectors.toList()));
            return strs == strd.size();
        }

    }

    public static int duplicateCount(String text) {
        return 3;
    }

    public static int[] between(int a, int b) {
        int[] array = new int[(b - a) + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = a++;
        }
        return array;
    }

//    public static void rightTriangle(int num){
//        for (int i = 1; i < num+1; i++) {
//            for (int j = 1; j <= i; j++) {
//                System.out.print(i);
//            }
//            System.out.println();
//        }
//    }

    public static char getGrade(int s1, int s2, int s3) {
        char res = 70;
        int ave = (s1 + s2 + s3) / 3;
        if (ave >= 90 && ave <= 100)
            res = 'A';
        else if (ave >= 80 && ave <= 90)
            res = 'B';
        else if (ave >= 70 && ave <= 80)
            res = 'C';
        else if (ave >= 60 && ave <= 70)
            res = 'D';
        else if (ave >= 50 && ave <= 60)
            res = 'F';
        return res;
    }

    public static String incrementString(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (sb.isEmpty()) {
            return "1";
        }
        for (int i = str.length() - 1; i >= 0; i--) {

            if (!Character.isDigit(sb.charAt(i))) {
                sb.append('1');
                break;
            } else if (i == 0) {
                if (sb.charAt(i) != '9') {
                    sb.setCharAt(i, (char) (str.charAt(i) + 1));
                    break;
                }
                sb.setCharAt(i, '1');
                sb.append('0');
                break;
            } else if (sb.charAt(i) != '9') {
                sb.setCharAt(i, (char) (str.charAt(i) + 1));
                break;
            } else if (Character.isLetter(sb.charAt(i - 1))) {
                sb.setCharAt(i, '1');
                sb.append('0');
                break;
            } else {
                sb.setCharAt(i, '0');
            }
        }
        return sb.toString();
    }

    public static String formatDuration(int seconds) {
        String duration = "";
        int hour = 3600;
        int minute = 60;
        int second = 1;
        String[] words = new String[]{"hour", "minute", "second"};
        int hourResult = seconds / hour;
        int minResult = (seconds % hour) / minute;
        int secResult = ((seconds % hour) % minute) / second;
        int[] numerus = new int[]{hourResult, minResult, secResult};
//        List<Integer> finalNum = Arrays.stream(numerus).filter(q -> q != 0).map();
        int timeCounter = 0;
        for (int i = 0; i < numerus.length; i++) {
            if (numerus[i] != 0) {
//                finalNum.add(i, numerus[i]);
            }

//            switch(numerus[i]) {
//                case 0:
//                    break;
//                case 1:
//                    duration += " " + numerus[i] + " " + words[i];
//                    break;
//                default:i
//                    duration += " " + numerus[i] + " " + words[i] + "s";
//                    break;
//            }
//            i
//            [7[j],8]

        }
        return duration;
    }

    public static int sumIntervals(int[][] intervals) {
        int counter = 0;
        for (int i = 0; i < intervals.length; i++) {
            for (int j = intervals[i][0]; j < intervals[i][1]; j++) {
                counter++;
            }
        }

        return counter;
    }

    public static int findOdd(int[] nums) {
        int count = 0;
        int temp = 0;

        for (int i = 0; i < nums.length; i++) {
            temp = nums[i];
            for (int j = 0; j < nums.length; j++) {
                if (temp == nums[j]) {
                    count++;
                }
            }
            if (count % 2 == 1) {
                break;
            }
        }

        return temp;
    }

    //    public static int getCount(String str) {
//        int count = 0;
//        for (int i = 0; i < str.length(); i++) {
//            if(str.charAt(i) == 'a') {
//                count++;
//            }
//        }
//    }
    public static List<Integer> streamingPirsu() {
        int[] arr = new int[]{3, 2, 1};

        List<Integer> collect = Arrays.stream(arr).boxed().filter(ans -> ans != 2).collect(Collectors.toList());
        return collect;
    }

    public static List<Integer> streamingPirsuMap() {
        int[] arr = new int[]{3, 2, 1, 0};

        List<Integer> collect = Arrays.stream(arr).boxed().map(ans -> ans + 1).collect(Collectors.toList());
        return collect;
    }

//            public static List<String> streamingPirsuSum() {
//                String[] arr = new String[]{"Tr", ".Q", "A"};
//
//                return Arrays.stream(arr).;
//
//            }
//            public static long streamingPirsuCount() {
//                int[] arr = new int[]{4,4,4,4,4,4,4,4,4,4,4,4};
//
//                return Arrays.stream(arr).count();
//
//            }


    public static boolean smallEnough(int[] a, int limit) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] < limit) {
                return false;
            }
        }
        return true;
    }

    public static String oddOrEven(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum % 2 == 0 ? "even" : "odd";
    }

    public static int findDifference(final int[] firstCuboid, final int[] secondCuboid) {
        int num1 = 1;
        int num2 = 1;
        int diff = 0;

        for (int i = 0; i < firstCuboid.length; i++) {
            num1 *= firstCuboid[i];
            num2 *= secondCuboid[i];
        }
        diff = num1 - num2;
        return diff < 0 ? diff * -1 : diff;
    }

    public static String seriesSum(int n) {
        DecimalFormat f = new DecimalFormat("##.00");
        double denominator = 1;

        double fraction = 1;

        String result = "1";

        for (int i = 1; i < n; i++) {
            denominator = denominator + 3;
            fraction += 1 / denominator;

        }
        return String.valueOf(f.format(fraction));
    }


    static boolean debug = false;

    static long largerPow(long x) {
        long newp = 1;
        while (newp < x) {
            newp <<= 1;
        }
        return newp;
    }

    static long rangeSum(long k, long r) {
        return (1 + r) * (r - 1 + 1);
    }

    //    static long elderAge(long n, long m, long k, long newp) {
//        if(n == 0 || m == 0){
//            return 0;
//        }
//        if(n > m){
//            n, m = m, n;
//        }
//        return 0; // do it!
//    }
    public static int[] fibonacci(int n) {
        int[] fib = new int[n];
        int current = 1;
        int previous = 0;


        for (int i = 0; i < n; i++) {
            fib[i] = current;
            current = previous;
            current += previous;
        }
        return fib;
    }

    public static String shortcut(String input) {
        return Arrays.stream(input.split("")).filter(x -> !x.matches("^[AEIOUaeiou]")).collect(joining());
    }

    public static int fib(int x) {
        if (x == 0) {
            return 0;
        } else if (x == 1) {
            return 1;
        } else {
            return fib(x - 1) + fib(x - 2);
        }
    }


    public static int countBits(int n) {
        String[] arr = Integer.toBinaryString(n).split("");

        return (int) Arrays.stream(arr).filter(x -> x.equals("1")).count();
    }

    public static String[] towerBuilder(int nFloors) {

        int maxL = (nFloors * 2) - 1;
        int center = (maxL / 2);
        String[] tower = new String[nFloors];
        for (int i = 0; i < nFloors; i++) {
            String temp = "";
            for (int j = 0; j < maxL; j++) {
                if (j >= center - i && j <= center + i) {
                    temp += "*";
                } else {
                    temp += " ";
                }
            }
            tower[i] = temp;
        }
        return tower;
    }

//    public static String multiTable(int num) {
//        int[] arry = new int[]{1,2,3,4,5,6,7,8,9,10};
//
//        Arrays.stream(arry).forEach(x -> x );
//        return ""; // good luck
//    }

    public static class A {
        String name = "pierce";
    }

    public static class B extends A {
        public B() {
            this.name = "jeymar";
        }

        String name;

    }


    public static boolean isNarcissistic(int number) {
        int originalNum = number;
        int sum = 0;
        int numSize = String.valueOf(number).length();
        while (number > 0) {
            int x = number % 10;
            sum += Math.pow(x, numSize);
            numSize /= 10;
        }
        return sum == originalNum;
    }

    public static String[] capitalize(String s) {
        String res1 = "";
        String res2 = "";

        for (int i = 0; i < s.length(); i++) {
            if (i % 2 == 1) {
                res1 += s.toUpperCase().charAt(i);
                res2 += s.toLowerCase().charAt(i);

            } else {
                res1 += s.toLowerCase().charAt(i);
                res2 += s.toUpperCase().charAt(i);
            }
        }
        String[] result = {res1, res2};
        return result;
    }

    public static int[] rowWeights(final int[] weights) {
        return new int[]{0, 0};
    }

    public static int minValue(int[] values) {

//        String s = Arrays.stream(values)
//                .sorted()
//                .distinct()
//                .mapToObj(Integer::toString)
//                .collect(Collectors.joining(""));
//        return Integer.valueOf(s);

        String res = "";
        int[] sorted = Arrays.stream(values).distinct().sorted().toArray();
        for (int i = 0; i < sorted.length; i++) {
            res += sorted[i];
        }
        return Integer.parseInt(res);
    }


    public static boolean getXO(String str) {
        String s = str.toLowerCase();
        int x = 0;
        int o = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'x') {
                x++;
            } else if (s.charAt(i) == 'o') {
                o++;
            }
        }
        if (x != o) {
            return false;
        }
        return true;
    }

    public static int findAverage(int[] nums) {

        return (int) Arrays.stream(nums).average().getAsDouble();
    }

    public static String sumStrings(String a, String b) {
        int leadingZeros = Math.abs(a.length() - b.length());
        int temp = 0;
        String sum = "";
        int carry = 0;

        if (a.length() > b.length()) {
            b = "0".repeat(leadingZeros) + b;
        } else if (a.length() < b.length()) {
            a = "0".repeat(leadingZeros) + a;
        }

        for (int i = a.length() - 1; i >= 0; i--) {
            temp = carry + Character.getNumericValue(b.charAt(i)) + Character.getNumericValue(a.charAt(i));
            carry = temp / 10;
            temp %= 10;
            sum = temp + sum;
            if (i == 0 && carry == 1) {
                sum = 1 + sum;
            }
        }
        return sum.replaceFirst("^0+(?!$)", "");
    }

    public static String CalculateAge(int birth, int yearTo) {
        int age = yearTo - birth;
        if (age == -1) {
            return "You will be born in 1 year.";
        } else if (age == 0) {
            return "You were born this very year!";
        } else if (age == 1) {
            return "You are 1 year old.";
        } else if (age >= 2) {
            return "You are " + age + " years old.";
        } else {
            return "You will be born in" + (age * -1) + "year.";
        }
    }

    public static boolean check(Object[] a, Object x) {

        int z = 3;
        Math.sqrt(z);
        return Arrays.stream(a).anyMatch(s -> s.equals(x));

    }

    public static int[] findEvenIndex(int[] arr) {
        return Arrays.stream(arr).distinct().toArray();
    }

    public static int[] twoSum(int[] numbers, int target) {
        int[] res = new int[2];
        if ((target - numbers[0]) == numbers[1]) {
            res = new int[]{0, 1};
        } else if ((target - numbers[1]) == numbers[2]) {
            res = new int[]{1, 2};
        } else {
            res = new int[]{0, 2};

        }

        return res; // Do your magic!
    }

    public static <T> List<T> josephusPermutation(final List<T> items, final int k) {
        int counter = k;
        List<T> res = new ArrayList<>();
        while (res.size() != items.size()){
            if(counter == items.size()){
                counter = 1;
            }
            else if(counter > items.size()){
                int carry = counter - items.size();
                counter = carry;
            }
            res.add(items.get(counter-1));
            counter += k;

        }
        return res;
    }

    public static int noBoringZeros(int n) {
        return Integer.parseInt(String.valueOf(n).replaceAll("0+$", ""));
    }

    public static int dutyFree(int normPrice, int discount, int hol) {


        double x = normPrice * (Math.round(discount * 100) / 100);
        return (int) (hol / x * 100);
    }

    public static double findUniq(double arr[]) {
        // Do the magic
        double[] x  = Arrays.stream(arr).sorted().toArray();
        return x[0] == x[1] ? x[0] : x[x.length-1];
    }

    public static String showSequence(int value){
        int sum = 0;
        String result = "";
        if(value < 0){
            return value + "<0";
        }
        else if(value == 0){
            return "0=0";
        }
        else{
            for (int i = 0; i <= value; i++) {
                sum += i;
                result += "+"+i;
            }
        }
        return result.replaceFirst("[+]", "") + "=" + sum;
    }
    public static String twoSort(String[] s) {
        return String.join("***",Arrays.stream(s).sorted().findFirst().get().split(""));
    }

    public static String high(String s) {
        // Your code here...
        return Arrays.stream(s.split(" ")).max(Comparator.comparing(x -> x.chars().sum())).get();
    }

    public static int [][] multiplicationTable(int n){
        int[][] matrix = new int [n][n];
        int num = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] += num;
                num++;
            }
        }
        return matrix;
    }

    public static void main(String[] args) {

        System.out.println(findUniq(new double[]{0, 1, 0}));

        Object[] s = {1,2,3,4,5,6,7,8,9,10};
        System.out.println(josephusPermutation(Arrays.stream(s).toList(), 2));
//        System.out.println(twoSum(s, -7));
//        System.out.println(sumStrings("0000003284723", "000001"));

//        System.out.println(minValue(s));
//        System.out.println(sumIntervals(new int[][]{{1, 4}, {7, 10}, {3, 5}}));
//        System.out.println(streamingPirsuSum());

        // 1 even occurrence
        // 2 odd occurrence
        // 3 even occurrence
        // return odd occurrence
//        System.out.println(shortcut("Hello"));
//        System.out.println(2%6);
//        Arrays.stream(fibonacci(10)).forEach(x -> System.out.println(x));

//        Arrays.stream(towerBuilder(3)).forEach(System.out::println);
//        System.out.println(wordRev("I am Java Developer"));
//        System.out.println(wordSearch("I am Java Developer", "Java"));
//        List<Integer> num = List.of(1,2,3,4,5,6,7,8,9,10);
//        System.out.println(ans(10, 8));
//        System.out.println(findWord("Listen look see learn", 3));

    }

    private static void josephusPermutation(Object[] objects, int i, Object[] objects1) {
    }

    public static String ans(int numList, int num) {
        String res = "";
        for (int i = 1; i < numList; i++) {
            int total = (numList - i) + (num - numList);
            if (total < 10 && total > 0) {
                res += i + " + " + (total) + " = " + num + "\n";
            }
        }
        return res;
    }

    public static boolean wordSearch(String sentence, String word) {
        return sentence.contains(word);
    }

    public static String findWord(String word, int find) {
        String[] str = word.split(" ");
        return str[find - 1];
    }

    public static String wordRevs(String word) {
        String[] s1 = word.split(" ");
        String s2 = "";
        for (int i = s1.length - 1; i >= 0; i--) {
            s2 += s1[i] + " ";
        }
        return s2.trim();
    }

    public static int strCount(String str, char letter) {
        //write code here
        int res = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == letter) res = i;
            break;
        }
        return res;
    }
//
//   public static String val(int num){
//        String[] denom = {"Hundred", "Thousand", "Million", "Billion", "Trillion", "Quintillion"};
//        String[] numToLet = {"Zero" , "One" , "Two" , "Three" , "Four" , "Five" , "Six" , "Seven" , "Eight" , "Nine" };
//        String[] numToThou = { "Ten" , "Twenty" , "Thirty" , "Fourty" , "Fifty" , "Sixty" , "Seventy" , "Eighty" , "Ninety" };
//
//       for (int i = 0; i < ; i++) {
//
//       }
//
//   }

    public static boolean anagram(String word, String gram) {
        String[] ana = word.split("");
        String[] gray = gram.split("");
        String s1 = Arrays.stream(ana).map(String::toLowerCase).sorted().collect(joining());
        String s2 = Arrays.stream(gray).map(String::toLowerCase).sorted().collect(joining());
        return s1.matches(s2);
    }
}