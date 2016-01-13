package com.uphold.uphold_android_sdk.model.user.settings.otp;

import java.io.Serializable;

/**
 * Login otp settings model.
 */

public class Login implements Serializable {

    private final Boolean enabled;

    /**
     * Constructor.
     *
     * @param enabled A boolean indicating if the otp is enable for the login process.
     */

    public Login(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets a boolean indicating if the otp is enable for the login process.
     *
     * @return a boolean indicating if the otp is enable for the login process.
     */

    public Boolean getEnabled() {
        return enabled;
    }

}
