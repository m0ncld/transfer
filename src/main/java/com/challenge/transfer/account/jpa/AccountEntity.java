package com.challenge.transfer.account.jpa;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ACCOUNT")
class AccountEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Owner ID.
     */
    @EmbeddedId
    private AccountEntityPK id;

    /**
     * Account balance.
     */
    @Column(name = "BALANCE", nullable = false)
    private Integer balance;

    public AccountEntityPK getId() {
        return id;
    }

    public void setId(AccountEntityPK id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountEntity that = (AccountEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
