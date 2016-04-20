package com.uphold.uphold_android_sdk.model;

import java.io.Serializable;

/**
 * Account model.
 */

public class Account implements Serializable {

    private final String currency;
    private final String id;
    private final String label;
    private final String status;
    private final String type;

    /**
     * Constructor.
     *
     * @param currency The currency.
     * @param id The id.
     * @param label The label.
     * @param status The status.
     * @param type The type.
     */

    public Account(String currency, String id, String label, String status, String type) {
        this.currency = currency;
        this.id = id;
        this.label = label;
        this.status = status;
        this.type = type;
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
     * Gets the id.
     *
     * @return the id.
     */

    public String getId() {
        return id;
    }

    /**
     * Gets the label.
     *
     * @return the label.
     */

    public String getLabel() {
        return label;
    }

    /**
     * Gets the status.
     *
     * @return the status.
     */

    public String getStatus() {
        return status;
    }

    /**
     * Gets the type.
     *
     * @return the type.
     */

    public String getType() {
        return type;
    }

}
