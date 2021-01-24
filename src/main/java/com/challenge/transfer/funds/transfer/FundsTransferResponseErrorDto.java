package com.challenge.transfer.funds.transfer;

import java.io.Serializable;

class FundsTransferResponseErrorDto implements Serializable {
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
    FundsTransferResponseErrorDto(String transactionId, int errorCode, String message) {
        this.transactionId = transactionId;
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * Returns transaction id.
     * @return Transaction id
     */
    String getTransactionId() {
        return transactionId;
    }

    /**
     * Returns error code.
     * @return Error code
     */
    int getErrorCode() {
        return errorCode;
    }

    /**
     * Returns error message.
     * @return Error message
     */
    String getMessage() {
        return message;
    }
}
