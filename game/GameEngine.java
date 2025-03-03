package game;

import io.StatsTracker;
import model.UserGame;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class GameEngine {
    private final String secretWord;
    private final Scanner scanner;
    private final StatsTracker statsTracker;
    private final String username;
    private final List<String> wordList;
    private int attempts;
    private static final int MAX_ATTEMPTS = 6;

    private final Set<Character> guessedLetters;
    private final Set<Character> incorrectGuesses;

    //initialize the game engine
    public GameEngine(String secretWord, Scanner scanner, StatsTracker statsTracker, String username, List<String> wordList) {
        this.secretWord = secretWord.toLowerCase(); //secret word in lowercase
        this.scanner = scanner;
        this.statsTracker = statsTracker;
        this.username = username;
        this.wordList = wordList;
        this.attempts = 0; //start attempts at 0
        this.guessedLetters = new HashSet<>();
        this.incorrectGuesses = new HashSet<>();
    }

    //start and run the game
    public boolean startGame() {
        System.out.println("Welcome to Wordle! Guess the 5-letter word.");
        
        boolean isWin = false;

        //loop until maximum attempts are reached
        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Enter your guess: ");
            String guess = scanner.nextLine().trim().toLowerCase();

            //validate the guess
            if (guess.length() != 5) {
                System.out.println("Your guess must be exactly 5 letters long.");
                continue;
            }
            if (!guess.matches("[a-z]+")) {
                System.out.println("Your guess must only contain lowercase letters.");
                continue;
            }
            if (!wordList.contains(guess)) {
                System.out.println("Word not in list. Please enter a valid word.");
                continue;
            }
            updateGuessedLetters(guess);
            attempts++;

            //check if the guess is correct
            if (guess.equals(secretWord)) {
                System.out.println("Congratulations! You've guessed the word correctly.");
                isWin = true;
                break;
            } else {
                //provide feedback on the guess
                System.out.println("Feedback: " + getFeedback(guess));
                displayRemainingLetters(guess); //show remaining letters
                System.out.println("Attempts remaining: " + (MAX_ATTEMPTS - attempts)); //show remaining attempts
            }
        }

        //game over message if user didn't win
        if (!isWin) {
            System.out.println("Game over. The correct word was: " + secretWord);
        }

        displayStatsOption(); //call to prompt for stats
        System.out.println("Press Enter to exit...");
        scanner.nextLine(); //wait for user to press 'ENTER' before exiting
        return isWin;
    }

    //get feedback on the guess
    private String getFeedback(String guess) {
        FeedbackGenerator feedbackGenerator = new FeedbackGenerator(secretWord, guess);
        return feedbackGenerator.provideFeedback();
    }
    private void updateGuessedLetters(String guess) {
        for (char c : guess.toCharArray()) {
            guessedLetters.add(c);
            if (secretWord.indexOf(c) == -1) {
                incorrectGuesses.add(c);
            }
        }
    }

    //display remaining letters
    private void displayRemainingLetters(String guess) {
        StringBuilder remainingLetters = new StringBuilder("Remaining letters: ");
        for (char c = 'A'; c <= 'Z'; c++) {
            char lowerC = Character.toLowerCase(c); 
            //check if the letter is not in the guess
            if (!incorrectGuesses.contains(lowerC)) {
                remainingLetters.append(c).append(" ");
            }
        }
        System.out.println(remainingLetters.toString().trim()); //remaining letters
    }

    private void displayStatsOption() {
        String response;
        do {
            System.out.print("Do you want to see your stats? (yes/no): ");
            response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes")) {
                try {
                    statsTracker.displayStats(username); //show stats if user wants to see them
                } catch (IOException e) {
                    System.out.println("An error occurred while retrieving your stats. Please try again later.");
                }
            } else if (response.equals("no")) {
            } else {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        } while (!response.equals("yes") && !response.equals("no")); //check for valid input
    }

    public int getAttempts() {
        return attempts;
    }
}
