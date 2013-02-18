package com.asolutions.samples.cryptography.ciphers;

import com.asolutions.samples.cryptography.ciphers.crackerjackprizes.DecoderRing;
import com.asolutions.samples.cryptography.ciphers.crackerjackprizes.EncoderRing;
import com.asolutions.samples.cryptography.ciphers.playingcards.Deck;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static junit.framework.Assert.*;


public class SolitaireAcceptanceTest {

    private Random random;
    private Deck sortedDeck;
    private Solitaire testObject;


    @Before
    public void setUp() throws Exception {
        random = new Random();
        sortedDeck = new Deck();
        sortedDeck.sort(true);
        testObject = new Solitaire(sortedDeck, new DecoderRing(), new EncoderRing());
    }

    @Test
    public void testSampleMessages() throws Exception {
        String sampleUnencryptedMessage1 = "CODEI NRUBY LIVEL ONGER";
        String sampleUnencryptedMessage2 = "YOURC IPHER ISWOR KINGX";
        String sampleUnencryptedMessage3 = "WELCO METOR UBYQU IZXXX";
        String sampleEncryptedMessage1 = "GLNCQ MJAFF FVOMB JIYCB";
        String sampleEncryptedMessage2 = "CLEPK HHNIY CFPWH FDFEH";
        String sampleEncryptedMessage3 = "ABVAW LWZSY OORYK DUPVH";

        assertEquals(sampleEncryptedMessage1, testObject.encrypt(sampleUnencryptedMessage1));
        assertEquals(sampleEncryptedMessage2, testObject.encrypt(sampleUnencryptedMessage2));
        assertEquals(sampleEncryptedMessage3, testObject.encrypt(sampleUnencryptedMessage3));

        assertEquals(sampleUnencryptedMessage1, testObject.decrypt(sampleEncryptedMessage1));
        assertEquals(sampleUnencryptedMessage2, testObject.decrypt(sampleEncryptedMessage2));
        assertEquals(sampleUnencryptedMessage3, testObject.decrypt(sampleEncryptedMessage3));

    }

    @Test
    public void testShuffledDeckEncryptsDifferentlyThanSortedDeck() throws Exception {

        Deck shuffledDeck = new Deck();
        int i = 0;
        while (shuffledDeck.equals(sortedDeck)) {
            shuffledDeck.shuffle();
            if(i++ > 1000) {
                fail("I'm tired of shuffling");
            }
        }
        Solitaire shuffledSolitareCipher = new Solitaire(shuffledDeck, new DecoderRing(), new EncoderRing());
        String unencryptedString = "ABCDE FGHIJ KLMNO PQRST UVWXY ZXXXX";

        String sortedEncryption = testObject.encrypt(unencryptedString);
        String shuffledEncryption = shuffledSolitareCipher.encrypt(unencryptedString);

        assertNotNull(sortedEncryption);
        assertNotNull(shuffledEncryption);
        assertFalse(sortedEncryption.equals(shuffledEncryption));
        assertEquals(testObject.decrypt(sortedEncryption), shuffledSolitareCipher.decrypt(shuffledEncryption));

    }

    @Test
    public void testTwoSolitareCiphersWithTheSameDeckCanDecryptEachOthersMessages() throws Exception {

        Deck deck1 = new Deck();
        deck1.shuffle();
        Deck deck2 = deck1.copy();
        Solitaire testObject1 = new Solitaire(deck1, new DecoderRing(), new EncoderRing());
        Solitaire testObject2 = new Solitaire(deck2, new DecoderRing(), new EncoderRing());

        for (int i = 0; i < 100; i++) {
            testCommunication(testObject1, testObject2);
            testCommunication(testObject2, testObject1);
        }

    }

    private void testCommunication(Solitaire testObjectA, Solitaire testObjectB) {
        String expected = testObjectA.formatMessage(buildRandomMessage());
        String encrypted = testObjectA.encrypt(expected);
        assertEquals(expected, testObjectB.decrypt(encrypted));
    }

    private String buildRandomMessage() {

        int stringSize = random.nextInt(99) + 1;
        StringBuilder sb = new StringBuilder("Z");

        for (int i = 0; i < stringSize; i++) {
           sb.append(Integer.toString(random.nextInt(35) + 1, 36));
        }

        return sb.toString();  //To change body of created methods use File | Settings | File Templates.
    }
}
