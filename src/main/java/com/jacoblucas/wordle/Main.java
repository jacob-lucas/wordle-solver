package com.jacoblucas.wordle;

import com.jacoblucas.wordle.game.Dictionary;
import com.jacoblucas.wordle.game.Game;
import com.jacoblucas.wordle.players.DumbPlayer;
import com.jacoblucas.wordle.players.MinimiseChoicesPlayer;
import com.jacoblucas.wordle.players.Player;
import com.jacoblucas.wordle.players.SmartPlayer;
import com.jacoblucas.wordle.players.TwoStartWordsPlayer;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Main {
    private static final int GAMES = Dictionary.WORDS.size();

    public static void main(String[] args) {
        final Game game = new Game();
        play(game,
                new DumbPlayer(game),
                new TwoStartWordsPlayer(game),
                new SmartPlayer(game, "SMART", true),
                new MinimiseChoicesPlayer(game, true)
        );
    }

    public static void play(final Game game, final Player...players) {
        final Map<String, Map<Integer, Integer>> resultsByPlayer = new HashMap<>();
        Arrays.stream(players).forEach(p -> resultsByPlayer.put(p.getName(), new HashMap<>()));

        IntStream.range(0, GAMES).forEach(i -> {
            game.newGame();
            game.setWord(Dictionary.WORDS.get(i));
            Arrays.stream(players).forEach(player -> {
                final int guesses = player.play();
                final Map<Integer, Integer> results = resultsByPlayer.get(player.getName());
                results.put(guesses, results.getOrDefault(guesses, 0) + 1);
                resultsByPlayer.put(player.getName(), results);
            });
        });

        Player winner = null;
        int bestScore = 0;
        final DecimalFormat decimalFormat = new DecimalFormat("##.00");
        for (final Player player : players) {
            final Map<Integer, Integer> results = resultsByPlayer.get(player.getName());
            System.out.println(player.getName() + " (hard mode = " + player.getHardMode() + ") guess distribution over " + GAMES + " games:");
            IntStream.range(1, 7).forEach(i -> System.out.println(i + ": " + results.getOrDefault(i,0) + " (" + decimalFormat.format(pct(results.getOrDefault(i,0), GAMES)) + "%)"));
            System.out.println("X: " + results.get(-1) + " (" + decimalFormat.format(pct(results.get(-1), GAMES)) + "%)");

            int score = score(results);
            if (score > bestScore) {
                winner = player;
                bestScore = score;
            }
            System.out.println("Score = " + score + "\n");
        }

        System.out.println("The winner is " + winner.getName() + " with a score of " + bestScore);
    }

    private static double pct(final int i, final int total) {
        return ((double) i) * 100 / total;
    }

    private static int score(final Map<Integer, Integer> results) {
        int score = 0;
        int maxPoints = 6;
        for (int i = 1; i <= maxPoints; i++) {
            score += (maxPoints - i + 1) * results.getOrDefault(i, 0);
        }
        return score;
    }

}
