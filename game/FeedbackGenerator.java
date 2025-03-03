package game;

public class FeedbackGenerator {
    private final String secretWord;
    private final String guess;
    private final String green = "\u001B[32m"; //ANSI code for green
    private final String yellow = "\u001B[33m"; //ANSI code for yellow
    private final String white = "\u001B[37m"; //ANSI code for white
    private final String reset = "\u001B[0m"; //ANSI code to reset color

    public FeedbackGenerator(String secretWord, String guess) {
        this.secretWord = secretWord.toUpperCase();
        this.guess = guess.toUpperCase();
    }

    public String provideFeedback() {
        StringBuilder feedback = new StringBuilder();
        boolean[] matchedInSecret = new boolean[secretWord.length()]; //track matched letters in secretWord
        boolean[] matchedInGuess = new boolean[guess.length()]; //track letters in the guess that have been matched already

        //first pass, check for green matches
        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            if (guessChar == secretWord.charAt(i)) {
                feedback.append(green).append(guessChar).append(reset); //append green feedback
                matchedInSecret[i] = true; //this postition in secret word is matched
                matchedInGuess[i] = true; //this position in guess is matched
            } else {
                matchedInGuess[i] = false; //mark it as unmatched
            }
        }
        //second pass, check for yellow matches
        for (int i = 0; i < guess.length(); i++) {
            if (matchedInGuess[i]) {
                continue; //skip letters that have been matched
            }
            char guessChar = guess.charAt(i);
            boolean foundMatch = false; //track if yellow match is found
            for (int j = 0; j < secretWord.length(); j++) {
                if (guessChar == secretWord.charAt(j) && !matchedInSecret[j]) {
                    feedback.append(yellow).append(guessChar).append(reset);
                    matchedInSecret[j] = true;
                    foundMatch = true;
                    break;
                }
            }
            if (!foundMatch) { //if no matches, mark it as white
                feedback.append(white).append(guessChar).append(reset);
            }
        }

        return feedback.toString();
    }

    public boolean isCorrect() {
        return guess.equals(secretWord);
    }
}