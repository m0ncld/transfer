package com.challenge.transfer.account.jpa;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class AccountEntityPK implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Owner ID.
     */
    private Integer ownerId;

    /**
     * Account currency code.
     */
    private String currency;

    AccountEntityPK() {
    }

    AccountEntityPK(Integer ownerId, String currency) {
        this.ownerId = ownerId;
        this.currency = currency;
    }

    Integer getOwnerId() {
        return ownerId;
    }

    void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    String getCurrency() {
        return currency;
    }

    void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountEntityPK that = (AccountEntityPK) o;
        return Objects.equals(ownerId, that.ownerId)
                && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, currency);
    }
}
