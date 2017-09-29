package com.uphold.uphold_android_sdk.model.user;

import java.io.Serializable;

/**
 * VerificationParameter model.
 */

public class VerificationParameter implements Serializable {

    private String reason;
    private String status;

    /**
     * Constructor.
     *
     * @param reason The verification reason.
     * @param status The verification status.
     */

    public VerificationParameter(String reason, String status) {
        this.reason = reason;
        this.status = status;
    }

    /**
     * Gets the verification reason.
     *
     * @return the verification reason.
     */

    public String getReason() {
        return reason;
    }

    /**
     * Gets the verification status.
     *
     * @return the verification status.
     */

    public String getStatus() {
        return status;
    }

}
