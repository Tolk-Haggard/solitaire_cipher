package com.asolutions.samples.cryptography.ciphers.crackerjackprizes;

import com.asolutions.samples.cryptography.ciphers.playingcards.Card;

/**
 * Decoder ring used to convert card and letter combinations into encoded letters
 * @author Brian Haggard
 */
public class DecoderRing extends Ring {

    /**
     * Accepts an encoded character and a playing card which is used to decode the character
     * @param character  a character [A-Z] to be decoded
     * @param card       the card from a solitaire cipher deck to use in decoding
     * @return           the decoded character [A-Z]
     */
    @Override
    public Character cipher(Character character, Card card) {
        int characterValue = alphabet.indexOf(character);
        return alphabet.charAt((characterValue - card.getValue() + 78) % 26);
    }
}
