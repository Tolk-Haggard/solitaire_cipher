package com.asolutions.samples.cryptography.ciphers.crackerjackprizes;


import com.asolutions.samples.cryptography.ciphers.playingcards.Card;

/**
 * Base class for Encoder and Decoder rings
 * @author Brian Haggard
 */
public abstract class Ring {
    /**
     * List of upper case characters in alphabetical order for convenience
     */
    protected static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public abstract Character cipher(Character character, Card card);
}
