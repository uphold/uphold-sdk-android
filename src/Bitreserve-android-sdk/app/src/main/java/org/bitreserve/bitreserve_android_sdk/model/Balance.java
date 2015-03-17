package org.bitreserve.bitreserve_android_sdk.model;

import org.bitreserve.bitreserve_android_sdk.model.balance.UserBalance;

/**
 * Balance model.
 */

public class Balance {

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
