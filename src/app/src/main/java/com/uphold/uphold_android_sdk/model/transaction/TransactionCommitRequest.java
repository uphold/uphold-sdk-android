package com.uphold.uphold_android_sdk.model.transaction;

import java.io.Serializable;

/**
 * Transaction commit request model.
 */

public class TransactionCommitRequest implements Serializable {

    String message;
    String securityCode;

    /**
     * Constructor.
     *
     * @param message The transaction message.
     */

    public TransactionCommitRequest(String message) {
        this.message = message;
    }

    /**
     * Constructor.
     *
     * @param message The transaction message.
     * @param securityCode The transaction security code.
     */

    public TransactionCommitRequest(String message, String securityCode) {
        this.message = message;
        this.securityCode = securityCode;
    }

    /**
     * Gets the transaction request message.
     *
     * @return the transaction request message
     */

    public String getMessage() {
        return message;
    }

    /**
     * Gets the transaction security code.
     *
     * @return the transaction security code
     */

    public String getSecurityCode() {
        return securityCode;
    }

}
