package com.uphold.uphold_android_sdk.model.user.settings.otp.transactions;

import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.withdraw.Crypto;

import java.io.Serializable;

/**
 * Withdraw otp settings model.
 */

public class Withdraw implements Serializable {

    private final Crypto crypto;

    /**
     * Constructor.
     *
     * @param crypto The transactions withdraw crypto otp settings.
     */

    public Withdraw(Crypto crypto) {
        this.crypto = crypto;
    }

    /**
     * Gets transactions withdraw crypto otp settings.
     *
     * @return the transactions withdraw crypto otp settings.
     */

    public Crypto getCrypto() {
        return crypto;
    }

}
