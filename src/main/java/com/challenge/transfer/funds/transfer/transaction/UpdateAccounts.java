package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.account.AccountController;
import com.challenge.transfer.account.AccountDto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
@RequestScope
@Order(ProcessorsOrder.UPDATE_ACCOUNTS)
class UpdateAccounts implements Processor {

    /**
     * Account controller for updating account data.
     */
    private final transient AccountController controller;

    UpdateAccounts(AccountController controller) {
        this.controller = controller;
    }

    @Override
    public void process(ProcessingContext context) throws ProcessingException {
        if (!context.getSourceAccount().isPresent()) {
            throw ProcessingException.accountNotExist("Unable to find source account in context");
        }
        if (!context.getTargetAccount().isPresent()) {
            throw ProcessingException.accountNotExist("Unable to find target account in context");
        }
        if (!context.getAmountInTargetCurrency().isPresent()) {
            throw ProcessingException.unknown("Unable to find amount in target currency in context");
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
