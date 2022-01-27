package com.jacoblucas.wordle;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.jacoblucas.wordle.Game.WORD_LENGTH;
import static com.jacoblucas.wordle.Player.ALPHABET;

public class GuessHistoryAnalysis {

    public static List<Character> getAvailableLetters(final LinkedHashMap<String, ResultCode[]> guessHistory) {
        return new ArrayList<>(
                Sets.difference(
                        ALPHABET.chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet()),
                        new HashSet<>(getLetters(guessHistory, ResultCode.NEVER))));
    }

    public static List<Character> getLetters(
            final LinkedHashMap<String, ResultCode[]> guessHistory,
            final ResultCode resultCode
    ) {
        final List<Character> wrongLetters = new ArrayList<>();
        guessHistory.forEach((word, resultCodes) -> {
            for (int i = 0; i < WORD_LENGTH; i++) {
                char letter = word.charAt(i);
                ResultCode rc = resultCodes[i];
                if (rc == resultCode) {
                    wrongLetters.add(letter);
                }
            }
        });

        return wrongLetters;
    }
}
