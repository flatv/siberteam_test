package org.filatov.pokerhandranking.util;

import org.filatov.pokerhandranking.game.Card;
import org.filatov.pokerhandranking.game.PokerHand;
import org.filatov.pokerhandranking.rules.HandValue;
import org.filatov.pokerhandranking.rules.Rank;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class HandValueEvaluator {

    public static HandValue evaluate(PokerHand hand) {
        long count = getCount(hand);

        /*
        Оставляем только карты с уникальной рубашкой, таким образом
        если останется одна карта то у нас флеш
         */
        long flush = getFlush(hand);

        Integer[] cardsCount = getCardsCount(hand);

        Rank[] ranks = getRanks(hand);

        /*
        Также оставляем карты с уникальным номиналом
        если осталось 4 карты у нас пара
         */
        if (count == 4) {
            return HandValue.PAIR;
        }
        if (count == 3) {
            return cardsCount[0] == 3 ? HandValue.THREE_OF_A_KIND : HandValue.TWO_PAIRS;
        }
        if (count == 2) {
            return cardsCount[0] == 3 ? HandValue.FULL_HOUSE : HandValue.FOUR_OF_A_KIND;
        }
        /*
        Проверяем что карты идут по порядку разностью позиций в enum,
        если разница равна 4, то у нас стрит.
        Также сравниваем старшую карту с тузом, что бы избежать того,
        что флеш рояль считается за стрит флеш
         */
        if (isStraight(ranks)) {
            return flush == 1 ? HandValue.STRAIGHT_FLUSH : HandValue.STRAIGHT;
        }
        if (ranks[0].equals(Rank.ACE) && ranks[1].equals(Rank.KING) && flush == 1) {
            return HandValue.ROYAL_FLUSH;
        }

        return flush == 1 ? HandValue.FLUSH : HandValue.HIGH_CARD;
    }

    private static boolean isStraight(Rank[] ranks) {
        return ranks[0].ordinal() - ranks[4].ordinal() == 4 && ranks[0] != Rank.ACE;
    }

    private static Rank[] getRanks(PokerHand hand) {
        return Arrays.stream(hand.getHand())
                .map(Card::rank)
                .sorted(Comparator.reverseOrder())
                .toArray(Rank[]::new);
    }

    private static Integer[] getCardsCount(PokerHand hand) {
        return Arrays.stream(hand.getHand())
                .map(Card::rank)
                .collect(Collectors.toMap(
                        card -> card,
                        c -> 1,
                        Integer::sum
                ))
                .values()
                .stream()
                .sorted(Comparator.reverseOrder())
                .toArray(Integer[]::new);
    }

    private static long getFlush(PokerHand hand) {
        return Arrays.stream(hand.getHand())
                .map(Card::suit)
                .distinct()
                .count();
    }

    private static long getCount(PokerHand hand) {
        return Arrays.stream(hand.getHand())
                .map(Card::rank)
                .distinct()
                .count();
    }

}
