package com.uphold.uphold_android_sdk.model;

import com.uphold.uphold_android_sdk.model.balance.UserBalance;

import java.io.Serializable;

/**
 * Balance model.
 */

public class Balance implements Serializable {

    private final UserBalance balances;

    /**
     * Constructor.
     *
     * @param balances The user balance.
     */

    public Balance(UserBalance balances) {
        this.balances = balances;
    }

    /**
     * Gets the user balance.
     *
     * @return the user {@link Balance}
     */

    public UserBalance getBalances() {
        return balances;
    }

}
