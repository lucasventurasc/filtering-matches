package com.sparknetworks.loveos.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RangeTest {

    @Test
    void should_return_false_when_value_passed_is_not_within_range_of_two_set_numbers() {
        Range<Integer> range = Range.of(1, 4);

        assertFalse(range.isWithinRange(0));
        assertFalse(range.isWithinRange(5));
    }

    @Test
    void should_return_true_when_value_passed_is_within_range_of_two_set_numbers() {
        Range<Integer> range = Range.of(1, 4);

        assertTrue(range.isWithinRange(1));
        assertTrue(range.isWithinRange(2));
        assertTrue(range.isWithinRange(3));
        assertTrue(range.isWithinRange(4));
    }

}