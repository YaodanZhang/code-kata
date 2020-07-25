import java.util.*;

public interface BACPlayer {

    /**
     * Return the author of the player, in form "Lastname, Firstname".
     */
    public String getAuthor();

    /**
     * Return the student ID of the the author of this player.
     */
    public String getStudentID();

    /**
     * This method is called exactly once by the BACRunner engine in the beginning. Initialize
     * whatever internal data structures you use to store the list of possible words to speed
     * up your future computations.
     */
    public void initializeWordList(List<String> words);

    /**
     * Given the current state of the game, return the next guess by the player agent.
     * @param n The length of the secret word.
     * @param soFar List of guesses done so far. Empty list, if no guesses have been made.
     * @param bulls List of number of bulls that each guess in {@code sofar} contained.
     * @param cows List of number of cows that each guess in {@code sofar} contained.
     */
    public String guess(int n, List<String> guesses, List<Integer> bulls, List<Integer> cows);

}
