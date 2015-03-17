package org.bitreserve.bitreserve_android_sdk.model;

/**
 * Base model.
 */

public class BaseModel {

    private Token token;

    /**
     * Constructor.
     */

    public BaseModel() {
    }

    /**
     * Constructor.
     *
     * @param token The token.
     */

    public BaseModel(Token token) {
        this.token = token;
    }

    /**
     * Gets the token.
     *
     * @return the token.
     */

    public Token getToken() {
        return token;
    }

    /**
     * Sets the token.
     *
     * @param token the {@link Token}
     */

    public void setToken(Token token) {
        this.token = token;
    }

}
