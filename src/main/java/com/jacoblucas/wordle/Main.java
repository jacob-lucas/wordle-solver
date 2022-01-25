package com.jacoblucas.wordle;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Main {
    private static final int GAMES = 10000;

    public static void main(String[] args) {
        final Game game = new Game();
        final Player player = new SmartPlayer(game);
        final Map<Integer, Integer> results = new HashMap<>();
        for (int i = 0; i < GAMES; i++) {
            game.newGame();
            final int guesses = player.play();
            results.put(guesses, results.getOrDefault(guesses, 0) + 1);
        }

        System.out.println("Guess distribution over " + GAMES + " games:");
        IntStream.range(1, 7).forEach(i -> System.out.println(i + ": " + results.get(i) + " (" + ((double)results.get(i)) * 100 / GAMES + "%)"));
        System.out.println("X: " + results.get(-1) + " (" + ((double)results.get(-1)) * 100 / GAMES + "%)");
    }
}
