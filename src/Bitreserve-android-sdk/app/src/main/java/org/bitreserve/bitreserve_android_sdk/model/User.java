package org.bitreserve.bitreserve_android_sdk.model;

import org.bitreserve.bitreserve_android_sdk.model.user.Settings;
import org.bitreserve.bitreserve_android_sdk.model.user.Status;

/**
 * User contains the information about the current user
 */

public class User {

    private final String country;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String name;
    private final Settings settings;
    private final String state;
    private final Status status;
    private final String username;

    /**
     * Constructor.
     *
     * @param country The user country
     * @param email The user email
     * @param firstName The user first name
     * @param lastName The user last name
     * @param name The user name
     * @param settings The user {@link Settings}
     * @param state The user state
     * @param status The user {@link Status}
     * @param username The user username
     */

    public User(String country, String email, String firstName, String lastName, String name, Settings settings, String state, Status status, String username) {
        this.country = country;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = name;
        this.settings = settings;
        this.state = state;
        this.status = status;
        this.username = username;
    }

    /**
     * Gets the user country.
     *
     * @return the user country
     */

    public String getCountry() {
        return country;
    }

    /**
     * Gets the user email.
     *
     * @return the user email
     */

    public String getEmail() {
        return email;
    }

    /**
     * Gets the user first name.
     *
     * @return the user first name
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the user last name.
     *
     * @return the user last name
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */

    public String getName() {
        return name;
    }

    /**
     * Gets the user settings.
     *
     * @return the user {@link Settings}
     */

    public Settings getSettings() {
        return settings;
    }

    /**
     * Gets the user state.
     *
     * @return the user state
     */

    public String getState() {
        return state;
    }

    /**
     * Gets the user status.
     *
     * @return the user {@link Status}
     */

    public Status getStatus() {
        return status;
    }

    /**
     * Gets the user username.
     *
     * @return the user username
     */

    public String getUsername() {
        return username;
    }
}