package org.filatov.pokerhandranking.game;

import org.filatov.pokerhandranking.util.HandValueEvaluator;
import org.filatov.pokerhandranking.rules.HandValue;
import org.filatov.pokerhandranking.rules.Rank;
import org.filatov.pokerhandranking.rules.Suit;

import java.util.Arrays;
import java.util.Comparator;

public class PokerHand implements Comparable<PokerHand> {

    private final Card[] hand;

    private final HandValue category;

    public PokerHand(String hand) {
        this.hand = createCardHandFromString(hand);

        this.category = HandValueEvaluator.evaluate(this);
    }

    @Override
    public int compareTo(PokerHand pokerHand) {
        return Comparator
                .comparing(PokerHand::getCategory)
                .thenComparing(PokerHand::getHand, Arrays::compare)
                .compare(this, pokerHand);
    }

    private static Card[] createCardHandFromString(String hand) {
        Card[] arr = Arrays
                .stream(hand.split(" "))
                .map(PokerHand::createCard)
                .distinct()
                .sorted(Card::compareTo)
                .toArray(Card[]::new);

        if (arr.length != 5) {
            throw new RuntimeException();
        }
        return arr;
    }

    private static Card createCard(String card) {
        String getRank = card.substring(0, 1);
        String getSuit = card.substring(1);

        Rank rank = getRank(getRank);

        Suit suit = getSuit(getSuit);

        return new Card(rank, suit);
    }

    private static Suit getSuit(String getSuit) {
        return Arrays
                .stream(Suit.values())
                .filter(value -> value.getShortName().equals(getSuit))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static Rank getRank(String getRank) {
        return Arrays
                .stream(Rank.values())
                .filter(value -> value.getShortName().equals(getRank))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Card[] getHand() {
        return hand;
    }

    public HandValue getCategory() {
        return category;
    }


    @Override
    public String toString() {
        return "hand=" + Arrays.toString(hand) + ", category=" + category;
    }
}
