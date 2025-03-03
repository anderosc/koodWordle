package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WordListLoader {

    public static List<String> loadWords(String filename) throws IOException {
        //read all lines from the file and return them as string
        return Files.readAllLines(Paths.get(filename));
    }
}