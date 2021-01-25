package com.challenge.transfer.funds.transfer;

import java.io.Serializable;
import java.util.Objects;

public class FundsTransferResponseErrorDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Transaction ID.
     */
    private final String transactionId;

    /**
     * Error code.
     */
    private final int errorCode;

    /**
     * Error message.
     */
    private final String message;

    /**
     * Construct funds transfer response error dto.
     * @param transactionId Transaction ID
     * @param errorCode Error code
     * @param message Error message
     */
    public FundsTransferResponseErrorDto(String transactionId, int errorCode, String message) {
        this.transactionId = transactionId;
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * Returns transaction id.
     * @return Transaction id
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Returns error code.
     * @return Error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Returns error message.
     * @return Error message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FundsTransferResponseErrorDto)) {
            return false;
        }
        FundsTransferResponseErrorDto that = (FundsTransferResponseErrorDto) o;
        return errorCode == that.errorCode
                && Objects.equals(transactionId, that.transactionId)
                && Objects.equals(message, that.message)
                && getClass().equals(that.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, errorCode, message);
    }
}
