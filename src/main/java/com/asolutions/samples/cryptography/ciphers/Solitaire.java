package com.asolutions.samples.cryptography.ciphers;

import com.asolutions.samples.cryptography.ciphers.crackerjackprizes.DecoderRing;
import com.asolutions.samples.cryptography.ciphers.crackerjackprizes.EncoderRing;
import com.asolutions.samples.cryptography.ciphers.crackerjackprizes.Ring;
import com.asolutions.samples.cryptography.ciphers.playingcards.Deck;

/**
 * Class that handles encryption and decryption using
 * a Solitaire Cipher
 * @author Brian Haggard
 */
public class Solitaire {

    /**
     * A deck of cards used for encryption
     */
    private Deck deck;
    /**
     * A ring used to encode characters based on a card
     */
    private Ring decoderRing;
    /**
     * A ring used to decode characters based on a card
     */
    private Ring encoderRing;

    /**
     * Default constructor creates a Solitaire class with a shuffled deck of cards
     * for cryptography.
     */
    public Solitaire() {
        this.deck = new Deck();
        this.deck.shuffle();
        this.decoderRing = new DecoderRing();
        this.encoderRing = new EncoderRing();
    }

    /**
     * Constructor used for testing that accepts the deck and rings used for cryptography
     * @param deck           Deck of cards to use in cryptography functions
     * @param decoderRing    Decoder ring used to decode characters using a card from the deck
     * @param encoderRing    Encoder ring used to encode characters using a card from the deck
     */
    public Solitaire(Deck deck, Ring decoderRing, Ring encoderRing) {
        this.deck = deck;
        this.decoderRing = decoderRing;
        this.encoderRing = encoderRing;
    }

    /**
     * Formats a message for encryption. It removes all non-word characters, breaks the message
     * into 5 character chunks, and pads the last chunk with X's if needed.
     * @param unformattedMessage  Message to be formatted
     * @return                    Formatted message
     */
    protected String formatMessage(String unformattedMessage) {
        String scrubbedMessage = unformattedMessage.replaceAll("[^a-zA-Z]", "").toUpperCase();
        char[] chars = scrubbedMessage.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length + 4; i++) {
            if (i % 5 == 0) {
                sb.append(" ");
            }
            if (i >= chars.length) {
                sb.append("X");
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.substring(1, sb.length() - (sb.length() % 6));
    }

    /**
     * Encrypts a plain text message using a solitaire cipher
     * @param plainTextMessage   Message to be encrypted
     * @return                   Encrypted message
     */
    public String encrypt(String plainTextMessage) {
        return cipher(plainTextMessage, encoderRing);
    }

    /**
     * Decrypts an encrypted message using a solitaire cipher
     * @param encryptedMessage   Message to be decrypted
     * @return                   Decrypted message
     */
    public String decrypt(String encryptedMessage) {
        return cipher(encryptedMessage, decoderRing);
    }

    /**
     * Uses the passed in ring to perform cryptography
     * @param message   Message to be encrypted or decrypted
     * @param ring      Ring to be used
     * @return          Resulting message
     */
    private String cipher(String message, Ring ring) {
        String scrubbedMessage = formatMessage(message);
        deck.restore();
        StringBuilder sb = new StringBuilder();
        for (Character character : scrubbedMessage.toCharArray()) {
            sb.append(character == ' ' ? ' ' : ring.cipher(character, deck.getNextCard()));
        }
        return sb.toString();
    }
}
