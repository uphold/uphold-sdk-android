package com.uphold.uphold_android_sdk.model.user.settings.otp.transactions;

import java.io.Serializable;

/**
 * Transfer otp settings model.
 */

public class Transfer implements Serializable {

    private final Boolean enabled;

    /**
     * Constructor.
     *
     * @param enabled A boolean indicating if the otp is enable for the transactions transfer process.
     */

    public Transfer(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets a boolean indicating if the otp is enable for transactions transfer process.
     *
     * @return a boolean indicating if the otp is enable for transactions transfer process.
     */

    public Boolean getEnabled() {
        return enabled;
    }

}
