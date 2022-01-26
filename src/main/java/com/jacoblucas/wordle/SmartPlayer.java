package com.jacoblucas.wordle;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.jacoblucas.wordle.Game.WORD_LENGTH;
import static com.jacoblucas.wordle.ResultCode.WRONG;

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
            // Start with a "smart" guess :)
            return "SMART";
        }

        // Prune letters from the guess order, and identify any wrong letters
        final List<Character> availableLetters = GuessHistoryAnalysis.getAvailableLetters(guessHistory);
        final List<Character> wrongLetters = GuessHistoryAnalysis.getLetters(guessHistory, WRONG);

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
        final List<String> options = Dictionary.getMatchingWords(pattern, wrongLetters);
        return options.get(0);
    }
}
