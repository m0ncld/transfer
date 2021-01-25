package com.challenge.transfer.funds.transfer.transaction;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequestScope
@Order(ProcessorsOrder.CALC_AMT_IN_TARGET_CURRENCY)
class CalcAmtInTargetCurrProcessor implements Processor {

    /**
     * Processor name.
     */
    private static final String NAME = "Calculate Amount In Target Currency";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void process(ProcessingContext context) throws ProcessingException {
        if (!context.getExchangeRate().isPresent()) {
            throw ProcessingException.rateNotRetrieved("Uable to find exchange rate in transaction context");
        }
        BigDecimal sourceAmount = context.getRequest().getAmount();
        int targetMinorUnit = context.getRequest().getTarget().getCurrency().getMinor();
        BigDecimal exchangeRate = context.getExchangeRate().get();
        BigDecimal targetAmount = sourceAmount.multiply(exchangeRate).setScale(targetMinorUnit, RoundingMode.FLOOR);
        context.setAmountInTargetCurrency(targetAmount);
    }

}
