package com.uphold.uphold_android_sdk.model;

import java.io.Serializable;

/**
 * Rate model.
 */

public class Rate implements Serializable {

    private final String ask;
    private final String bid;
    private final String currency;
    private final String pair;

    /**
     * Constructor.
     *
     * @param ask The rate ask.
     * @param bid The rate bid.
     * @param currency The rate currency.
     * @param pair The rate pair.
     */

    public Rate(String ask, String bid, String currency, String pair) {
        this.ask = ask;
        this.bid = bid;
        this.currency = currency;
        this.pair = pair;
    }

    /**
     * Gets the rate ask.
     *
     * @return the rate ask
     */

    public String getAsk() {
        return ask;
    }

    /**
     * Gets the rate bid.
     *
     * @return the rate bid
     */

    public String getBid() {
        return bid;
    }

    /**
     * Gets the rate currency.
     *
     * @return the rate currency
     */

    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the rate pair.
     *
     * @return the rate pair
     */

    public String getPair() {
        return pair;
    }

}
