package org.bitreserve.bitreserve_android_sdk.model;

/**
 * Authentication request model.
 */

public class AuthenticationRequest {

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
