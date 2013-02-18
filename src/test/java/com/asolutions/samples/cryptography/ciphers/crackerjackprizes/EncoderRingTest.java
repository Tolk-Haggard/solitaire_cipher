package com.asolutions.samples.cryptography.ciphers.crackerjackprizes;

import com.asolutions.samples.cryptography.ciphers.playingcards.Card;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class EncoderRingTest {
    @Mock
    Card mockCard;

    private EncoderRing testObject;

    @Before
    public void setUp() throws Exception {
        testObject = new EncoderRing();
    }

    @Test
    public void testCipher() throws Exception {

        when(mockCard.getValue()).thenReturn(10).thenReturn(20).thenReturn(30).thenReturn(40).thenReturn(25);

        assertEquals((Character)DecoderRing.alphabet.charAt(10), testObject.cipher('A', mockCard));
        assertEquals((Character)DecoderRing.alphabet.charAt(20), testObject.cipher('A', mockCard));
        assertEquals((Character)DecoderRing.alphabet.charAt(30 - 26), testObject.cipher('A', mockCard));
        assertEquals((Character)DecoderRing.alphabet.charAt(40 - 26), testObject.cipher('A', mockCard));
        assertEquals((Character)DecoderRing.alphabet.charAt(25), testObject.cipher('A', mockCard));

    }
}
