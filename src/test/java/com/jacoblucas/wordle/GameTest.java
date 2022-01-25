package com.jacoblucas.wordle;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.jacoblucas.wordle.Player.FOUND;
import static com.jacoblucas.wordle.ResultCode.NEVER;
import static com.jacoblucas.wordle.ResultCode.RIGHT;
import static com.jacoblucas.wordle.ResultCode.WRONG;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameTest {
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidWord() {
        game.check("ABCDE");
    }

    @Test
    public void testCheckAllWrong() {
        game.setWord("TABLE");
        final ResultCode[] resultCodes = game.check("GUMMY");
        assertThat(Arrays.deepEquals(resultCodes, new ResultCode[]{NEVER, NEVER, NEVER, NEVER, NEVER}), is(true));
    }

    @Test
    public void testCheckNoneRightSomeWrong() {
        game.setWord("TABLE");
        final ResultCode[] resultCodes = game.check("MOUTH");
        assertThat(Arrays.deepEquals(resultCodes, new ResultCode[]{NEVER, NEVER, NEVER, WRONG, NEVER}), is(true));
    }

    @Test
    public void testCheckSomeRightSomeWrong() {
        game.setWord("TABLE");
        final ResultCode[] resultCodes = game.check("ZEBRA");
        assertThat(Arrays.deepEquals(resultCodes, new ResultCode[]{NEVER, WRONG, RIGHT, NEVER, WRONG}), is(true));
    }

    @Test
    public void testCheckAllRight() {
        game.setWord("TABLE");
        final ResultCode[] resultCodes = game.check("TABLE");
        assertThat(Arrays.deepEquals(resultCodes, FOUND), is(true));
    }
}
