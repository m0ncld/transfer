package com.challenge.transfer.exchange.rate;

import com.challenge.transfer.util.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

class ExchangeRate {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRate.class);

    /**
     * Base currency of exchange rate table.
     */
    private final Optional<String> base;

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
        this.base = Optional.ofNullable(externalDto.getBase());
        this.date = Optional.ofNullable(externalDto.getDate());
        this.rates = externalDto.getRates() != null
                ? Collections.unmodifiableMap(externalDto.getRates())
                : Collections.emptyMap();
    }

    Optional<String> getBase() {
        return base;
    }

    Optional<LocalDate> getDate() {
        return date;
    }

    Optional<BigDecimal> getRateFor(Currency currency) {
        return Optional.ofNullable(rates.get(currency));
    }

    Optional<BigDecimal> getRateBetween(Currency currency1, Currency currency2) {
        Optional<BigDecimal> rate1 = getRateFor(currency1);
        Optional<BigDecimal> rate2 = getRateFor(currency2);
        if (!rate1.isPresent() || !rate2.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(rate2.get().divide(rate1.get()));
        } catch (ArithmeticException ex) {
            String msg = String.format(
                    "Error occured during calculating rates between currencies '%s' and '%s'",
                    currency1, currency2);
            LOGGER.warn(msg, ex);
            return Optional.empty();
        }
    }

}
