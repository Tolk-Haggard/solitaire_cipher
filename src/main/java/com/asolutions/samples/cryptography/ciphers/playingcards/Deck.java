package com.asolutions.samples.cryptography.ciphers.playingcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A deck of cards
 * @author Brian Haggard
 */
public class Deck {
    /**
     * ArrayList of cards in the order to begin encrypting or decrypting a message
     */
    private List<Card> originalOrderCards = new ArrayList<Card>();

    /**
     * ArrayList of cards that is manipulated during encryption or decryption of a message
     */
    private List<Card> cards = new ArrayList<Card>();

    /**
     * Default constructor creates a sorted deck of 54 cards
     * Ace through King in 4 suits plus 2 jokers
     */
    public Deck() {
        sort(true);
    }

    /**
     * Private constructor used to create a deck copy that will be able to decrypt
     * messages encrypted by the original deck.
     * @param originalOrderCards    ArrayList of cards in the order to begin cryptography
     */
    private Deck(List<Card> originalOrderCards) {
        this.originalOrderCards.addAll(originalOrderCards);
        this.cards.addAll(originalOrderCards);
    }

    /**
     * Getter method for originalOrderCards used in testing
     * @return      originalOrderCards ArrayList
     */
    protected List<Card> getOriginalOrderCards() {
        return originalOrderCards;
    }

    /**
     * Getter method for cards used in testing
     * @return      cards ArrayList
     */
    protected List<Card> getCards() {
        return cards;
    }

    /**
     * Finds the card parameter in the deck and moves it down a number
     * of positions in the deck. When the bottom of the deck is reached
     * the card will instead be moved below the top card of the deck.
     * @param card                 card to be moved down
     * @param numberOfPositions    number of positions to move the card down
     */
    protected void moveCardDown(Card card, Integer numberOfPositions) {
        for (int i = 0; i < numberOfPositions; i++) {
            moveCardDown(card);
        }
    }

    /**
     * Finds the card parameter in the deck and moves it down a single
     * position in the deck. When the bottom of the deck is reached
     * the card will instead be moved below the top card of the deck.
     * @param card                 card to be moved down
     */
    protected void moveCardDown(Card card) {
        int switchIndex;
        int cardIndex = cards.indexOf(card);
        if (cardIndex == cards.size() - 1) {
            cards.remove(card);
            cards.add(1, card);
        } else {
            switchIndex = cardIndex + 1;
            Card switchCard = cards.get(switchIndex);
            cards.set(cardIndex, switchCard);
            cards.set(switchIndex, card);
        }
    }

    /**
     * Performs a triple cut of the deck. All cards above the card passed in
     * that is highest in the deck will be moved to the bottom of the deck
     * and all cards above the card passed in that is lowest in the deck will
     * be moved to the top of the deck.
     * @param firstCutCard       First card to search for when doing the triple cut
     * @param secondCutCard      Second card to search for when doing the triple cut
     */
    protected void tripleCut(Card firstCutCard, Card secondCutCard) {

        int firstCutCardIndex = cards.indexOf(firstCutCard);
        int secondCutCardIndex = cards.indexOf(secondCutCard);
        int firstCardIndex = firstCutCardIndex < secondCutCardIndex ? firstCutCardIndex : secondCutCardIndex;
        int secondCardIndex = firstCutCardIndex < secondCutCardIndex ? secondCutCardIndex : firstCutCardIndex;

        List<Card> holdCardList = new ArrayList<Card>();
        holdCardList.addAll(cards.subList(secondCardIndex + 1, cards.size()));
        holdCardList.addAll(cards.subList(firstCardIndex, secondCardIndex + 1));
        holdCardList.addAll(cards.subList(0, firstCardIndex));
        cards.clear();
        cards.addAll(holdCardList);
    }

    /**
     * Performs a bottom value cut of the deck. The bottom card in the deck
     * is checked for its value. Then that many cards are taken from the
     * top of the deck and moved to just above that bottom card.
     */
    protected void bottomValueCut() {
        Card bottomCard = cards.get(cards.size() - 1);
        Integer bottomCardValue = bottomCard.getValue();
        List<Card> holdCardList = new ArrayList<Card>();
        holdCardList.addAll(cards.subList(bottomCardValue, cards.size() - 1));
        holdCardList.addAll(cards.subList(0, bottomCardValue));
        holdCardList.add(bottomCard);
        cards.clear();
        cards.addAll(holdCardList);
    }

    /**
     * Counts the number of cards in the deck
     * @return    Number of cards in the deck
     */
    public Integer countCards() {
        return cards.size();
    }

    /**
     * Sorts the deck by value.
     * @param ascending   True results in the deck being sorted from lowest value to highest.
     *                    False results in the deck being sorted from highest value to lowest.
     */
    public void sort(boolean ascending) {
        createdOrderedDeckOfCards();
        if (!ascending) {
            Collections.reverse(cards);
        }
        originalOrderCards.clear();
        originalOrderCards.addAll(cards);
    }

    /**
     * Shuffles the deck using a Fisher-Yates shuffle
     */
    public void shuffle() {
        Collections.shuffle(cards);
        originalOrderCards.clear();
        originalOrderCards.addAll(cards);
    }

    /**
     * Performs several deck manipulations and then checks the value of the top
     * card of the deck and returns the card that is that many from the top of
     * the deck.
     *
     * The deck manipulations in order are: move joker A down 1, move joker B down 2,
     * triple cut the deck on the two jokers, and bottom value cut the deck.
     *
     * If a joker is selected getNextCard calls itself until a non-joker card is selected.
     *
     * @return The next card to be used in cryptography
     */
    public Card getNextCard() {
        Card jokerA = new Card(Suit.JOKER_A, Rank.JOKER);
        Card jokerB = new Card(Suit.JOKER_B, Rank.JOKER);
        moveCardDown(jokerA);
        moveCardDown(jokerB, 2);
        tripleCut(jokerA, jokerB);
        bottomValueCut();
        Integer topCardValue = cards.get(0).getValue();
        Card card = cards.get(topCardValue);
        if (!card.getRank().equals(Rank.JOKER)) {
            return card;
        } else {
            return getNextCard();
        }

    }

    /**
     * Returns the deck to its original configuration, usually the state
     * after the last time it was shuffled.
     */
    public void restore() {
        cards.clear();
        cards.addAll(originalOrderCards);
    }

    /**
     * Creates a copy of the deck that can be used to decrypt encrypted messages
     * @return  A copy of this deck.
     */
    public Deck copy() {
        return new Deck(originalOrderCards);
    }

    /**
     * Sets the cards array to be an array list of the 54 cards in a standard deck
     * of cards ordered by their value.
     */
    private void createdOrderedDeckOfCards() {
        cards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                if (!(suit.getValue().equals(53) || rank.equals(Rank.JOKER))) {
                    Card card = new Card(suit, rank);
                    cards.add(card);
                }
            }
        }
        cards.add(new Card(Suit.JOKER_A, Rank.JOKER));
        cards.add(new Card(Suit.JOKER_B, Rank.JOKER));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deck deck = (Deck) o;

        if (cards != null ? !cards.equals(deck.cards) : deck.cards != null) return false;
        if (originalOrderCards != null ? !originalOrderCards.equals(deck.originalOrderCards) : deck.originalOrderCards != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = originalOrderCards != null ? originalOrderCards.hashCode() : 0;
        result = 31 * result + (cards != null ? cards.hashCode() : 0);
        return result;
    }
}
