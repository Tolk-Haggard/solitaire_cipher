package com.asolutions.samples.cryptography.ciphers.crackerjackprizes;

import com.asolutions.samples.cryptography.ciphers.playingcards.Card;
import com.asolutions.samples.cryptography.ciphers.playingcards.Rank;
import com.asolutions.samples.cryptography.ciphers.playingcards.Suit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class DecoderRingTest {

    @Mock
    Card mockCard;

    private DecoderRing testObject;

    @Before
    public void setUp() throws Exception {
        testObject = new DecoderRing();
    }

    @Test
    public void testCipher() throws Exception {

        when(mockCard.getValue()).thenReturn(1).thenReturn(20).thenReturn(30).thenReturn(40);

        assertEquals((Character)DecoderRing.alphabet.charAt(25), testObject.cipher('A', mockCard));
        assertEquals((Character)DecoderRing.alphabet.charAt(26 - 20), testObject.cipher('A', mockCard));
        assertEquals((Character)DecoderRing.alphabet.charAt(52 - 30), testObject.cipher('A', mockCard));
        assertEquals((Character)DecoderRing.alphabet.charAt(52 - 40), testObject.cipher('A', mockCard));

    }
}
