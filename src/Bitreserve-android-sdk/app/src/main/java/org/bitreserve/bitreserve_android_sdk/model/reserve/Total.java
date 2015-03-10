package org.bitreserve.bitreserve_android_sdk.model.reserve;

/**
 * This class represents the total model.
 */

public class Total {

    private final String commissions;
    private final String transactions;
    private final String assets;
    private final String liabilities;

    /**
     * Constructor.
     *
     * @param commissions The commission from the corresponding holding.
     * @param transactions The transactions from the corresponding holding.
     * @param assets The assets from the corresponding holding.
     * @param liabilities The liabilities from the corresponding holding.
     */

    public Total(String commissions, String transactions, String assets, String liabilities) {
        this.commissions = commissions;
        this.transactions = transactions;
        this.assets = assets;
        this.liabilities = liabilities;
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
     * Gets the transactions from the corresponding holding.
     *
     * @return the transactions from the corresponding holding
     */

    public String getTransactions() {
        return transactions;
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
     * Gets the liabilities from the corresponding holding.
     *
     * @return the liabilities from the corresponding holding
     */

    public String getLiabilities() {
        return liabilities;
    }
}
