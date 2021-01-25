package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.account.AccountDto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Order(ProcessorsOrder.CHECK_ACCOUNT_FUNDS)
class CheckAccountFundsProcessor implements Processor {

    /**
     * Processor name.
     */
    private static final String NAME = "Check Account Funds";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void process(ProcessingContext context) throws ProcessingException {
        if (!context.getSourceAccount().isPresent()) {
            throw ProcessingException
                    .accountNotExist("Unable to find source account in transaction context");
        }
        AccountDto sourceAccount = context.getSourceAccount().get();
        if (context.getRequest().getAmount().compareTo(sourceAccount.getBalance()) > 0) {
            throw ProcessingException
                    .balanceNotSufficient("Not sufficient balance on source account");
        }
    }
}
