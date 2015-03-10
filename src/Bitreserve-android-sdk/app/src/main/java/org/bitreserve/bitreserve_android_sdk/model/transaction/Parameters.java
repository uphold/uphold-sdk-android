package org.bitreserve.bitreserve_android_sdk.model.transaction;

/**
 * This class represents the parameters model.
 */

public class Parameters {

    private final String currency;
    private final String margin;
    private final String pair;
    private final String progress;
    private final String rate;
    private final Integer ttl;
    private final String txid;
    private final String type;

    /**
     * Constructor.
     *
     * @param currency The transaction currency.
     * @param margin The transaction margin.
     * @param pair The transaction pair.
     * @param progress The transaction progress.
     * @param rate The transaction rate.
     * @param ttl The transaction ttl.
     * @param txid The transaction txid.
     * @param type The transaction type.
     */

    public Parameters(String currency, String margin, String pair, String progress, String rate, Integer ttl, String txid, String type) {
        this.currency = currency;
        this.margin = margin;
        this.pair = pair;
        this.progress = progress;
        this.rate = rate;
        this.ttl = ttl;
        this.txid = txid;
        this.type = type;
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
     * Gets the margin of the transaction.
     *
     * @return the margin of the transaction
     */

    public String getMargin() {
        return margin;
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
     * Gets the progress of the transaction.
     *
     * @return the progress of the transaction
     */

    public String getProgress() {
        return progress;
    }

    /**
     * Gets the rate of the transaction.
     *
     * @return the rate of the transaction
     */

    public String getRate() {
        return rate;
    }

    /**
     * Gets the ttl of the transaction.
     *
     * @return the ttl of the transaction
     */

    public Integer getTtl() {
        return ttl;
    }

    /**
     * Gets the txid of the transaction.
     *
     * @return the txid of the transaction
     */

    public String getTxid() {
        return txid;
    }

    /**
     * Gets the type of the transaction.
     *
     * @return the type of the transaction
     */

    public String getType() {
        return type;
    }
}
