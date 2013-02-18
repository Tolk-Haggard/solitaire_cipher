package com.asolutions.samples.cryptography.ciphers.playingcards;


public enum Suit {
    CLUBS(0),
    DIAMONDS(13),
    HEARTS(26),
    SPADES(39),
    JOKER_A(53),
    JOKER_B(53);

    private Integer value;

    Suit(Integer i) {
        this.value = i;
    }

    public Integer getValue() {
        return value;
    }

}
