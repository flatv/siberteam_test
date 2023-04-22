package org.filatov.pokerhandranking.game;

import org.filatov.pokerhandranking.rules.Rank;
import org.filatov.pokerhandranking.rules.Suit;

public record Card(Rank rank, Suit suit) implements Comparable<Card> {
    @Override
    public String toString() {
        return rank + " " + suit;
    }

    @Override
    public int compareTo(Card o) {
        return this.rank.compareTo(o.rank);
    }
}
