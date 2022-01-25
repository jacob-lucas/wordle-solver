package com.jacoblucas.wordle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.jacoblucas.wordle.Dictionary.WORDS;
import static com.jacoblucas.wordle.Game.WORD_LENGTH;

public class SmartPlayer extends Player {
    public SmartPlayer(Game game) {
        super(game);
    }

    @Override
    public String getName() {
        return "SmartPlayer";
    }

    @Override
    public String getNextWord() {
        if (guessHistory.isEmpty()) {
            // Start with a good guess
            return "GREAT";
        }

        // Prune letters from the guess order, and identify any wrong letters
        final List<Character> wrongLetters = new ArrayList<>();
        final List<Character> availableLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        guessHistory.forEach((word, resultCodes) -> {
            for (int i = 0; i < WORD_LENGTH; i++) {
                char letter = word.charAt(i);
                ResultCode resultCode = resultCodes[i];
                if (resultCode == ResultCode.NEVER) {
                    availableLetters.remove(new Character(letter));
                } else if (resultCode == ResultCode.WRONG) {
                    wrongLetters.add(letter);
                }
            }
        });

        // Identify any letters in the right location
        String[] patternArr = new String[5];
        for (Map.Entry<String, ResultCode[]> entry : guessHistory.entrySet()) {
            String word = entry.getKey();
            ResultCode[] resultCodes = entry.getValue();
            for (int i = 0; i < WORD_LENGTH; i++) {
                char letter = word.charAt(i);
                ResultCode resultCode = resultCodes[i];
                if (resultCode == ResultCode.RIGHT) {
                    patternArr[i] = ""+letter;
                } else {
                    if (patternArr[i] == null) {
                        patternArr[i] = "[" +
                                availableLetters.stream().map(String::valueOf).sorted().collect(Collectors.joining())
                                + "]";
                    }
                }
            }
        }

        final Pattern pattern = Pattern.compile(String.join("", patternArr));
        final List<String> options = WORDS.stream()
                .filter(w -> pattern.matcher(w).matches())
                .filter(w -> {
                    // contains all of the letters marked WRONG
                    for (final char letter : wrongLetters) {
                        if (w.indexOf(letter) == -1) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());

        return options.get(0);
    }
}
