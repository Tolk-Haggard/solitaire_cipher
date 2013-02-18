package com.asolutions.samples.cryptography.ciphers.playingcards;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static junit.framework.Assert.*;


public class DeckTest {

    private Random random;
    private Deck testObject;

    @Before
    public void setUp() throws Exception {
        random = new Random();
        testObject = new Deck();
    }

    @Test
    public void testDefaultConstructorCreatesStandard54CardDeck() throws Exception {
        List<Card> cards = testObject.getCards();
        assertNotNull(cards);
        assertEquals(cards.size(), 54);
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                if (!(suit.getValue().equals(53) || rank.equals(Rank.JOKER))) {
                    Card card = new Card(suit, rank);
                    assertTrue(cards.contains(card));
                }
            }
        }
        assertTrue(cards.contains(new Card(Suit.JOKER_A, Rank.JOKER)));
        assertTrue(cards.contains(new Card(Suit.JOKER_B, Rank.JOKER)));
    }

    @Test
    public void testInternalCardListsAreInTheSameOrderAfterConstruction() throws Exception {
        List<Card> cards = testObject.getCards();
        List<Card> originalOrderCards = testObject.getOriginalOrderCards();
        assertNotNull(testObject.getCards());
        assertNotNull(testObject.getOriginalOrderCards());
        for (int i = 0; i < cards.size(); i++) {
            assertTrue(cards.get(i).equals(originalOrderCards.get(i)));
        }

    }

    @Test
    public void testCountCardsReturnsNumberOfCardsInDeck() throws Exception {

        Integer actual = testObject.countCards();

        assertNotNull(actual);
        assertNotNull(testObject.getCards());
        assertNotNull(testObject.getOriginalOrderCards());
        assertEquals((Integer) testObject.getCards().size(), actual);
        assertEquals((Integer) testObject.getOriginalOrderCards().size(), actual);

    }

    @Test
    public void testShuffleChangesTheOrderOfInteralCardLists() throws Exception {

        List<Card> originalOrder = new ArrayList<Card>();
        originalOrder.addAll(testObject.getCards());

        testObject.shuffle();

        assertFalse(originalOrder.equals(testObject.getCards()));
        assertFalse(originalOrder.equals(testObject.getOriginalOrderCards()));

    }

    @Test
    public void testShuffleSufficientlyRandomizes() throws Exception {

        Integer differencesFound = 0;
        Integer cardsCompared = 0;
        List<Card> originalOrder = new ArrayList<Card>();
        List<Card> lastRun = new ArrayList<Card>();
        originalOrder.addAll(testObject.getCards());
        lastRun.addAll(testObject.getCards());


        for (int i = 0; i < 1000; i++) {
            testObject.shuffle();
            List<Card> shuffledCards = testObject.getCards();
            for (int j = 0; j < originalOrder.size(); j++) {
                cardsCompared++;
                if (!(originalOrder.get(j).equals(shuffledCards.get(j)) && lastRun.get(j).equals(shuffledCards.get(j)))) {
                    differencesFound++;
                }
            }
            lastRun.clear();
            lastRun.addAll(shuffledCards);
        }

        assertTrue(((double) differencesFound / cardsCompared) > .8);

    }


    @Test
    public void testSortOrdersTheCardsAfterShuffle() throws Exception {

        List<Card> originalOrder = new ArrayList<Card>();
        originalOrder.addAll(testObject.getCards());

        testObject.shuffle();
        testObject.sort(true);

        assertEquals(originalOrder, testObject.getCards());

    }

    @Test
    public void testSortDescending() throws Exception {

        List<Card> originalOrder = new ArrayList<Card>();
        originalOrder.addAll(testObject.getCards());

        testObject.shuffle();
        testObject.sort(false);

        List<Card> cards = testObject.getCards();
        for (int i = 0; i < originalOrder.size(); i++) {
            assertEquals(originalOrder.get(i), cards.get(53 - i));
        }

    }

    @Test
    public void testMoveCardDown() throws Exception {

        testObject.sort(true);

        for (int i = 0; i < 100; i++) {
            int cardLocation = random.nextInt(53);
            int moveDown = random.nextInt(9) + 1;
            int cardDestination = (cardLocation + moveDown) % 54;
            Card card = testObject.getCards().get(cardLocation);
            int displacedIndex;
            Card displacedCard = testObject.getCards().get(cardDestination);
            if (cardDestination < moveDown) {
                cardDestination++;
            }
            displacedIndex = cardDestination - 1;

            testObject.moveCardDown(card, moveDown);

            assertEquals(card, testObject.getCards().get(cardDestination));
            assertEquals(displacedCard, testObject.getCards().get(displacedIndex));
        }
    }

    @Test
    public void testTripleCut() throws Exception {

        List<Card> cards = testObject.getCards();
        int firstCardIndex = random.nextInt(10) + 10;
        int secondCardIndex = random.nextInt(10) + 30;
        Card firstCard = cards.get(firstCardIndex);
        Card secondCard = cards.get(secondCardIndex);

        List<Card> expected = new ArrayList<Card>();
        expected.addAll(cards.subList(secondCardIndex + 1, cards.size()));
        expected.addAll(cards.subList(firstCardIndex, secondCardIndex + 1));
        expected.addAll(cards.subList(0, firstCardIndex));

        testObject.tripleCut(firstCard, secondCard);

        assertEquals(expected, testObject.getCards());

        testObject.restore();

        testObject.tripleCut(secondCard, firstCard);

        assertEquals(expected, testObject.getCards());

    }

    @Test
    public void testBottomValueCut() throws Exception {

        testObject.shuffle();
        List<Card> cardsBeforeCut = new ArrayList<Card>();
        cardsBeforeCut.addAll(testObject.getCards());
        Card bottomCard = cardsBeforeCut.get(cardsBeforeCut.size() - 1);
        Integer bottomCardValue = bottomCard.getValue();

        testObject.bottomValueCut();

        List<Card> cardsAfterCut = testObject.getCards();

        assertEquals(bottomCard, cardsAfterCut.get(cardsAfterCut.size() - 1));
        assertEquals(cardsBeforeCut.subList(bottomCardValue, cardsBeforeCut.size() - 1), cardsAfterCut.subList(0, cardsAfterCut.size() - bottomCardValue - 1));
        assertEquals(cardsBeforeCut.subList(0, bottomCardValue), cardsAfterCut.subList(cardsAfterCut.size() - bottomCardValue - 1, cardsAfterCut.size() - 1));

    }


    @Test
    public void testGetNextCard() throws Exception {

        testObject.sort(true);

        //Examples for a sorted deck from www.rubyquiz.com
        //D (4)  W (49)  J (10)  Skip Joker (53)  X (24)  H (8)
        //Y (51)  R (44)  F (6)  D (4)  G (33)
        Card nextCard = testObject.getNextCard();
        assertNotNull(nextCard);
        assertEquals((Integer) 4, nextCard.getValue());
        assertEquals((Integer) 49, testObject.getNextCard().getValue());
        assertEquals((Integer) 10, testObject.getNextCard().getValue());
        assertEquals((Integer) 24, testObject.getNextCard().getValue());
        assertEquals((Integer) 8, testObject.getNextCard().getValue());
        assertEquals((Integer) 51, testObject.getNextCard().getValue());
        assertEquals((Integer) 44, testObject.getNextCard().getValue());
        assertEquals((Integer) 6, testObject.getNextCard().getValue());
        assertEquals((Integer) 4, testObject.getNextCard().getValue());
        assertEquals((Integer) 33, testObject.getNextCard().getValue());


    }

    @Test
    public void testRestoreReturnsCardsListToOriginalState() throws Exception {

        List<Card> originalOrderCards = testObject.getOriginalOrderCards();

        testObject.moveCardDown(new Card(Suit.HEARTS, Rank.QUEEN), 10);

        assertFalse(originalOrderCards.equals(testObject.getCards()));

        testObject.restore();

        assertEquals(originalOrderCards, testObject.getCards());

    }

    @Test
    public void testCopy() throws Exception {

        Deck result = testObject.copy();

        assertNotNull(result);
        assertFalse(result == testObject);
        assertEquals(testObject.getCards(), result.getCards());

    }
}
