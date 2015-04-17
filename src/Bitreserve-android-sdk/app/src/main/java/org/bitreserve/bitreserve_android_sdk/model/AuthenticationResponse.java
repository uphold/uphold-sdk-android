package org.bitreserve.bitreserve_android_sdk.model;

/**
 * Authentication response model.
 */

public class AuthenticationResponse {

    private final String access_token;
    private final String description;
    private final String expires_in;

    /**
     * Constructor.
     *
     * @param access_token The access token from the user.
     * @param description The description from the authentication response.
     * @param expires_in The expiration date from the token.
     */

    public AuthenticationResponse(String access_token, String description, String expires_in) {
        this.access_token = access_token;
        this.description = description;
        this.expires_in = expires_in;
    }

    /**
     * Gets the access token.
     *
     * @return the access token
     */

    public String getAccessToken() {
        return access_token;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */

    public String getDescription() {
        return description;
    }

    /**
     * Gets the expiration date.
     *
     * @return the expiration date
     */

    public String getExpiresIn() {
        return expires_in;
    }

}
