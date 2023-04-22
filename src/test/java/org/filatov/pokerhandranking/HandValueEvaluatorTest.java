package org.filatov.pokerhandranking;

import org.filatov.pokerhandranking.game.PokerHand;
import org.filatov.pokerhandranking.rules.HandValue;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.filatov.pokerhandranking.HandValueEvaluator.evaluate;

class HandValueEvaluatorTest {


    @ParameterizedTest
    @ArgumentsSource(PokerHandsArgumentProvider.class)
    void evaluateTest(PokerHand hand) {
        HandValue value = evaluate(hand);

        System.out.println(value);
    }

    static class PokerHandsArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of(new PokerHand("TC 4H 7D KC 2S")),
                    Arguments.of(new PokerHand("2C 3C AC 4C AD")),
                    Arguments.of(new PokerHand("KS KH TC JD TD")),
                    Arguments.of(new PokerHand("KC KH KD 7C 5S")),
                    Arguments.of(new PokerHand("3C 4H 5D 6C 7S")),
                    Arguments.of(new PokerHand("KC QC 9C 8C 2C")),
                    Arguments.of(new PokerHand("KC KH KD 7C 7D")),
                    Arguments.of(new PokerHand("6S 6H 6D 6C KD")),
                    Arguments.of(new PokerHand("2D 3D 4D 5D 6D")),
                    Arguments.of(new PokerHand("AH KH QH JH TH"))
            );
        }
    }

}