package com.challenge.transfer.account;

import com.challenge.transfer.util.Currency;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class AccountDto implements Serializable {
    private static final long serialVersionUID = 2L;

    /**
     * Owner ID.
     */
    @NotNull
    private final Integer ownerId;

    /**
     * Account currency code.
     */
    @NotNull
    private final Currency currency;

    /**
     * Account balance.
     */
    @NotNull
    private final BigDecimal balance;

    /**
     * Construct the account object.
     * @param ownerId Account owner ID
     * @param currency Account currency
     * @param balance Account balance
     */
    public AccountDto(Integer ownerId, Currency currency, BigDecimal balance) {
        this.ownerId = ownerId;
        this.currency = currency;
        this.balance = balance;
    }

    /**
     * Returns account owner ID.
     * @return Account owner ID
     */
    public Integer getOwnerId() {
        return ownerId;
    }

    /**
     * Returns account currency code.
     * @return Account currency code
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Returns account balance.
     * @return Account balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountDto that = (AccountDto) o;
        return Objects.equals(ownerId, that.ownerId)
                && Objects.equals(currency, that.currency)
                && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, currency, balance);
    }
}
