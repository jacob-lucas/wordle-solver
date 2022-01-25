package com.jacoblucas.wordle;

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
        return guesses.remove();
    }
}
