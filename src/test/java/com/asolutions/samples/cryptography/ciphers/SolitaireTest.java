package com.asolutions.samples.cryptography.ciphers;

import com.asolutions.samples.cryptography.ciphers.crackerjackprizes.DecoderRing;
import com.asolutions.samples.cryptography.ciphers.crackerjackprizes.EncoderRing;
import com.asolutions.samples.cryptography.ciphers.playingcards.Card;
import com.asolutions.samples.cryptography.ciphers.playingcards.Deck;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Solitaire.class)
public class SolitaireTest {

    @Mock
    Deck mockDeck;
    @Mock
    DecoderRing mockDecoderRing;
    @Mock
    Card mockCard;
    @Mock
    EncoderRing mockEncoderRing;

    private Solitaire testObject;

    @Before
    public void setUp() throws Exception {
        testObject = new Solitaire(mockDeck, mockDecoderRing, mockEncoderRing);
    }

    @Test
    public void testDefaultConstructorUsesShuffledDeck() throws Exception {
        whenNew(Deck.class).withNoArguments().thenReturn(mockDeck);
        Solitaire testedObject = new Solitaire();

        verify(mockDeck).shuffle();
    }

    @Test
    public void testFormatMessage() throws Exception {
        String unformattedMessage1 = "This is a message!";
        String unformattedMessage2 = "a!@#$%";
        String unformattedMessage3 = "The quick brown fox jumped over the lazy dog";
        String unformattedMessage4 = "1234a";
        String unformattedMessage5 = "aa";
        String unformattedMessage6 = "aaa";
        String unformattedMessage7 = "aaaa";
        String unformattedMessage8 = "aaaaa";
        String unformattedMessage9 = "aaaaaa";
        String unformattedMessage10 = "aaaaaaa";
        String unformattedMessage11 = "aaaaaaaa";
        String unformattedMessage12 = "aaaaaaaaa";
        String unformattedMessage13 = "aaaaaaaaaa";
        String unformattedMessage14 = "aaaaaaaaaaa";
        String unformattedMessage15 = "aaaaaaaaaaaa";
        String formattedMessage1 = "THISI SAMES SAGEX";
        String formattedMessage2 = "AXXXX";
        String formattedMessage3 = "THEQU ICKBR OWNFO XJUMP EDOVE RTHEL AZYDO GXXXX";
        String formattedMessage4 = "AXXXX";
        String formattedMessage5 = "AAXXX";
        String formattedMessage6 = "AAAXX";
        String formattedMessage7 = "AAAAX";
        String formattedMessage8 = "AAAAA";
        String formattedMessage9 = "AAAAA AXXXX";
        String formattedMessage10 = "AAAAA AAXXX";
        String formattedMessage11 = "AAAAA AAAXX";
        String formattedMessage12 = "AAAAA AAAAX";
        String formattedMessage13 = "AAAAA AAAAA";
        String formattedMessage14 = "AAAAA AAAAA AXXXX";
        String formattedMessage15 = "AAAAA AAAAA AAXXX";

        assertEquals(formattedMessage1, testObject.formatMessage(unformattedMessage1));
        assertEquals(formattedMessage2, testObject.formatMessage(unformattedMessage2));
        assertEquals(formattedMessage3, testObject.formatMessage(unformattedMessage3));
        assertEquals(formattedMessage4, testObject.formatMessage(unformattedMessage4));
        assertEquals(formattedMessage5, testObject.formatMessage(unformattedMessage5));
        assertEquals(formattedMessage6, testObject.formatMessage(unformattedMessage6));
        assertEquals(formattedMessage7, testObject.formatMessage(unformattedMessage7));
        assertEquals(formattedMessage8, testObject.formatMessage(unformattedMessage8));
        assertEquals(formattedMessage9, testObject.formatMessage(unformattedMessage9));
        assertEquals(formattedMessage10, testObject.formatMessage(unformattedMessage10));
        assertEquals(formattedMessage11, testObject.formatMessage(unformattedMessage11));
        assertEquals(formattedMessage12, testObject.formatMessage(unformattedMessage12));
        assertEquals(formattedMessage13, testObject.formatMessage(unformattedMessage13));
        assertEquals(formattedMessage14, testObject.formatMessage(unformattedMessage14));
        assertEquals(formattedMessage15, testObject.formatMessage(unformattedMessage15));
    }

    @Test
    public void testDecrypt() throws Exception {

        String unencryptedMessage = "aaa bbb";
        InOrder inOrder = inOrder(mockDeck);
        when(mockDeck.getNextCard()).thenReturn(mockCard);
        when(mockDecoderRing.cipher('A', mockCard)).thenReturn('J');
        when(mockDecoderRing.cipher('B', mockCard)).thenReturn('K');
        when(mockDecoderRing.cipher('X', mockCard)).thenReturn('L');
        when(mockDecoderRing.cipher(' ', mockCard)).thenReturn(' ');

        String result = testObject.decrypt(unencryptedMessage);

        assertNotNull(result);
        inOrder.verify(mockDeck).restore();
        inOrder.verify(mockDeck, times(10)).getNextCard();
        assertEquals("JJJKK KLLLL", result);

    }

    @Test
    public void testEncrypt() throws Exception {
        String encryptedMessage = "aaa bbb";
        InOrder inOrder = inOrder(mockDeck);
        when(mockDeck.getNextCard()).thenReturn(mockCard);
        when(mockEncoderRing.cipher('A', mockCard)).thenReturn('J');
        when(mockEncoderRing.cipher('B', mockCard)).thenReturn('K');
        when(mockEncoderRing.cipher('X', mockCard)).thenReturn('L');
        when(mockEncoderRing.cipher(' ', mockCard)).thenReturn(' ');

        String result = testObject.encrypt(encryptedMessage);

        assertNotNull(result);
        inOrder.verify(mockDeck).restore();
        inOrder.verify(mockDeck, times(10)).getNextCard();
        assertEquals("JJJKK KLLLL", result);
    }
}
