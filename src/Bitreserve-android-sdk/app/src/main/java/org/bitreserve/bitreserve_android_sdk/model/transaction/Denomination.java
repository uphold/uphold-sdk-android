package org.bitreserve.bitreserve_android_sdk.model.transaction;

/**
 * Denomination model.
 */

public class Denomination {

    private final String amount;
    private final String currency;
    private final String pair;
    private final String rate;

    /**
     * Constructor.
     *
     * @param amount The amount of the transaction.
     * @param currency The currency of the transaction.
     * @param pair The pair of the transaction.
     * @param rate The rate of the transaction.
     */

    public Denomination(String amount, String currency, String pair, String rate) {
        this.amount = amount;
        this.currency = currency;
        this.pair = pair;
        this.rate = rate;
    }

    /**
     * Gets the amount of the transaction.
     *
     * @return the amount of the transaction
     */

    public String getAmount() {
        return amount;
    }

    /**
     * Gets the currency of the transaction.
     *
     * @return the currency of the transaction
     */

    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the pair of the transaction.
     *
     * @return the pair of the transaction
     */

    public String getPair() {
        return pair;
    }

    /**
     * Gets the rate of the transaction.
     *
     * @return the rate of the transaction
     */

    public String getRate() {
        return rate;
    }

}
