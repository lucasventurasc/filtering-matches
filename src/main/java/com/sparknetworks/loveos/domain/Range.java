package com.sparknetworks.loveos.domain;

public class Range<T extends Comparable<T>> {

    private T from;
    private T to;

    private Range() {}

    public static <T extends Comparable<T>> Range<T> of(T from, T to) {
        Range<T> range = new Range<>();
        range.from = from;
        range.to = to;
        return range;
    }

    public boolean isWithinRange(T value) {
        return value.compareTo(from) >= 0 && value.compareTo(to) <= 0;
    }
}
