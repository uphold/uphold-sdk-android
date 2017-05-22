package com.uphold.uphold_android_sdk.model.user;

import java.io.Serializable;

/**
 * Phone model.
 */

public class Phone implements Serializable {

    private final String id;
    private final String e164Masked;
    private final Boolean primary;
    private final Boolean verified;

    /**
     * Constructor.
     *
     * @param id The contact id.
     * @param e164Masked The E.164 phone mask.
     * @param primary A boolean indicating if the phone is the primary user phone.
     * @param verified A boolean indicating if the phone is verified.
     */

    public Phone(String id, String e164Masked, Boolean primary, Boolean verified) {
        this.id = id;
        this.e164Masked = e164Masked;
        this.primary = primary;
        this.verified = verified;
    }

    /**
     * Gets the phone id.
     *
     * @return the phone id
     */

    public String getId() {
        return id;
    }

    /**
     * Gets the phone E.164 mask.
     *
     * @return the phone E.164 mask
     */

    public String getE164Masked() {
        return e164Masked;
    }

    /**
     * Gets a boolean indicating if the phone is the primary user phone.
     *
     * @return a boolean indicating if the phone is the primary user phone
     */

    public Boolean getPrimary() {
        return primary;
    }

    /**
     * Gets a boolean indicating if the phone is verified.
     *
     * @return a boolean indicating if the phone is verified
     */

    public Boolean getVerified() {
        return verified;
    }

}
