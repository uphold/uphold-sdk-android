package com.uphold.uphold_android_sdk.model.card;

import java.io.Serializable;

/**
 * Card address request model.
 */

public class AddressRequest implements Serializable {

    private final String network;

    /**
     * Constructor.
     *
     * @param network The network for the address to be created.
     */

    public AddressRequest(String network) {
        this.network = network;
    }

    /**
     * Gets the network.
     *
     * @return The network for the address to be created.
     */

    public String getNetwork() {
        return network;
    }

}
