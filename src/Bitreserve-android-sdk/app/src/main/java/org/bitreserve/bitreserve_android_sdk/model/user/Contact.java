package org.bitreserve.bitreserve_android_sdk.model.user;

import java.util.List;

/**
 * Contact represents a user contact information.
 */

public class Contact {

    private final String id;
    private final List<String> addresses;
    private final String company;
    private final List<String> emails;
    private final String firstName;
    private final String lastName;
    private final String name;

    /**
     * Constructor.
     *
     * @param id The contact id.
     * @param addresses The list of addresses.
     * @param company The company name.
     * @param emails The list of contact emails.
     * @param firstName The contact first name.
     * @param lastName The contact last name.
     * @param name The contact name.
     */

    public Contact(String id, List<String> addresses, String company, List<String> emails, String firstName, String lastName, String name) {
        this.id = id;
        this.addresses = addresses;
        this.company = company;
        this.emails = emails;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = name;
    }

    /**
     * Gets the contact id.
     *
     * @return the contact id
     */

    public String getId() {
        return id;
    }

    /**
     * Gets the list of addresses for the contact.
     *
     * @return a list of addresses
     */

    public List<String> getAddresses() {
        return addresses;
    }

    /**
     * Gets the contact company.
     *
     * @return the contact company
     */

    public String getCompany() {
        return company;
    }

    /**
     * Gets the contact list of emails.
     *
     * @return a list of emails associated to the contact
     */

    public List<String> getEmails() {
        return emails;
    }

    /**
     * Gets the contact first name.
     *
     * @return the contact first name
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the contact last name.
     *
     * @return the contact last name
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the contact name.
     *
     * @return the contact name
     */

    public String getName() {
        return name;
    }
}
