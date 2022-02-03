package com.jacoblucas.wordle.utils;

import com.jacoblucas.wordle.game.ResultCode;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static com.jacoblucas.wordle.game.ResultCode.NEVER;
import static com.jacoblucas.wordle.game.ResultCode.RIGHT;
import static com.jacoblucas.wordle.game.ResultCode.WRONG;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class GuessHistoryAnalysisTest {

    @Test
    public void testGetAvailableLetters() {
        final LinkedHashMap<String, ResultCode[]> guessHistory = new LinkedHashMap<>();
        guessHistory.put("RAISE", new ResultCode[]{NEVER, RIGHT, WRONG, NEVER, NEVER});
        guessHistory.put("MOUTH", new ResultCode[]{NEVER, NEVER, NEVER, NEVER, NEVER});
        guessHistory.put("LANKY", new ResultCode[]{NEVER, NEVER, NEVER, NEVER, NEVER});

        final List<Character> availableLetters = GuessHistoryAnalysis.getAvailableLetters(guessHistory);
        assertThat(availableLetters, containsInAnyOrder('B','C','D','F','G','I','J','P','Q','V','W','X','Z'));
    }

    @Test
    public void testGetWrongLetters() {
        final LinkedHashMap<String, ResultCode[]> guessHistory = new LinkedHashMap<>();
        guessHistory.put("RAISE", new ResultCode[]{NEVER, RIGHT, WRONG, NEVER, NEVER});
        guessHistory.put("MOUTH", new ResultCode[]{NEVER, NEVER, NEVER, WRONG, NEVER});
        guessHistory.put("LANKY", new ResultCode[]{NEVER, NEVER, NEVER, NEVER, WRONG});

        final List<Character> availableLetters = GuessHistoryAnalysis.getLetters(guessHistory, WRONG);
        assertThat(availableLetters, containsInAnyOrder('I','T','Y'));
    }
}
