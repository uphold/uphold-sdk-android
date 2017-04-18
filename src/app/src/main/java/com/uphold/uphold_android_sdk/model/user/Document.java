package com.uphold.uphold_android_sdk.model.user;

import java.io.Serializable;

/**
 * Document model.
 */

public class Document implements Serializable {

    private final String type;
    private final String value;

    /**
     * Constructor.
     *
     * @param type The document type.
     * @param value The document value.
     */

    public Document(String type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Gets the document type.
     *
     * @return the document type.
     */

    public String getType() {
        return type;
    }

    /**
     * Gets the document value.
     *
     * @return the document value.
     */

    public String getValue() {
        return value;
    }

}
