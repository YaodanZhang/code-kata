import java.util.*;
import java.io.*;

public class BACRunner {

    // Minimum length of the secret word.
    public static final int MINLEN = 5;
    // Maximum length of the secret word.
    public static final int MAXLEN = 15;
    // How many rounds to play.
    public static final int ROUNDS = 50;
    // The seed value of the pseudorandom number generator for words.
    public static final int SEED = 4242;
    // Mercy limit after which the word is considered to be guessed.
    public static final int MERCY = 20;

    // A word is acceptable is it contains no duplicated letters.
    private static boolean acceptable(String s) {
        if(MINLEN <= s.length() && s.length() <= MAXLEN) {
            boolean[] seen = new boolean[26]; // words_alpha only uses 26 lowercase letters
            for(int i = 0; i < s.length(); i++) {
                int c = ((int)s.charAt(i)) - 'a';
                if(seen[c]) { return false; }
                seen[c] = true;
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Count the number of bulls and cows in the guess word with respect to the secret word.
     * You might want to call this method from your player code instead of duplicating the
     * logic. The logic works since words_alpha file uses only 26 English lowercase letters.
     * @param guess The current guess word.
     * @param secret The secret word.
     * @param out Two-element array in which the answer (bulls, cows) is written.
     */
    public static void countBullsAndCows(String guess, String secret, int[] out) {
        if(guess.length() != secret.length()) {
            out[0] = out[1] = 0;
            return;
        }
        boolean[] seen = new boolean[26];
        for(int i = 0; i < secret.length(); i++) {
            int c = ((int)secret.charAt(i)) - 'a';
            seen[c] = true;
        }
        int bulls = 0, cows = 0;
        try {
            for(int i = 0; i < secret.length(); i++) {
                int c1 = ((int)secret.charAt(i)) - 'a';
                int c2 = ((int)guess.charAt(i)) - 'a';
                if(c1 == c2) { bulls++; }
                else if(seen[c2]) { cows++; }
            }
        }
        catch(Exception e) { bulls = cows = 0; }
        out[0] = bulls; out[1] = cows;
    }

    private static int playOneRound(BACPlayer player, String secret, int mercy) {
        List<String> guessesSoFar = new ArrayList<String>();
        List<String> guessesSoFarP = Collections.unmodifiableList(guessesSoFar);
        List<Integer> bullsSoFar = new ArrayList<Integer>();
        List<Integer> bullsSoFarP = Collections.unmodifiableList(bullsSoFar);
        List<Integer> cowsSoFar = new ArrayList<Integer>();
        List<Integer> cowsSoFarP = Collections.unmodifiableList(cowsSoFar);
        String guess = "";
        int[] bc = new int[2];
        System.out.print("[" + secret + "]:");
        while(!secret.equals(guess) && mercy-- > 0) {
            try {
                guess = player.guess(secret.length(), guessesSoFarP, bullsSoFarP, cowsSoFarP);
            }
            catch(Exception e) {
                guess = "";
                for(int i = 0; i < secret.length(); i++) { guess += (char)('a' + i); }
            }
            countBullsAndCows(guess, secret, bc);
            System.out.print(" " + guess);
            System.out.flush();
            guessesSoFar.add(guess);
            bullsSoFar.add(bc[0]);
            cowsSoFar.add(bc[1]);
        }
        System.out.println("");
        return guessesSoFar.size();
    }

    private static int playBullsAndCows(BACPlayer player) throws IOException {
        Scanner s = new Scanner(new File("/Users/twer/Documents/development/code/code-kata/bullandcow/src/main/java/words_alpha.txt"));
        List<String> words = new ArrayList<String>();
        while(s.hasNextLine()) {
            String word = s.nextLine();
            if(acceptable(word)) {
                words.add(word);
            }
        }
        player.initializeWordList(Collections.unmodifiableList(words));
        int score = 0;
        Random rng = new Random(SEED);
        for(int round = 0; round < ROUNDS; round++) {
            String secret = words.get(rng.nextInt(words.size()));
            score += playOneRound(player, secret, MERCY);
        }
        return score;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("BACRunner Java with seed=" + SEED + ".");
        BACPlayer player = new BACImplementation();
        int score = playBullsAndCows(player);
        System.out.println(score + " " + player.getStudentID() + " " + player.getAuthor());
    }
}