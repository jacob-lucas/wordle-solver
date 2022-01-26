package com.jacoblucas.wordle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Dictionary {
    public static final List<String> WORDS = new ArrayList<>();

    static {
        try {
            WORDS.addAll(Files.readAllLines(
                            Paths.get("src/main/resources/words.txt"), StandardCharsets.UTF_8).stream()
                    .map(String::toUpperCase)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getMatchingWords(final Pattern pattern, final List<Character> includedLetters) {
        return WORDS.stream()
                .filter(w -> pattern.matcher(w).matches())
                .filter(w -> {
                    // contains all of the letters marked WRONG
                    for (final char letter : includedLetters) {
                        if (w.indexOf(letter) == -1) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
