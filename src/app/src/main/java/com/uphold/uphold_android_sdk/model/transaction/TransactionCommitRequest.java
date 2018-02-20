package com.uphold.uphold_android_sdk.model.transaction;

import java.io.Serializable;

/**
 * Transaction commit request model.
 */

public class TransactionCommitRequest implements Serializable {

    private final Beneficiary beneficiary;
    private final String message;

    /**
     * Constructor.
     *
     * @param beneficiary The transaction beneficiary.
     */

    public TransactionCommitRequest(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
        this.message = null;
    }

    /**
     * Constructor.
     *
     * @param message The transaction message.
     */

    public TransactionCommitRequest(String message) {
        this.beneficiary = null;
        this.message = message;
    }

    /**
     * Constructor.
     *
     * @param beneficiary The transaction beneficiary.
     * @param message The transaction message.
     */

    public TransactionCommitRequest(Beneficiary beneficiary, String message) {
        this.beneficiary = beneficiary;
        this.message = message;
    }

    /**
     * Gets the transaction beneficiary.
     *
     * @return the transaction beneficiary.
     */

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    /**
     * Gets the transaction request message.
     *
     * @return the transaction request message.
     */

    public String getMessage() {
        return message;
    }

}
