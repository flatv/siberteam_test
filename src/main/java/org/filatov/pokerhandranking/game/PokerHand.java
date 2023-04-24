package org.filatov.pokerhandranking.game;

import org.filatov.pokerhandranking.util.HandValueEvaluator;
import org.filatov.pokerhandranking.rules.HandValue;
import org.filatov.pokerhandranking.rules.Rank;
import org.filatov.pokerhandranking.rules.Suit;

import java.util.Arrays;
import java.util.Comparator;

public class PokerHand implements Comparable<PokerHand> {

    /**
     * Руку храним в массиве, чтобы использовать {@link Arrays#compare(Comparable[], Comparable[])}
     * для лексикографического сравнения,
     * так как поля {@link Card} это {@link Enum} по сути словарь, поэтому сравнение пройдет как нужно
     */
    private final Card[] hand;

    private final HandValue category;

    public PokerHand(String hand) {
        this.hand = createCardHandFromString(hand);

        this.category = HandValueEvaluator.evaluate(this);
    }

    /**
     * Сравниваем сначала по комбинации, потом по старшинству карт
     * (если будет две руки с парой то сильнее будет комбинация у
     * которой пятая карта старше, если я правильно понял правила)
     */
    @Override
    public int compareTo(PokerHand pokerHand) {
        return Comparator
                .comparing(PokerHand::getCategory)
                .thenComparing(PokerHand::getHand, Arrays::compare)
                .compare(this, pokerHand);
    }

    /**
     * С объектами удобнее работать чем со строкой, потому что придётся часто сравнивать 
     * карты по масти и по номиналу.
     * <p>
     * @param hand входящая строка содержащая пять карт
     * @return массив объектов {@link Card} который в удобном виде представляет руку
     * @throws RuntimeException если карт будет не 5
     * @throws IllegalArgumentException если будут переданы одинаковые карты или вообще не карты
     * @see <a href="https://refactoring.guru/ru/replace-type-code-with-class">Замена кодирования типа классом</a>
     */
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
