package org.filatov.pokerhandranking.game;

import org.filatov.pokerhandranking.HandValueEvaluator;
import org.filatov.pokerhandranking.rules.HandValue;
import org.filatov.pokerhandranking.rules.Rank;
import org.filatov.pokerhandranking.rules.Suit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PokerHand implements Comparable<PokerHand>{

    private final List<Card> hand;

    private final HandValue category;

    public PokerHand(String hand)  {
        this.hand = Arrays
                .stream(hand.split(" "))
                .map(card -> {
                    String getRank = card.substring(0, 1);
                    String getSuit = card.substring(1);

                    Rank rank = Arrays
                            .stream(Rank.values())
                            .filter(value -> value.getShortName().equals(getRank))
                            .findAny()
                            .orElseThrow(IllegalArgumentException::new);

                    Suit suit = Arrays
                            .stream(Suit.values())
                            .filter(value -> value.getShortName().equals(getSuit))
                            .findAny()
                            .orElseThrow(IllegalArgumentException::new);;

                        return new Card(rank, suit);

                }).collect(Collectors.toList());

        this.hand.sort(Card::compareTo);

        this.category = HandValueEvaluator.evaluate(this);
    }



    public List<Card> getHand() {
        return hand;
    }

    public HandValue getCategory() {
        return category;
    }

    @Override
    public int compareTo(PokerHand o) {
        return 0;
    }
}
