package com.jacoblucas.wordle;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.jacoblucas.wordle.Game.WORD_LENGTH;
import static com.jacoblucas.wordle.ResultCode.WRONG;

public class SmartPlayer extends Player {
    protected String startWord;

    public SmartPlayer(Game game, final String startWord) {
        super(game);
        this.startWord = startWord;
    }

    @Override
    public String getName() {
        return "SmartPlayer-" + startWord;
    }

    @Override
    public String getNextWord() {
        if (guessHistory.isEmpty()) {
            return startWord;
        }

        // Any guess will do
        return getOptions().get(0);
    }

    protected List<String> getOptions() {
        final List<Character> availableLetters = GuessHistoryAnalysis.getAvailableLetters(guessHistory);
        final List<Character> wrongLetters = GuessHistoryAnalysis.getLetters(guessHistory, WRONG);
        final Pattern pattern = getWordPatternForHistory(availableLetters);
        return Dictionary.getMatchingWords(pattern, wrongLetters);
    }

    private Pattern getWordPatternForHistory(final List<Character> availableLetters) {
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

        return Pattern.compile(String.join("", patternArr));
    }
}
