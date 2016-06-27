package com.uphold.uphold_android_sdk.model.card;

import java.io.Serializable;

/**
 * The card address model.
 */

public class Address implements Serializable {

    private final String id;
    private final String network;

    /**
     * Constructor.
     *
     * @param id The address.
     * @param network The network.
     */

    public Address(String id, String network) {
        this.id = id;
        this.network = network;
    }

    /**
     * Gets the address.
     *
     * @return the address.
     */

    public String getId() {
        return id;
    }

    /**
     * Gets the network.
     *
     * @return the network.
     */

    public String getNetwork() {
        return network;
    }

}
