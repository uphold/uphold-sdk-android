package com.uphold.uphold_android_sdk.model.card;

import java.io.Serializable;

/**
 * Card request model.
 */

public class CardRequest implements Serializable {

    private final String label;
    private final String currency;
    private final Settings settings;

    /**
     * Constructor.
     *
     * @param label The label of the card.
     * @param currency The currency of the card.
     */

    public CardRequest(String label, String currency) {
        this.label = label;
        this.currency = currency;
        this.settings = null;
    }

    /**
     * Constructor.
     *
     * @param label The label of the card.
     * @param currency The currency of the card.
     * @param settings The settings of the card.
     */

    public CardRequest(String label, String currency, Settings settings) {
        this.label = label;
        this.currency = currency;
        this.settings = settings;
    }

    /**
     * Gets the label of the card.
     *
     * @return the label of the card
     */

    public String getLabel() {
        return label;
    }

    /**
     * Gets the currency of the card.
     *
     * @return the currency of the card
     */

    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the settings of the card.
     *
     * @return the settings of the card
     */

    public Settings getSettings() {
        return settings;
    }

}
