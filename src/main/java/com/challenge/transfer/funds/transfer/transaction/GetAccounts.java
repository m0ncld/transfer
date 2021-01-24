package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.account.AccountController;
import com.challenge.transfer.account.AccountDto;
import com.challenge.transfer.account.AccountId;
import com.challenge.transfer.util.Currency;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
@RequestScope
@Order(ProcessorsOrder.GET_ACCOUNTS)
class GetAccounts implements Processor {

    /**
     * Account controller for CRUD operations.
     */
    private final transient AccountController accountController;

    GetAccounts(AccountController accountController) {
        this.accountController = accountController;
    }

    @Override
    public void process(ProcessingContext context) throws ProcessingException {
        AccountId sourceId = context.getRequest().getSource();
        AccountId targetId = context.getRequest().getTarget();
        List<AccountId> ids = Arrays.asList(sourceId, targetId);
        Iterable<AccountDto> accounts = accountController.get(ids);
        Optional<AccountDto> sourceAccount = getAccount(accounts, sourceId);
        Optional<AccountDto> targetAccount = getAccount(accounts, targetId);

        if (!sourceAccount.isPresent() || !targetAccount.isPresent()) {
            String details = String.format("Source account %s, target account %s",
                    sourceAccount.isPresent() ? "exist" : "not exist",
                    targetAccount.isPresent() ? "exist" : "not exist"
            );
            throw ProcessingException.accountNotExist(details);
        }

        context.setSourceAccount(sourceAccount.get());
        context.setTargetAccount(targetAccount.get());
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") // Variables used for filter
    private Optional<AccountDto> getAccount(Iterable<AccountDto> accounts, AccountId id) {
        Currency currency = id.getCurrency();
        int ownerId = id.getOwnerId();
        return StreamSupport
                .stream(accounts.spliterator(), true)
                .filter(account -> ownerId == account.getOwnerId() && currency.equals(account.getCurrency()))
                .findAny();
    }
}
