package com.challenge.transfer.funds.transfer.transaction;

import com.challenge.transfer.account.AccountDto;
import com.challenge.transfer.funds.transfer.FundsTransferRequestDto;

import java.math.BigDecimal;
import java.util.Optional;

class ProcessingContext {

    /**
     * Transaction ID.
     */
    private final String transactionID;

    /**
     * Funds transfer transaction request.
     */
    private final FundsTransferRequestDto request;

    /**
     * Exchange rate between source and target currencies.
     */
    private BigDecimal exchangeRate;

    /**
     * Amount in target currency.
     */
    private BigDecimal amountInTargetCurrency;

    /**
     * Source account.
     */
    private AccountDto sourceAccount;

    /**
     * Target account.
     */
    private AccountDto targetAccount;

    ProcessingContext(String transactionID, FundsTransferRequestDto request) {
        this.transactionID = transactionID;
        this.request = request;
    }

    String getTransactionID() {
        return transactionID;
    }

    FundsTransferRequestDto getRequest() {
        return request;
    }

    public Optional<BigDecimal> getExchangeRate() {
        return Optional.ofNullable(exchangeRate);
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Optional<BigDecimal> getAmountInTargetCurrency() {
        return Optional.ofNullable(amountInTargetCurrency);
    }

    public void setAmountInTargetCurrency(BigDecimal amountInTargetCurrency) {
        this.amountInTargetCurrency = amountInTargetCurrency;
    }

    public Optional<AccountDto> getSourceAccount() {
        return Optional.ofNullable(sourceAccount);
    }

    public void setSourceAccount(AccountDto sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Optional<AccountDto> getTargetAccount() {
        return Optional.ofNullable(targetAccount);
    }

    public void setTargetAccount(AccountDto targetAccount) {
        this.targetAccount = targetAccount;
    }
}
