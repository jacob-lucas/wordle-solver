package com.jacoblucas.wordle;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Main {
    private static final int GAMES = 10000;

    public static void main(String[] args) {
        final Game game = new Game();
        play(new SmartPlayer(game), game);
    }

    public static void play(final Player player, final Game game) {
        final Map<Integer, Integer> results = new HashMap<>();
        for (int i = 0; i < GAMES; i++) {
            game.newGame();
            final int guesses = player.play();
            results.put(guesses, results.getOrDefault(guesses, 0) + 1);
        }

        System.out.println(player.getName() + " guess distribution over " + GAMES + " games:");
        IntStream.range(1, 7).forEach(i -> System.out.println(i + ": " + results.getOrDefault(i,0) + " (" + pct(results.getOrDefault(i,0), GAMES) + "%)"));
        System.out.println("X: " + results.get(-1) + " (" + pct(results.get(-1), GAMES) + "%)");
    }

    private static double pct(final int i, final int total) {
        return ((double) i) * 100 / total;
    }
}
