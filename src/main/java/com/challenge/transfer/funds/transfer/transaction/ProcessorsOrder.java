package com.challenge.transfer.funds.transfer.transaction;

class ProcessorsOrder {
    private ProcessorsOrder() { }

    /**
     * Order for exchange rate processor.
     */
    static final int EXCHANGE_RATE = 0;

    /**
     * Order for calculation amount in target currency processor.
     */
    static final int CALC_AMT_IN_TARGET_CURRENCY = 1;

    /**
     * Order for get accounts processor.
     */
    static final int GET_ACCOUNTS = 2;

    /**
     * Order for check account funds processor.
     */
    static final int CHECK_ACCOUNT_FUNDS = 3;

    /**
     * Order for update accounts processor.
     */
    static final int UPDATE_ACCOUNTS = 4;
}
