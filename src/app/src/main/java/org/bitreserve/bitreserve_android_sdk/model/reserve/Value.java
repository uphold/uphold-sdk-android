package org.bitreserve.bitreserve_android_sdk.model.reserve;

import java.io.Serializable;

/**
 * Value model.
 */

public class Value implements Serializable {

    private final String assets;
    private final String currency;
    private final String liabilities;
    private final String rate;

    /**
     * Constructor.
     *
     * @param assets The quantity of assets held for the corresponding holding, but converted to a different currency.
     * @param currency The currency we are computing the current holding in.
     * @param liabilities The quantity of liabilities for the corresponding holding, but converted to a different currency.
     * @param rate The rate we used when computing the holding to the corresponding currency.
     */

    public Value(String assets, String currency, String liabilities, String rate) {
        this.assets = assets;
        this.currency = currency;
        this.liabilities = liabilities;
        this.rate = rate;
    }


    /**
     * Gets the quantity of assets held for the corresponding holding.
     *
     * @return the quantity of assets held for the corresponding holding
     */

    public String getAssets() {
        return assets;
    }

    /**
     * Gets the currency we are computing the current holding in.
     *
     * @return the currency we are computing the current holding in
     */

    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the quantity of liabilities for the corresponding holding.
     *
     * @return the quantity of liabilities for the corresponding holding
     */

    public String getLiabilities() {
        return liabilities;
    }

    /**
     * Gets the rate we used when computing the holding to the corresponding currency.
     *
     * @return the rate we used when computing the holding to the corresponding currency
     */

    public String getRate() {
        return rate;
    }

}
