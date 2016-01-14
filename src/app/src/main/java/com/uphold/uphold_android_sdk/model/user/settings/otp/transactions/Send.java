package com.uphold.uphold_android_sdk.model.user.settings.otp.transactions;

import java.io.Serializable;

/**
 * Send otp settings model.
 */

public class Send implements Serializable {

    private final Boolean enabled;

    /**
     * Constructor.
     *
     * @param enabled A boolean indicating if the otp is enable for the transactions send process.
     */

    public Send(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets a boolean indicating if the otp is enable for transactions send process.
     *
     * @return a boolean indicating if the otp is enable for transactions send process.
     */

    public Boolean getEnabled() {
        return enabled;
    }

}
