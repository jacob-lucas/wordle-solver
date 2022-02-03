package com.jacoblucas.wordle.players;

import com.jacoblucas.wordle.game.Game;

public class TwoStartWordsPlayer extends SmartPlayer {
    public TwoStartWordsPlayer(final Game game) {
        super(game, "RAISE");
    }

    @Override
    public String getName() {
        return "TwoStartWordsPlayer";
    }

    @Override
    public String getNextWord() {
        if (guessHistory.isEmpty()) {
            // Start with a good guess
            return startWord;
        } else if (guessHistory.size() == 1) {
            return "MOUTH";
        }

        // Any guess will do
        return getOptions().get(0);
    }
}
