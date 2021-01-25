package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.exchange.rate.ExchangeRateService;
import com.challenge.transfer.util.Currency;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequestScope
@Order(ProcessorsOrder.EXCHANGE_RATE)
class ExchangeRateProcessor implements Processor {

    /**
     * Processor name.
     */
    private static final String NAME = "Exchange Rate Processor";

    /**
     * Exchange rate service.
     */
    private final transient ExchangeRateService service;

    ExchangeRateProcessor(ExchangeRateService service) {
        this.service = service;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void process(ProcessingContext context) throws ProcessingException {
        Currency sourceCurrency = context.getRequest().getSource().getCurrency();
        Currency targetCurrency = context.getRequest().getTarget().getCurrency();

        Optional<BigDecimal> exchangeRate = service.getRateBetween(sourceCurrency, targetCurrency);
        if (!exchangeRate.isPresent()) {
            throw ProcessingException
                    .rateNotRetrieved("Exchange rate cannot be retrieved");
        }
        context.setExchangeRate(exchangeRate.get());
    }
}
