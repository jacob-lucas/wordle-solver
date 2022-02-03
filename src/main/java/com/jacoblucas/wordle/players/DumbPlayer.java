package com.jacoblucas.wordle.players;

import com.jacoblucas.wordle.game.Game;

import java.util.ArrayDeque;
import java.util.Queue;

public class DumbPlayer extends Player {
    private final Queue<String> guesses;

    public DumbPlayer(final Game game) {
        super(game);
        guesses = new ArrayDeque<>();
        guesses.add("RAISE");
        guesses.add("MOUTH");
        guesses.add("LANKY");
        guesses.add("BRICK");
        guesses.add("ZEBRA");
        guesses.add("BLAND");
        guesses.add("CREST");
    }

    @Override
    public String getName() {
        return "DumbPlayer";
    }

    @Override
    public String getNextWord() {
        final String guess = guesses.remove();
        guesses.add(guess);
        return guess;
    }
}
