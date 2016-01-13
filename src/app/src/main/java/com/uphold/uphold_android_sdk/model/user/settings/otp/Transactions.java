package com.uphold.uphold_android_sdk.model.user.settings.otp;

import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.Send;
import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.Transfer;
import com.uphold.uphold_android_sdk.model.user.settings.otp.transactions.Withdraw;

import java.io.Serializable;

/**
 * Transactions otp settings model.
 */

public class Transactions implements Serializable {

    private final Send send;
    private final Transfer transfer;
    private final Withdraw withdraw;

    /**
     * Constructor.
     *
     * @param send The send transactions otp settings.
     * @param transfer The transfer transactions otp settings.
     * @param withdraw The withdraw transactions otp settings.
     */

    public Transactions(Send send, Transfer transfer, Withdraw withdraw) {
        this.send = send;
        this.transfer = transfer;
        this.withdraw = withdraw;
    }

    /**
     * Gets the send transactions otp settings.
     *
     * @return the send transactions otp settings.
     */

    public Send getSend() {
        return send;
    }

    /**
     * Gets the transfer transactions otp settings.
     *
     * @return the transfer transactions otp settings.
     */

    public Transfer getTransfer() {
        return transfer;
    }

    /**
     * Gets the withdraw transactions otp settings.
     *
     * @return the withdraw transactions otp settings.
     */

    public Withdraw getWithdraw() {
        return withdraw;
    }

}
