package com.uphold.uphold_android_sdk.model.transaction;

import java.io.Serializable;

/**
 * Beneficiary address model.
 */

public class BeneficiaryAddress implements Serializable {

    private final String city;
    private final String country;
    private final String line1;
    private final String line2;
    private final String state;
    private final String zipCode;

    /**
     * Constructor.
     *
     * @param city The city.
     * @param country The country.
     * @param line1 The line 1.
     * @param line2 The line 2.
     * @param state The state.
     * @param zipCode The zip code.
     */

    public BeneficiaryAddress(String city, String country, String line1, String line2, String state, String zipCode) {
        this.city = city;
        this.country = country;
        this.line1 = line1;
        this.line2 = line2;
        this.state = state;
        this.zipCode = zipCode;
    }

    /**
     * Gets the city.
     *
     * @return the city.
     */

    public String getCity() {
        return city;
    }

    /**
     * Gets the country.
     *
     * @return the country.
     */

    public String getCountry() {
        return country;
    }

    /**
     * Gets the line 1.
     *
     * @return the line 1.
     */

    public String getLine1() {
        return line1;
    }

    /**
     * Gets the line 2.
     *
     * @return the line 2.
     */

    public String getLine2() {
        return line2;
    }

    /**
     * Gets the state.
     *
     * @return the state.
     */

    public String getState() {
        return state;
    }

    /**
     * Gets the zip code.
     *
     * @return the zip code.
     */

    public String getZipCode() {
        return zipCode;
    }

}
