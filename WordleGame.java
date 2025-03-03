import game.GameEngine;
import io.WordListLoader;
import io.StatsTracker;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class WordleGame {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        //load the list of words
        List<String> words = WordListLoader.loadWords("wordle-words.txt");

        if (args.length == 0) {
            System.out.println("Please provide a number as command line argument");
            return;
        }

        int wordIndex;
        try {
            //argument to integer
            wordIndex = Integer.parseInt(args[0]);
            //check for valid index
            if (wordIndex < 0 || wordIndex >= words.size()) {
                System.out.println("Error: Word index out of range.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid word index. Please provide an integer.");
            return;
        }

        //secret word = provided index
        String secretWord = words.get(wordIndex).toUpperCase();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        StatsTracker statsTracker = new StatsTracker("stats.csv");

        GameEngine gameEngine = new GameEngine(secretWord, scanner, statsTracker, username, words);
        boolean isWin = gameEngine.startGame();

        //game result to stats tracker
        statsTracker.recordGame(username, secretWord, gameEngine.getAttempts(), isWin ? "win" : "loss");
    }
}