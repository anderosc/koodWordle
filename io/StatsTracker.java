package io;

import java.io.*;
import java.util.*;

public class StatsTracker {
    private final String statsFile;

    public StatsTracker(String statsFile) {
        this.statsFile = statsFile;
    }
    //record the outcome
    public void recordGame(String username, String secretWord, int attempts, String result) throws IOException {
        //game outcome to stats file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(statsFile, true))) {
            writer.write(username + "," + secretWord + "," + attempts + "," + result);
            writer.newLine();
        }
    }

    //display stats for specific user
    public void displayStats(String username) throws IOException {
        int gamesPlayed = 0;
        int gamesWon = 0;
        int totalAttempts = 0;

        //read stats file and calculate statistics
        try (BufferedReader reader = new BufferedReader(new FileReader(statsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) { //check if the entry belongs to the user
                    gamesPlayed++;
                    totalAttempts += Integer.parseInt(parts[2]);
                    if (parts[3].equals("win")) {
                        gamesWon++;
                    }
                }
            }
        }

        //display statistics
        if (gamesPlayed > 0) {
            double averageAttempts = (double) totalAttempts / gamesPlayed;
            System.out.println("Stats for " + username + ":");
            System.out.println("Games played: " + gamesPlayed);
            System.out.println("Games won: " + gamesWon);
            System.out.printf("Average attempts per game: %.1f\n", averageAttempts);
        } else {
            System.out.println("No stats available for " + username);
        }
    }
}