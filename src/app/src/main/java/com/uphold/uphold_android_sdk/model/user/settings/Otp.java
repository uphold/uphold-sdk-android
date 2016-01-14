package com.uphold.uphold_android_sdk.model.user.settings;

import com.uphold.uphold_android_sdk.model.user.settings.otp.Login;
import com.uphold.uphold_android_sdk.model.user.settings.otp.Transactions;

import java.io.Serializable;

/**
 * Otp settings model.
 */

public class Otp implements Serializable {

    private final Login login;
    private final Transactions transactions;

    /**
     * Constructor.
     *
     * @param login The login otp settings.
     * @param transactions The transactions otp settings.
     */

    public Otp(Login login, Transactions transactions) {
        this.login = login;
        this.transactions = transactions;
    }

    /**
     * Gets the user otp login settings.
     *
     * @return the user otp login settings.
     */

    public Login getLogin() {
        return login;
    }

    /**
     * Gets the user otp transactions settings.
     *
     * @return the user otp transactions settings.
     */

    public Transactions getTransactions() {
        return transactions;
    }

}
