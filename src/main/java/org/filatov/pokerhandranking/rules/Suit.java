package org.filatov.pokerhandranking.rules;

public enum Suit {
    SPADES("S"), //пики
    HEARTS("H"), //черви
    DIAMONDS("D"), //бубны
    CLUBS("C"); //трефы

    private final String shortName;

    Suit(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

}
