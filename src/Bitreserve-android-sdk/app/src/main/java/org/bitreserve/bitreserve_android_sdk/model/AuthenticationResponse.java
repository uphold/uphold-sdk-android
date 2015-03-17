package org.bitreserve.bitreserve_android_sdk.model;

/**
 * Authentication response model.
 */

public class AuthenticationResponse {

    private final String access_token;
    private final String description;
    private final String expires;

    /**
     * Constructor.
     *
     * @param access_token The access token from the user.
     * @param description The description from the authentication response.
     * @param expires The expiration date from the token.
     */

    public AuthenticationResponse(String access_token, String description, String expires) {
        this.access_token = access_token;
        this.description = description;
        this.expires = expires;
    }

    /**
     * Gets the access token.
     *
     * @return the access token
     */

    public String getAccess_token() {
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

    public String getExpires() {
        return expires;
    }

}
