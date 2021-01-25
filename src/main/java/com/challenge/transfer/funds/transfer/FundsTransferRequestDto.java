package com.challenge.transfer.funds.transfer;

import com.challenge.transfer.account.AccountId;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class FundsTransferRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Funds transfer transaction source target account.
     */
    @NotNull
    private final AccountId source;

    /**
     * Funds transfer transaction request target account.
     */
    @NotNull
    private final AccountId target;

    /**
     * Funds transfer transaction request amount.
     */
    @NotNull
    @DecimalMin("0")
    private final BigDecimal amount;

    /**
     * Construct a funds transfer request dto.
     * @param source Source account ID
     * @param target Target account ID
     * @param amount Funds transfer amount
     */
    FundsTransferRequestDto(AccountId source, AccountId target, BigDecimal amount) {
        this.source = source;
        this.target = target;
        this.amount = amount;
    }

    /**
     * Returns funds transfer transaction source account ID.
     * @return Funds transfer transaction Source account ID
     */
    public AccountId getSource() {
        return source;
    }

    /**
     * Returns funds transfer transaction target account ID.
     * @return Funds transfer transaction target account ID
     */
    public AccountId getTarget() {
        return target;
    }

    /**
     * Returns funds transfer transaction amount.
     * @return Funds transfer transaction amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FundsTransferRequestDto that = (FundsTransferRequestDto) o;
        return Objects.equals(source, that.source)
                && Objects.equals(target, that.target)
                && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, amount);
    }
}
