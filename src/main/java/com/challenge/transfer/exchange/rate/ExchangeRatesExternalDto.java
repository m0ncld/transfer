package com.challenge.transfer.exchange.rate;

import com.challenge.transfer.util.Currency;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

class ExchangeRatesExternalDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Base currency of exchange rate table.
     */
    private String base;

    /**
     * Date of currency exchange rate table.
     */
    private LocalDate date;

    /**
     * Exchange rate map with currency and corresponding rate.
     */
    private Map<Currency, BigDecimal> rates;

    public void setBase(String base) {
        this.base = base;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRates(Map<Currency, BigDecimal> rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Currency, BigDecimal> getRates() {
        return rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExchangeRatesExternalDto that = (ExchangeRatesExternalDto) o;
        return Objects.equals(base, that.base)
                && Objects.equals(date, that.date)
                && Objects.equals(rates, that.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, date, rates);
    }
}
