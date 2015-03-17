package org.bitreserve.bitreserve_android_sdk.model.balance;

/**
 * Currency model.
 */

public class Currency {

    private final String amount;
    private final String balance;
    private final String currency;
    private final String rate;

    /**
     * Constructor.
     *
     * @param amount The amount of the currency.
     * @param balance The balance of the currency.
     * @param currency The currency.
     * @param rate The rate of the currency.
     */

    public Currency(String amount, String balance, String currency, String rate) {
        this.amount = amount;
        this.balance = balance;
        this.currency = currency;
        this.rate = rate;
    }

    /**
     * Gets the amount of the currency.
     *
     * @return the amount of the currency.
     */

    public String getAmount() {
        return amount;
    }

    /**
     * Gets the balance of the currency.
     *
     * @return the balance of the currency.
     */

    public String getBalance() {
        return balance;
    }

    /**
     * Gets the currency.
     *
     * @return the currency.
     */

    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the rate of the currency.
     *
     * @return the rate of the currency.
     */

    public String getRate() {
        return rate;
    }

}
