package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.account.AccountController;
import com.challenge.transfer.account.AccountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
@RequestScope
@Order(ProcessorsOrder.UPDATE_ACCOUNTS)
class UpdateAccountsProcessor implements Processor {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateAccountsProcessor.class);

    /**
     * Processor name.
     */
    private static final String NAME = "Update Accounts";

    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Account controller for updating account data.
     */
    private final transient AccountController controller;

    UpdateAccountsProcessor(AccountController controller) {
        this.controller = controller;
    }

    @Override
    public void process(ProcessingContext context) throws ProcessingException {
        if (context.getRequest().getSource().equals(context.getRequest().getTarget())) {
            LOGGER.debug("Source and target account are the same. No need to update");
            return;
        }
        if (!context.getSourceAccount().isPresent()) {
            throw ProcessingException.accountNotExist("Unable to find source account in context");
        }
        if (!context.getTargetAccount().isPresent()) {
            throw ProcessingException.accountNotExist("Unable to find target account in context");
        }
        if (!context.getAmountInTargetCurrency().isPresent()) {
            throw ProcessingException.rateNotRetrieved("Unable to find amount in target currency in context");
        }
        AccountDto sourceAccount = context.getSourceAccount().get();
        AccountDto targetAccount = context.getTargetAccount().get();

        BigDecimal sourceAmount = context.getRequest().getAmount().negate();
        BigDecimal targetAmount = context.getAmountInTargetCurrency().get();

        AccountDto preparedSourceAccount = prepareAccount(sourceAccount, sourceAmount);
        AccountDto preparedTargetAccount = prepareAccount(targetAccount, targetAmount);
        controller.edit(Arrays.asList(preparedSourceAccount, preparedTargetAccount));

    }

    private AccountDto prepareAccount(AccountDto account, BigDecimal amount) {
        BigDecimal updatedBalance = account.getBalance().add(amount);
        return new AccountDto(
                account.getOwnerId(),
                account.getCurrency(),
                updatedBalance
        );
    }
}
