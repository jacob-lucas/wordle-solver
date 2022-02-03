package com.jacoblucas.wordle.players;

import com.jacoblucas.wordle.game.Game;
import com.jacoblucas.wordle.game.ResultCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static com.jacoblucas.wordle.game.Game.MAX_GUESSES;
import static com.jacoblucas.wordle.game.ResultCode.RIGHT;
import static com.jacoblucas.wordle.game.ResultCode.WRONG;

public abstract class Player {
    public static final ResultCode[] FOUND = {RIGHT, RIGHT, RIGHT, RIGHT, RIGHT};
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    protected final Game game;
    protected final boolean hardMode;
    protected LinkedHashMap<String, ResultCode[]> guessHistory;

    public Player(final Game game) {
        this(game, false);
    }

    public Player(final Game game, final boolean hardMode) {
        this.game = game;
        this.hardMode = hardMode;
        this.guessHistory = new LinkedHashMap<>();
    }

    public abstract String getName();

    public abstract String getNextWord();

    public boolean getHardMode() {
        return hardMode;
    }

    public int play() {
        guessHistory = new LinkedHashMap<>();
        int guesses = 0;
        boolean foundWord = false;
        String word;
        while (guesses < MAX_GUESSES && !foundWord) {
            word = getNextWord();
            final ResultCode[] resultCodes = guess(word);
            guesses++;
            foundWord = Arrays.deepEquals(FOUND, resultCodes);
        }

        return foundWord ? guesses : -1;
    }

    public ResultCode[] guess(final String word) {
        try {
            validateHardMode(word);
            final ResultCode[] resultCodes = game.check(word);
            guessHistory.put(word, resultCodes);
            return resultCodes;
        } catch (final IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
            throw iae;
        }
    }

    private void validateHardMode(final String word) {
        if (!hardMode) {
            return;
        }

        final Map<Character, Integer> rightLetters = new HashMap<>();
        guessHistory.forEach(getStringBiConsumer(rightLetters, RIGHT));

        final Map<Character, Integer> wrongLetters = new HashMap<>();
        guessHistory.forEach(getStringBiConsumer(wrongLetters, WRONG));

        rightLetters.forEach((c, i) -> {
            if (word.charAt(i) != c) {
                throw new IllegalArgumentException("Character at index " + i + " must be '" + c.toString().toUpperCase() + "'");
            }
        });

        wrongLetters.forEach((c, i) -> {
            final String ch = c.toString().toUpperCase();
            if (!word.contains(ch)) {
                throw new IllegalArgumentException("Guess must contain '" + ch + "'");
            }
        });
    }

    private BiConsumer<String, ResultCode[]> getStringBiConsumer(Map<Character, Integer> letterMap, final ResultCode resultCode) {
        return (w, rcs) -> {
            for (int i = 0; i < rcs.length; i++) {
                if (rcs[i] == resultCode) {
                    letterMap.put(w.charAt(i), i);
                }
            }
        };
    }
}
