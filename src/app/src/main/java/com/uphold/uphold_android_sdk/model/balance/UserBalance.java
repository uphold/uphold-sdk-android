package com.uphold.uphold_android_sdk.model.balance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Balance model
 */

public class UserBalance implements Serializable {

    private final HashMap<String, Currency> currencies;
    private final String total;

    /**
     * Constructor.
     *
     * @param currencies The list of the currencies.
     * @param total The total of the balance.
     */

    public UserBalance(HashMap<String, Currency> currencies, String total) {
        this.currencies = currencies;
        this.total = total;
    }

    /**
     * Gets the list of the currencies for the balance.
     *
     * @return the {@link List<Currency>} for the balance of the user.
     */

    public HashMap<String, Currency> getCurrencies() {
        return currencies;
    }

    /**
     * Gets the total of the balance.
     *
     * @return the total of the balance.
     */

    public String getTotal() {
        return total;
    }

}
