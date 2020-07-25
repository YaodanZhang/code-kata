import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BACImplementation implements BACPlayer {
    private String author = "Li, Xinlong";
    private String studentID = "500998565";
  //  private Set<String> guessSet = new HashSet<String>(); // make a list to store the guess list with given length
    private List<String> words = new ArrayList<String>();

    public String getAuthor() {
        return author;
    }

    public String getStudentID() {
        return studentID;
    }

    public void initializeWordList(List<String> words) {
        // make a copy of the words list
        this.words = new ArrayList<>(words);
    }

  public String guess(int n, List<String> guesses, List<Integer> bulls, List<Integer> cows) {
      List<String> candidates = words.stream()
          .filter(it -> it.length() == n)
          .collect(Collectors.toList());
      for (int i = 0; i < guesses.size(); i++) {
        String currentGuess = guesses.get(i);
        Integer currentBull = bulls.get(i);
        Integer currentCow = cows.get(i);
        candidates.removeIf(candidate -> {
          int[] score = new int[2];
          BACRunner.countBullsAndCows(candidate, currentGuess, score);
          return !(score[0] == currentBull && score[1] == currentCow);
        });
      }
      return candidates.get(0);
    }
}
