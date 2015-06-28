package org.bitreserve.bitreserve_android_sdk.model.reserve;

import java.io.Serializable;

/**
 * Total model.
 */

public class Total implements Serializable {

    private final String assets;
    private final String commissions;
    private final String liabilities;
    private final String transactions;

    /**
     * Constructor.
     *
     * @param assets The assets from the corresponding holding.
     * @param commissions The commission from the corresponding holding.
     * @param liabilities The liabilities from the corresponding holding.
     * @param transactions The transactions from the corresponding holding.
     */

    public Total(String assets, String commissions, String liabilities, String transactions) {
        this.assets = assets;
        this.commissions = commissions;
        this.liabilities = liabilities;
        this.transactions = transactions;
    }

    /**
     * Gets the assets from the corresponding holding.
     *
     * @return the assets from the corresponding holding
     */

    public String getAssets() {
        return assets;
    }

    /**
     * Gets the commissions from the corresponding holding.
     *
     * @return the commissions from the corresponding holding
     */

    public String getCommissions() {
        return commissions;
    }

    /**
     * Gets the liabilities from the corresponding holding.
     *
     * @return the liabilities from the corresponding holding
     */

    public String getLiabilities() {
        return liabilities;
    }

    /**
     * Gets the transactions from the corresponding holding.
     *
     * @return the transactions from the corresponding holding
     */

    public String getTransactions() {
        return transactions;
    }

}
