package com.uphold.uphold_android_sdk.model.transaction;

import java.io.Serializable;

/**
 * Node model.
 */

public class Node implements Serializable {

    private final String brand;
    private final String id;
    private final String type;

    /**
     * Constructor.
     *
     * @param brand The brand of the node.
     * @param id The id of the node.
     * @param type The type of the node.
     */

    public Node(String brand, String id, String type) {
        this.brand = brand;
        this.id = id;
        this.type = type;
    }

    /**
     * Gets the brand of the node.
     *
     * @return the brand of the node.
     */

    public String getBrand() {
        return brand;
    }

    /**
     * Gets the id of the node.
     *
     * @return the id of the node.
     */

    public String getId() {
        return id;
    }

    /**
     * Gets the type of the node.
     *
     * @return the type of the node.
     */

    public String getType() {
        return type;
    }

}
