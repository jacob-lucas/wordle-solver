package com.jacoblucas.wordle;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jacoblucas.wordle.Dictionary.WORDS;

public class MinimiseChoicesPlayer extends SmartPlayer {
    final Map<Character, Long> charFrequencyMap = new HashMap<>();

    public MinimiseChoicesPlayer(final Game game, final boolean hardMode) {
        super(game, "RAISE", hardMode);
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

            // Pick the word that uses the most common remaining letters to minimise future choices
            return getOptions().stream()
                    .max(Comparator.comparing(w -> score(w, availableLetters)))
                    .get();
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
