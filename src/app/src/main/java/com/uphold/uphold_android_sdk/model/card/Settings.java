package com.uphold.uphold_android_sdk.model.card;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Card settings model.
 */

public class Settings implements Serializable {

    @SerializedName("protected")
    private final Boolean isProtected;
    private final Integer position;
    private final Boolean starred;

    /**
     * Constructor.
     *
     * @param position The position of the card.
     * @param starred A boolean indicating if the card is starred.
     */

    public Settings(Integer position, Boolean starred) {
        this.isProtected = false;
        this.position = position;
        this.starred = starred;
    }

    /**
     * Gets the position of the card.
     *
     * @return the position of the card
     */

    public Integer getPosition() {
        return position;
    }

    /**
     * Gets a boolean indicating if the card is protected.
     *
     * @return a boolean indicating if the card is protected
     */

    public Boolean getProtected() {
        return isProtected;
    }

    /**
     * Gets a boolean indicating if the card is starred.
     *
     * @return a boolean indicating if the card is starred
     */

    public Boolean getStarred() {
        return starred;
    }

}
