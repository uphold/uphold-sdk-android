package com.uphold.uphold_android_sdk.exception;

/**
 * LogicException exception.
 */

public class UpholdSdkNotInitializedException extends UpholdClientException {

    /**
     * Constructor.
     *
     * @param detailsMessage The exception message.
     */

    public UpholdSdkNotInitializedException(String detailsMessage) {
        super(detailsMessage);
    }

}
