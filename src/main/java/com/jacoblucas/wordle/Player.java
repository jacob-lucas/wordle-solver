package com.jacoblucas.wordle;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static com.jacoblucas.wordle.Game.MAX_GUESSES;
import static com.jacoblucas.wordle.ResultCode.RIGHT;

public abstract class Player {
    public static final ResultCode[] FOUND = {RIGHT, RIGHT, RIGHT, RIGHT, RIGHT};

    protected final Game game;
    protected LinkedHashMap<String, ResultCode[]> guessHistory;

    public Player(final Game game) {
        this.game = game;
        this.guessHistory = new LinkedHashMap<>();
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
            final ResultCode[] resultCodes = game.check(word);
            guessHistory.put(word, resultCodes);
            return resultCodes;
        } catch (final IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
            throw iae;
        }
    }

    public abstract String getName();

    public abstract String getNextWord();
}
