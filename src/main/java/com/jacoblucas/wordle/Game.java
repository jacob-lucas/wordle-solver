package com.jacoblucas.wordle;

import java.util.Random;

import static com.jacoblucas.wordle.Dictionary.WORDS;
import static com.jacoblucas.wordle.ResultCode.NEVER;
import static com.jacoblucas.wordle.ResultCode.RIGHT;
import static com.jacoblucas.wordle.ResultCode.WRONG;

public class Game {
    public static final int MAX_GUESSES = 6;
    public static final int WORD_LENGTH = 5;

    private final Random random;

    private String word;

    public Game() {
        random = new Random();
        newGame();
    }

    public void newGame() {
        setWord(WORDS.get(random.nextInt(WORDS.size())));
    }

    public void setWord(final String word) {
        this.word = word;
    }

    public ResultCode[] check(final String str) {
        final String guess = str.toUpperCase();
        if (!WORDS.contains(guess)) {
            throw new IllegalArgumentException(guess + " is not a valid word");
        }

        ResultCode[] result = new ResultCode[WORD_LENGTH];
        for (int i = 0; i < WORD_LENGTH; i++) {
            char letter = guess.charAt(i);
            ResultCode rc;
            if (!word.contains(""+letter)) {
                rc = NEVER;
            } else {
                rc = word.charAt(i) == letter ? RIGHT : WRONG;
            }
            result[i] = rc;
        }
        return result;
    }
}
