package com.challenge.transfer.funds.transfer.transaction;

public class TransactionProcessingException extends ProcessingException {
    private static final long serialVersionUID = 1L;

    /**
     * Transaction ID.
     */
    private final String transactionId;

    /**
     *
     * @param transactionId
     * @param errorCode
     * @param message
     * @param details
     */
    public TransactionProcessingException(String transactionId, int errorCode, String message, String details) {
        super(errorCode, message, details);
        this.transactionId = transactionId;
    }

    /**
     * Construct transaction processing error.
     * @param transactionId Transaction ID
     * @param errorCode Error code
     * @param message Error message
     * @param details Error details
     * @param cause Error cause
     */
    public TransactionProcessingException(String transactionId, int errorCode, String message, String details,
                                          Throwable cause) {
        super(errorCode, message, details, cause);
        this.transactionId = transactionId;
    }

    /**
     * Returns transaction ID.
     * @return Transaction ID
     */
    public String getTransactionId() {
        return transactionId;
    }

    static TransactionProcessingException of(String transactionId, String details, Throwable cause)  {
        return new TransactionProcessingException(transactionId, 0, "Unknown exception occurred", details, cause);
    }

    static TransactionProcessingException of(String transactionId, ProcessingException cause)  {
        return new TransactionProcessingException(
                transactionId, cause.getErrorCode(), cause.getMessage(), cause.getDetails());
    }

}
