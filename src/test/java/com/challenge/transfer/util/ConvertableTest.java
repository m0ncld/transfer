package com.challenge.transfer.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ConvertableTest {

    @Test
    void defaultConversionFrom() {
        ConvertableTestClass defaultImplementation = new ConvertableTestClass();
        assertThrows(UnsupportedOperationException.class, () -> defaultImplementation.convertFrom(1L));
    }

    @Test
    void defaultConversionTo() {
        ConvertableTestClass defaultImplementation = new ConvertableTestClass();
        assertThrows(UnsupportedOperationException.class, () -> defaultImplementation.convertTo(1));
    }

    class ConvertableTestClass implements Convertable<Long, Integer> { }
}