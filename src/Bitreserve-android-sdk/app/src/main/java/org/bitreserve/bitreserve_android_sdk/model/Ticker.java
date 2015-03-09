package org.bitreserve.bitreserve_android_sdk.model;

/**
 * This class represents the ticker model.
 */

public class Ticker {

    private final String ask;
    private final String bid;
    private final String currency;
    private final String pair;

    /**
     * Constructor.
     *
     * @param ask The ticker ask.
     * @param bid The ticker bid.
     * @param currency The ticker currency.
     * @param pair The ticker pair.
     */

    public Ticker(String ask, String bid, String currency, String pair) {
        this.ask = ask;
        this.bid = bid;
        this.currency = currency;
        this.pair = pair;
    }

    /**
     * Gets the ticker ask.
     *
     * @return the ticker ask
     */

    public String getAsk() {
        return ask;
    }

    /**
     * Gets the ticker bid.
     *
     * @return the ticker bid
     */

    public String getBid() {
        return bid;
    }

    /**
     * Gets the ticker currency.
     *
     * @return the ticker currency
     */

    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the ticker pair.
     *
     * @return the ticker pair
     */

    public String getPair() {
        return pair;
    }
}
