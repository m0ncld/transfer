package com.challenge.transfer.exchange.rate;

import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRateTest {

    private ExchangeRate instance;
    private ExchangeRatesExternalDto source;

    @BeforeEach
    void setUp() {
        instance = null;
        source = new ExchangeRatesExternalDto();
        source.setDate(LocalDate.of(2021, 1, 22));
        source.setBase(Currency.EUR.getCode());
        source.setRates(new HashMap<>(3));
        source.getRates().put(Currency.EUR, BigDecimal.ONE);
        source.getRates().put(Currency.USD, new BigDecimal("0.25"));
        source.getRates().put(Currency.PLN, new BigDecimal(5));
    }

    @Test
    void constructDefault() {
        // Invoke test method
        instance = new ExchangeRate();

        // Assert result
        assertNotNull(instance.getBase());
        assertNotNull(instance.getDate());
        assertFalse(instance.getBase().isPresent());
        assertFalse(instance.getDate().isPresent());
    }

    @Test
    void constructFromNull() {
        // Invoke test method
        instance = new ExchangeRate(null);

        // Assert result
        assertNotNull(instance.getBase());
        assertNotNull(instance.getDate());
        assertFalse(instance.getBase().isPresent());
        assertFalse(instance.getDate().isPresent());
    }

    @Test
    void constructFromEmpty() {
        // Invoke test method
        instance = new ExchangeRate(new ExchangeRatesExternalDto());

        // Assert result
        assertNotNull(instance.getBase());
        assertNotNull(instance.getDate());
        assertFalse(instance.getBase().isPresent());
        assertFalse(instance.getDate().isPresent());
    }

    @Test
    void getRateForExisting() {
        instance = new ExchangeRate(source);
        Optional<BigDecimal> result = instance.getRateFor(Currency.USD);
        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("0.25").compareTo(result.get()), 0);
    }

    @Test
    void getRateForNotExisting() {
        instance = new ExchangeRate(source);
        Optional<BigDecimal> result = instance.getRateFor(Currency.AED);
        assertFalse(result.isPresent());
    }

    @Test
    void getRateBetweenNotExistingBoth() {
        instance = new ExchangeRate(source);
        Optional<BigDecimal> result = instance.getRateBetween(Currency.AED, Currency.AFN);
        assertFalse(result.isPresent());
    }

    @Test
    void getRateBetweenNotExistingFirst() {
        instance = new ExchangeRate(source);
        Optional<BigDecimal> result = instance.getRateBetween(Currency.EUR, Currency.AFN);
        assertFalse(result.isPresent());
    }

    @Test
    void getRateBetweenNotExistingSecond() {
        instance = new ExchangeRate(source);
        Optional<BigDecimal> result = instance.getRateBetween(Currency.AED, Currency.EUR);
        assertFalse(result.isPresent());
    }

    @Test
    void getRateBetweenTheSame() {
        instance = new ExchangeRate(source);
        Optional<BigDecimal> result = instance.getRateBetween(Currency.PLN, Currency.PLN);
        assertTrue(result.isPresent());
        assertEquals(BigDecimal.ONE.compareTo(result.get()), 0);
    }

    @Test
    void getRateBetweenDifferent() {
        instance = new ExchangeRate(source);
        Optional<BigDecimal> result = instance.getRateBetween(Currency.USD, Currency.PLN);
        assertTrue(result.isPresent());
        assertEquals(new BigDecimal(20).compareTo(result.get()), 0);
    }

    @Test
    void getRateBetweenRate0() {
        source.getRates().put(Currency.AED, BigDecimal.ZERO);
        instance = new ExchangeRate(source);
        Optional<BigDecimal> result = instance.getRateBetween(Currency.AED, Currency.PLN);
        assertFalse(result.isPresent());
    }
}
