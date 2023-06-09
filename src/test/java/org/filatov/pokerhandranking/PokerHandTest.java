package org.filatov.pokerhandranking;

import org.filatov.pokerhandranking.game.PokerHand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandTest {

    @Test
    void sortingTest() {
        List<PokerHand> pokerHands = new ArrayList<>();
        pokerHands.add(new PokerHand("KS 2H 5C JD TD"));
        pokerHands.add(new PokerHand("2C 3C AC 4C 5C"));
        pokerHands.add(new PokerHand("TC 4H 7D KC 2S"));
        pokerHands.add(new PokerHand("2C 3C AC 4C AD"));
        pokerHands.add(new PokerHand("KS KH TC JD TD"));
        pokerHands.add(new PokerHand("KC KH KD 7C 5S"));
        pokerHands.add(new PokerHand("3C 4H 5D 6C 7S"));

        Collections.shuffle(pokerHands);

        pokerHands.sort(Comparator.reverseOrder());

        assertEquals(Collections.max(pokerHands), pokerHands.get(0));
        assertEquals(Collections.min(pokerHands), pokerHands.get(pokerHands.size() - 1));
    }

    @Test
    void test() {
        PokerHand pair = new PokerHand("2C 2D 4H 5D AD");
        PokerHand pair1 = new PokerHand("5C 5D 2H 3D 6D");

        PokerHand samePair = new PokerHand("2C 2D 4H 5D AD");
        PokerHand samePair1 = new PokerHand("2S 2H 5H 7D AS");

        PokerHand set = new PokerHand("KC KD KH AD TD");
        PokerHand set1 = new PokerHand("AC AS AH JD QD");

        assertTrue(pair1.compareTo(pair) > 0);
        assertTrue(samePair.compareTo(samePair1) < 0);
        assertTrue(set.compareTo(set1) < 0);
    }

    @Test
    void assertThrowsTest() {
        assertThrows(IllegalArgumentException.class, () -> new PokerHand("AA BB CC"));
        assertThrows(RuntimeException.class, () -> new PokerHand("KS KS KS KS KS"));
    }

}