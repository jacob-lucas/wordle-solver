package com.jacoblucas.wordle.game;

public enum ResultCode {
    // The letter is not in the word in any spot.
    NEVER,

    // The letter is in the word and in the correct spot.
    RIGHT,

    // The letter is in the word but in the wrong spot.
    WRONG
}
