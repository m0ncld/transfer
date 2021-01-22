package com.challenge.transfer.account;

import com.challenge.transfer.util.Currency;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class AccountId implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Owner ID.
     */
    private final int ownerId;

    /**
     * Account currency.
     */
    private final Currency currency;

    /**
     * Construct the account ID.
     * @param ownerId Owner ID
     * @param currency Account currency
     */
    public AccountId(int ownerId, @NotNull Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        this.ownerId = ownerId;
        this.currency = currency;
    }

    /**
     * Returns owner ID.
     * @return Owner ID
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Returns account currency.
     * @return Account currency
     */
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountId accountId = (AccountId) o;
        return ownerId == accountId.ownerId
                && currency == accountId.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, currency);
    }
}
