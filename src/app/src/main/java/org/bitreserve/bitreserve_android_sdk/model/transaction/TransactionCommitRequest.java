package org.bitreserve.bitreserve_android_sdk.model.transaction;

import java.io.Serializable;

/**
 * Transaction commit request model.
 */

public class TransactionCommitRequest implements Serializable {

    String message;

    /**
     * Constructor.
     *
     * @param message The transanction message.
     */

    public TransactionCommitRequest(String message) {
        this.message = message;
    }

    /**
     * Gets the transaction request message.
     *
     * @return the transaction request message
     */

    public String getMessage() {
        return message;
    }

}
