package com.uphold.uphold_android_sdk.model;

import java.io.Serializable;

/**
 * Authentication response model.
 */

public class AuthenticationResponse implements Serializable {

    private final String access_token;
    private final Integer expires_in;
    private final String scope;
    private final String token_type;

    /**
     * Constructor.
     *
     * @param access_token The access token from the user.
     * @param token_type The description from the authentication response.
     * @param expires_in The expiration date from the token.
     */

    public AuthenticationResponse(String access_token, Integer expires_in, String scope, String token_type) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.scope = scope;
        this.token_type = token_type;
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
     * Gets the expiration.
     *
     * @return the expiration
     */

    public Integer getExpiresIn() {
        return expires_in;
    }

    /**
     * Gets the scope.
     *
     * @return the scope
     */

    public String getScope() {
        return scope;
    }

    /**
     * Gets the token type.
     *
     * @return the token type
     */

    public String getTokenType() {
        return token_type;
    }

}
