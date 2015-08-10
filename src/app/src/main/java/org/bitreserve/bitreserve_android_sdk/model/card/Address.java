package org.bitreserve.bitreserve_android_sdk.model.card;

import java.io.Serializable;

/**
 * Address model.
 */

public class Address implements Serializable {

    private final String id;
    private final String network;

    /**
     * Constructor.
     *
     * @param id The id of the address.
     * @param network The network of the address.
     */

    public Address(String id, String network) {
        this.id = id;
        this.network = network;
    }

    /**
     * Gets the id of the address.
     *
     * @return the address id
     */

    public String getId() {
        return id;
    }

    /**
     * Gets the network of the address.
     *
     * @return the network of the address
     */

    public String getNetwork() {
        return network;
    }

}
