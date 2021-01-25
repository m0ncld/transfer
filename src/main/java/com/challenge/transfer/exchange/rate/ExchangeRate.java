package com.challenge.transfer.exchange.rate;

import com.challenge.transfer.util.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class ExchangeRate {

    /**
     * Exchange rate calculation scale.
     */
    private static final int RATE_SCALE = 8;

    /**
     * Class logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRate.class);

    /**
     * Base currency of exchange rate table.
     */
    private final Optional<Currency> base;

    /**
     * Date of currency exchange rate table.
     */
    private final Optional<LocalDate> date;

    /**
     * Exchange rate map with currency and corresponding rate.
     */
    private final transient Map<Currency, BigDecimal> rates;

    ExchangeRate() {
        this(null);
    }

    ExchangeRate(ExchangeRatesExternalDto externalDto) {
        if (externalDto == null) {
            this.base = Optional.empty();
            this.date = Optional.empty();
            this.rates = Collections.emptyMap();
            return;
        }
        this.base = prepareBaseCurrency(externalDto.getBase());
        this.date = Optional.ofNullable(externalDto.getDate());
        this.rates = prepareRates(externalDto.getRates(), base);
    }

    private Map<Currency, BigDecimal> prepareRates(Map<Currency, BigDecimal> extTates, Optional<Currency> extBase) {
        if (extTates == null) {
            return Collections.emptyMap();
        }
        Map<Currency, BigDecimal> preparedRates = new HashMap<>(extTates);
        extBase.ifPresent(currency -> preparedRates.put(currency, BigDecimal.ONE));
        return Collections.unmodifiableMap(preparedRates);
    }

    private static Optional<Currency> prepareBaseCurrency(String baseCurrency) {
        if (baseCurrency == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(Currency.valueOf(baseCurrency));
        } catch (IllegalArgumentException ex) {
            LOGGER.warn("Unable to get currency in dictionary for base currency " + baseCurrency);
        }
        return Optional.empty();
    }

    Optional<Currency> getBase() {
        return base;
    }

    Optional<LocalDate> getDate() {
        return date;
    }

    Optional<BigDecimal> getRateFor(Currency currency) {
        return Optional.ofNullable(rates.get(currency));
    }

    int getSize() {
        return rates.size();
    }

    Optional<BigDecimal> getRateBetween(Currency currency1, Currency currency2) {
        Optional<BigDecimal> rate1 = getRateFor(currency1);
        Optional<BigDecimal> rate2 = getRateFor(currency2);
        if (!rate1.isPresent() || !rate2.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(rate2.get().divide(rate1.get(), RATE_SCALE, RoundingMode.FLOOR));
        } catch (ArithmeticException ex) {
            String msg = String.format(
                    "Error occured during calculating rates between currencies '%s' and '%s'",
                    currency1, currency2);
            LOGGER.warn(msg, ex);
            return Optional.empty();
        }
    }

}
