package com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.withdraw;

import java.io.Serializable;

/**
 * Send otp settings model.
 */

public class Crypto implements Serializable {

    private final Boolean enabled;

    /**
     * Constructor.
     *
     * @param enabled A boolean indicating if the otp is enable for the transactions withdraw process.
     */

    public Crypto(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets a boolean indicating if the otp is enable for transactions withdraw process.
     *
     * @return a boolean indicating if the otp is enable for transactions withdraw process.
     */

    public Boolean getEnabled() {
        return enabled;
    }

}
