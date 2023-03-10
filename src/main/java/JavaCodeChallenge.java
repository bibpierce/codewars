public class JavaCodeChallenge {

    public static String wordReversal(String sentence){
        String[] words = sentence.split(" ");
        String[] reversed = new String[words.length];
        int j = 0;
        for (int i = words.length-1 ; i > 0 ; i++) {
            reversed[j] += words[i];
            j++;
        }
        return String.join(" ", reversed);
    }

    public static void main(String[] args) {

        System.out.println(wordReversal("I am Java Developer"));

    }
}
