package com.uphold.uphold_android_sdk.model;

import java.io.Serializable;

/**
 * Authentication request model.
 */

public class AuthenticationRequest implements Serializable {

    private final String description;

    /**
     * Constructor.
     *
     * @param description The description for the authentication request.
     */

    public AuthenticationRequest(String description) {
        this.description = description;
    }

    /**
     * Gets the description for the authentication request.
     *
     * @return the description
     */

    public String getDescription() {
        return description;
    }

}
