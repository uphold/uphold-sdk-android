package com.uphold.uphold_android_sdk.model.transaction;

import java.io.Serializable;

/**
 * Merchant model.
 */

public class Merchant implements Serializable {

    private final String city;
    private final String country;
    private final String name;
    private final String state;
    private final String zipCode;

    /**
     * Constructor.
     *
     * @param city The city of the merchant.
     * @param country The country of the merchant.
     * @param name The name of the merchant.
     * @param state The state of the merchant.
     * @param zipCode The zip code of the merchant.
     */

    public Merchant(String city, String country, String name, String state, String zipCode) {
        this.city = city;
        this.country = country;
        this.name = name;
        this.state = state;
        this.zipCode = zipCode;
    }

    /**
     * Gets the city of the merchant.
     *
     * @return the city of the merchant.
     */

    public String getCity() {
        return city;
    }

    /**
     * Gets the country of the merchant.
     *
     * @return the country of the merchant.
     */

    public String getCountry() {
        return country;
    }

    /**
     * Gets the name of the merchant.
     *
     * @return the name of the merchant.
     */

    public String getName() {
        return name;
    }

    /**
     * Gets the state of the merchant.
     *
     * @return the state of the merchant.
     */

    public String getState() {
        return state;
    }

    /**
     * Gets the zip code of the merchant.
     *
     * @return the zip code of the merchant.
     */

    public String getZipCode() {
        return zipCode;
    }

}
