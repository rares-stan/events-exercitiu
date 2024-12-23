package com.example.demo.event;

import java.util.Objects;

public record HourlyEvents(Integer hour, Double count) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HourlyEvents that = (HourlyEvents) o;
        return Objects.equals(hour, that.hour) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, count);
    }
}
