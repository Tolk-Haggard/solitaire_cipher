package com.asolutions.samples.cryptography.ciphers.crackerjackprizes;

import com.asolutions.samples.cryptography.ciphers.playingcards.Card;

/**
 * Encoder ring used to convert card and letter combinations into encoded letters
 * @author Brian Haggard
 */
public class EncoderRing extends Ring {

    /**
     * Accepts an character and a playing card which is used to encode the character
     * @param character  a character [A-Z] to be encoded
     * @param card       the card from a solitaire cipher deck to use in encoding
     * @return           the encoded character [A-Z]
     */
    @Override
    public Character cipher(Character character, Card card) {
        int characterValue = alphabet.indexOf(character);
        return alphabet.charAt((characterValue + card.getValue()) % 26);
    }
}
