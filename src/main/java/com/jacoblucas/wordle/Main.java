package com.jacoblucas.wordle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Main {
    private static final int GAMES = 10000;

    public static void main(String[] args) {
        final Game game = new Game();
        play(game, new SmartPlayer(game), new TwoStartWordsPlayer(game));
    }

    public static void play(final Game game, final Player ...players) {
        final Map<String, Map<Integer, Integer>> resultsByPlayer = new HashMap<>();
        Arrays.stream(players).forEach(p -> resultsByPlayer.put(p.getName(), new HashMap<>()));

        for (int i = 0; i < GAMES; i++) {
            game.newGame();
            for (final Player player : players) {
                final int guesses = player.play();
                final Map<Integer, Integer> results = resultsByPlayer.get(player.getName());
                results.put(guesses, results.getOrDefault(guesses, 0) + 1);
                resultsByPlayer.put(player.getName(), results);
            }
        }

        Arrays.stream(players).forEach(player -> {
            final Map<Integer, Integer> results = resultsByPlayer.get(player.getName());
            System.out.println(player.getName() + " guess distribution over " + GAMES + " games:");
            IntStream.range(1, 7).forEach(i -> System.out.println(i + ": " + results.getOrDefault(i,0) + " (" + pct(results.getOrDefault(i,0), GAMES) + "%)"));
            System.out.println("X: " + results.get(-1) + " (" + pct(results.get(-1), GAMES) + "%)");
        });
    }

    private static double pct(final int i, final int total) {
        return ((double) i) * 100 / total;
    }
}
