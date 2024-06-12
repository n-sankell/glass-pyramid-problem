package org.example.glasspyramid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GlassPyramidTest {

    private GlassPyramid pyramid;

    @BeforeEach
    void setUp() {
        pyramid = new GlassPyramid(6);
    }

    @ParameterizedTest
    @MethodSource("rowGlassExpectedNumbers")
    void calculateTest(int row, int glass, double expected) {
        double result = pyramid.calculateFillTimeForGlass(row, glass);
        assertEquals(expected, result, 0.0001);
    }

    private static Stream<Arguments> rowGlassExpectedNumbers() {
        return Stream.of(
            Arguments.of(1, 1, 10.0009),
            Arguments.of(2, 1, 30.001),
            Arguments.of(5, 2, 136.667)
        );
    }

}