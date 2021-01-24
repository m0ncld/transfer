package com.challenge.transfer.funds.transfer.transaction;

class ProcessingException  extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Unknown exception occurred error code.
     */
    private static final int UNDEFINED_EC = 0;

    /**
     * Either the debit or the credit account does not exist error code.
     */
    private static final int ACCT_NOT_EXIST_EC = 1;

    /**
     * The exchange rate cannot be retrieved error code.
     */
    private static final int RATE_NOT_EXIST_EC = 2;

    /**
     * The balance of the debit account is not sufficient error code.
     */
    private static final int BALANCE_NOT_SUFF_EC = 3;

    /**
     * Exception error code.
     */
    private final int errorCode;

    /**
     * Exception details.
     */
    private final String details;

    ProcessingException(int errorCode, String message, String details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }

    ProcessingException(int errorCode, String message, String details, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.details = details;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }

    static ProcessingException unknown(String details) {
        return new ProcessingException(UNDEFINED_EC, "Unknown exception occurred", details);
    }

    static ProcessingException accountNotExist(String details) {
        return new ProcessingException(ACCT_NOT_EXIST_EC,
                "Either the debit or the credit account does not exist", details);
    }

    static ProcessingException rateNotRetrieved(String details) {
        return new ProcessingException(RATE_NOT_EXIST_EC, "The exchange rate cannot be retrieved", details);
    }

    static ProcessingException balanceNotSufficient(String details) {
        return new ProcessingException(BALANCE_NOT_SUFF_EC,
                "The balance of the debit account is not sufficient", details);
    }

}
