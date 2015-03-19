package org.bitreserve.bitreserve_android_sdk.model.user;

/**
 * Status model.
 */

public class Status {

    private final String email;
    private final String identity;
    private final String overview;
    private final String phone;
    private final String registration;
    private final String review;
    private final String screening;
    private final String volume;

    /**
     * Constructor.
     *
     * @param email The status for the user email.
     * @param identity The status for the user identity.
     * @param overview The status for the user overview.
     * @param phone The status for the user phone.
     * @param registration The status for the user registration.
     * @param review The status for the user review.
     * @param screening The status for the user screening.
     * @param volume The status for the user volume.
     */

    public Status(String email, String identity, String overview, String phone, String registration, String review, String screening, String volume) {
        this.email = email;
        this.identity = identity;
        this.overview = overview;
        this.phone = phone;
        this.registration = registration;
        this.review = review;
        this.screening = screening;
        this.volume = volume;
    }

    /**
     * Gets the user email status.
     *
     * @return the user email status
     */

    public String getEmail() {
        return email;
    }

    /**
     * Gets the user identity status.
     *
     * @return the user identity status
     */

    public String getIdentity() {
        return identity;
    }

    /**
     * Gets the user overview status.
     *
     * @return the user overview status
     */

    public String getOverview() {
        return overview;
    }

    /**
     * Gets the user phone status.
     *
     * @return the user phone status
     */

    public String getPhone() {
        return phone;
    }

    /**
     * Gets the user registration status.
     *
     * @return the user registration status
     */

    public String getRegistration() {
        return registration;
    }

    /**
     * Gets the user review status.
     *
     * @return the user review status
     */

    public String getReview() {
        return review;
    }

    /**
     * Gets the user screening status.
     *
     * @return the user screening status
     */

    public String getScreening() {
        return screening;
    }

    /**
     * Gets the user volume status.
     *
     * @return the user volume status
     */

    public String getVolume() {
        return volume;
    }

}
