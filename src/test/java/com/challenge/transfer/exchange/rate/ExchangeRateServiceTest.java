package com.challenge.transfer.exchange.rate;

import com.challenge.transfer.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExchangeRateServiceTest {

    private static final String EXCHANGE_RATE_URL = "exchange.rate.path";
    private ExchangeRateService instance;

    private ExchangeRate exchangeRate;

    @Mock
    private ResponseEntity<ExchangeRatesExternalDto> responseEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.exchange(EXCHANGE_RATE_URL,
                HttpMethod.GET,
                null,
                ExchangeRatesExternalDto.class)).thenReturn(responseEntity);

        exchangeRate = spy(new ExchangeRate());
        instance = new ExchangeRateService(EXCHANGE_RATE_URL, exchangeRate, restTemplate);
    }

    @Test
    public void getRateForOnEmptyExchangeRate() {
        // Invoke test method
        Optional<BigDecimal> result = instance.getRateFor(Currency.EUR);

        // Assert result
        assertFalse(result.isPresent());
    }

    @Test
    public void getRateForOnCurrencyExisting() {
        // Prepare test data
        when(exchangeRate.getRateFor(Currency.EUR)).thenReturn(Optional.of(BigDecimal.TEN));

        // Invoke test method
        Optional<BigDecimal> result = instance.getRateFor(Currency.EUR);

        // Assert result
        assertTrue(result.isPresent());
        assertEquals(BigDecimal.TEN, result.get());
    }

    @Test
    public void getRateBetweenOnEmptyExchangeRate() {
        // Prepare test data
        when(exchangeRate.getRateBetween(Currency.EUR, Currency.USD)).thenReturn(Optional.of(BigDecimal.TEN));

        // Invoke test method
        Optional<BigDecimal> result = instance.getRateBetween(Currency.EUR, Currency.USD);

        // Assert result
        assertTrue(result.isPresent());
        assertEquals(BigDecimal.TEN, result.get());
    }

    @Test
    public void refreshRatesNotSuccessRest() {
        // Setup REST response
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);

        // Setup initial rate
        ExchangeRate initialExchangeRate = instance.getExchangeRate();

        // Execute refresh
        instance.refreshRates();

        // Assert result
        assertNotSame(initialExchangeRate, instance.getExchangeRate());
        assertFalse(instance.getExchangeRate().getDate().isPresent());
    }

    @Test
    public void refreshRatesSuccessRestNoObject() {
        // Setup REST response
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(responseEntity.getBody()).thenReturn(null);

        // Setup initial rate
        ExchangeRate initialExchangeRate = instance.getExchangeRate();

        // Execute refresh
        instance.refreshRates();

        // Assert result
        assertNotSame(initialExchangeRate, instance.getExchangeRate());
        assertFalse(instance.getExchangeRate().getDate().isPresent());
    }

    @Test
    public void refreshRatesSuccessRestNoDate() {
        // Setup REST response
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        ExchangeRatesExternalDto restRate = mock(ExchangeRatesExternalDto.class);
        when(responseEntity.hasBody()).thenReturn(true);
        when(responseEntity.getBody()).thenReturn(restRate);

        // Setup initial rate
        ExchangeRate initialExchangeRate = instance.getExchangeRate();

        // Execute refresh
        instance.refreshRates();

        // Assert result
        assertSame(initialExchangeRate, instance.getExchangeRate());
    }

    @Test
    public void refreshRatesSuccessCurrentNoDate() {
        // Setup REST response
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        ExchangeRatesExternalDto restRate = mock(ExchangeRatesExternalDto.class);
        when(restRate.getDate()).thenReturn(LocalDate.of(2021, 1, 22));
        when(responseEntity.hasBody()).thenReturn(true);
        when(responseEntity.getBody()).thenReturn(restRate);

        // Setup initial rate
        ExchangeRate initialExchangeRate = instance.getExchangeRate();

        // Execute refresh
        instance.refreshRates();

        // Assert result
        assertNotSame(initialExchangeRate, instance.getExchangeRate());
        assertTrue(instance.getExchangeRate().getDate().isPresent());
        assertEquals(LocalDate.of(2021, 1, 22), instance.getExchangeRate().getDate().get());
    }

    @Test
    public void refreshRatesSuccessCurrentNewer() {
        // Setup REST response
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        ExchangeRatesExternalDto restRate = mock(ExchangeRatesExternalDto.class);
        when(restRate.getDate()).thenReturn(LocalDate.of(2021, 1, 22));
        when(responseEntity.hasBody()).thenReturn(true);
        when(responseEntity.getBody()).thenReturn(restRate);

        // Setup initial rate
        ExchangeRate initialExchangeRate = instance.getExchangeRate();
        when(initialExchangeRate.getDate()).thenReturn(Optional.of(LocalDate.of(2021, 1, 23)));

        // Execute refresh
        instance.refreshRates();

        // Assert result
        assertSame(initialExchangeRate, instance.getExchangeRate());
    }

    @Test
    public void refreshRatesSuccessCurrentSame() {
        // Setup REST response
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        ExchangeRatesExternalDto restRate = mock(ExchangeRatesExternalDto.class);
        when(restRate.getDate()).thenReturn(LocalDate.of(2021, 1, 22));
        when(responseEntity.hasBody()).thenReturn(true);
        when(responseEntity.getBody()).thenReturn(restRate);

        // Setup initial rate
        ExchangeRate initialExchangeRate = instance.getExchangeRate();
        when(initialExchangeRate.getDate()).thenReturn(Optional.of(LocalDate.of(2021, 1, 22)));

        // Execute refresh
        instance.refreshRates();

        // Assert result
        assertSame(initialExchangeRate, instance.getExchangeRate());
    }

    @Test
    public void refreshRatesSuccessCurrentOlder() {
        // Setup REST response
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        ExchangeRatesExternalDto restRate = mock(ExchangeRatesExternalDto.class);
        when(restRate.getDate()).thenReturn(LocalDate.of(2021, 1, 22));
        when(responseEntity.hasBody()).thenReturn(true);
        when(responseEntity.getBody()).thenReturn(restRate);

        // Setup initial rate
        ExchangeRate initialExchangeRate = instance.getExchangeRate();
        when(initialExchangeRate.getDate()).thenReturn(Optional.of(LocalDate.of(2021, 1, 21)));

        // Execute refresh
        instance.refreshRates();

        // Assert result
        assertNotSame(initialExchangeRate, instance.getExchangeRate());
        assertTrue(instance.getExchangeRate().getDate().isPresent());
        assertEquals(LocalDate.of(2021, 1, 22), instance.getExchangeRate().getDate().get());
    }
}