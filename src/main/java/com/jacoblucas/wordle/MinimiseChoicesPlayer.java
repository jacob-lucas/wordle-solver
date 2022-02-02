package com.jacoblucas.wordle;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jacoblucas.wordle.Dictionary.WORDS;

public class MinimiseChoicesPlayer extends SmartPlayer {
    final Map<Character, Long> charFrequencyMap = new HashMap<>();

    public MinimiseChoicesPlayer(Game game) {
        super(game, "RAISE");
        WORDS.forEach(w ->
                w.chars().forEach(ch ->
                        charFrequencyMap.put((char)ch, charFrequencyMap.getOrDefault((char)ch, 0L) + 1)));
    }

    @Override
    public String getName() {
        return "MinimiseChoicesPlayer";
    }

    @Override
    public String getNextWord() {
        if (guessHistory.isEmpty()) {
            return startWord;
        } else {
            final List<Character> availableLetters = GuessHistoryAnalysis.getAvailableLetters(guessHistory);
            final List<String> matchingWords = getOptions();

            final List<String> sorted = matchingWords.stream()
                    .sorted(Comparator.comparing(w -> -1 * score(w, availableLetters)))
                    .collect(Collectors.toList());

            return sorted.get(0);
        }
    }

    // The higher the score, the more common the letters in the word, the more choices eliminated after the guess
    private long score(final String word, final List<Character> availableLetters) {
        return word.chars()
                .distinct()
                .filter(ch -> availableLetters.contains((char) ch))
                .mapToLong(ch -> charFrequencyMap.get((char) ch))
                .sum();
    }
}
