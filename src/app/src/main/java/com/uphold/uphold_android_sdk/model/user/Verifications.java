package com.uphold.uphold_android_sdk.model.user;

import java.io.Serializable;

/**
 * Verifications model.
 */

public class Verifications implements Serializable {

    private VerificationParameter address;
    private VerificationParameter birthdate;
    private VerificationParameter documents;
    private VerificationParameter email;
    private VerificationParameter identity;
    private VerificationParameter location;
    private VerificationParameter marketing;
    private VerificationParameter phone;
    private VerificationParameter terms;

    /**
     * Constructor.
     *
     * @param address The address verification.
     * @param birthdate The birthdate verification.
     * @param documents The documents verification.
     * @param email The email verification.
     * @param identity The identity verification.
     * @param location The location verification.
     * @param marketing The marketing verification.
     * @param phone The phone verification.
     * @param terms The terms verification.
     */

    public Verifications(VerificationParameter address, VerificationParameter birthdate, VerificationParameter documents, VerificationParameter email, VerificationParameter identity, VerificationParameter location, VerificationParameter marketing, VerificationParameter phone, VerificationParameter terms) {
        this.address = address;
        this.birthdate = birthdate;
        this.documents = documents;
        this.email = email;
        this.identity = identity;
        this.location = location;
        this.marketing = marketing;
        this.phone = phone;
        this.terms = terms;
    }

    /**
     * Gets the address verification.
     *
     * @return the address verification.
     */

    public VerificationParameter getAddress() {
        return address;
    }

    /**
     * Gets the birthdate verification.
     *
     * @return the birthdate verification.
     */

    public VerificationParameter getBirthdate() {
        return birthdate;
    }

    /**
     * Gets the documents verification.
     *
     * @return the documents verification.
     */

    public VerificationParameter getDocuments() {
        return documents;
    }

    /**
     * Gets the email verification.
     *
     * @return the email verification.
     */

    public VerificationParameter getEmail() {
        return email;
    }

    /**
     * Gets the identity verification.
     *
     * @return the identity verification.
     */

    public VerificationParameter getIdentity() {
        return identity;
    }

    /**
     * Gets the location verification.
     *
     * @return the location verification.
     */

    public VerificationParameter getLocation() {
        return location;
    }

    /**
     * Gets the marketing verification.
     *
     * @return the marketing verification.
     */

    public VerificationParameter getMarketing() {
        return marketing;
    }

    /**
     * Gets the phone verification.
     *
     * @return the phone verification.
     */

    public VerificationParameter getPhone() {
        return phone;
    }

    /**
     * Gets the terms verification.
     *
     * @return the terms verification.
     */

    public VerificationParameter getTerms() {
        return terms;
    }

}
