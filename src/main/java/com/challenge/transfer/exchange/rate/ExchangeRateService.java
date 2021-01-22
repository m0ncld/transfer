package com.challenge.transfer.exchange.rate;

import com.challenge.transfer.util.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ExchangeRateService {

    /**
     * Refresh rate scheduler initial delay.
     */
    private static final int INITIAL_DELAY = 500;

    /**
     * Class logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateService.class);

    /**
     * URL for external exchange rates api.
     */
    private final transient String exchangeUrl;

    /**
     * Synchronous client to perform HTTP requests.
     */
    private final RestTemplate restTemplate;

    /**
     * Exchange rate model.
     *
     * Contains exchange rate currency map, date and base currency information.
     */
    private transient ExchangeRate exchangeRate;

    @Autowired
    ExchangeRateService(@Value("${exchange-rate.url}") String apiPath) {
        this(apiPath, new ExchangeRate(), new RestTemplate());
    }

    ExchangeRateService(String apiPath, ExchangeRate exchangeRate, RestTemplate restTemplate) {
        this.exchangeUrl = apiPath;
        this.exchangeRate = exchangeRate;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRateString = "${exchange-rate.refresh-rate}", initialDelay = INITIAL_DELAY)
    void refreshRates() {
        LOGGER.trace("Refreshing Exchange rates");
        ResponseEntity<ExchangeRatesExternalDto> response = sendRequest();
        if (!response.getStatusCode().is2xxSuccessful() || !response.hasBody()) {
            exchangeRate = new ExchangeRate();
            LOGGER.warn("Unable to retrieve exchange rates from '" + exchangeUrl + "'");
            return;
        }

        ExchangeRatesExternalDto body = response.getBody();
        if (body.getDate() == null) {
            LOGGER.warn("Unable to get date from retrieved exchange rate");
            return;
        }
        if (!exchangeRate.getDate().isPresent()) {
            LOGGER.debug("Exchange rate not existing. Refreshing exchange rate with new value");
            exchangeRate = new ExchangeRate(body);
            return;
        }
        if (body.getDate().compareTo(exchangeRate.getDate().get()) > 0) {
            LOGGER.debug("Exchange rate refreshed");
            exchangeRate = new ExchangeRate(body);
        }
    }

    private ResponseEntity<ExchangeRatesExternalDto> sendRequest() {
        ResponseEntity<ExchangeRatesExternalDto> response = restTemplate.exchange(
                exchangeUrl,
                HttpMethod.GET,
                null,
                ExchangeRatesExternalDto.class);
        return response;
    }

    /**
     * Exchange rate of given currency.
     *
     * Return exchange rate of given currency or <code>Optional.empty()</code> when there is not exchange rate
     * defined for given currency.
     * @param currency Currency
     * @return Currency exchange rate
     */
    public Optional<BigDecimal> getRateFor(Currency currency) {
        return exchangeRate.getRateFor(currency);
    }

    /**
     * Calculate exchange rate between two currencies.
     *
     * Return exchange rate between two currencies or <code>Optional.empty()</code> when there is not exchange rate
     * defined for given currencies or when exception occurred during rate calculation.
     * @param source Source currency
     * @param target Target currency
     * @return Exchange rate between given currencies
     */
    public Optional<BigDecimal> getRateBetween(Currency source, Currency target) {
        return exchangeRate.getRateBetween(source, target);
    }

    RestTemplate getRestTemplate() {
        return restTemplate;
    }

    ExchangeRate getExchangeRate() {
        return exchangeRate;
    }
}
