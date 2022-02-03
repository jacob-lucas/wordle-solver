package com.jacoblucas.wordle.players;

import com.jacoblucas.wordle.game.Game;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
        game.setWord("ALERT");
    }

    @Test
    public void testHardModeOffIgnoresRules() {
        final Player player = new SmartPlayer(game, "HOIST", false);
        player.guess("HOIST");
        player.guess("ZEBRA");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHardModeBlocksGuessWithoutRightLetters() {
        final Player player = new SmartPlayer(game, "HOIST", true);
        player.guess("HOIST");
        player.guess("CLUCK"); // should include the T
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHardModeBlocksGuessWithoutWrongLetters() {
        final Player player = new SmartPlayer(game, "DROOP", true);
        player.guess("DROOP");
        player.guess("CLUCK"); // should include the R
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHardModeBlocksGuessWithoutAllRevealedLetters() {
        final Player player = new SmartPlayer(game, "WAIST", true);
        player.guess("WAIST");
        player.guess("CLUCK"); // should include the A, T
    }
}
