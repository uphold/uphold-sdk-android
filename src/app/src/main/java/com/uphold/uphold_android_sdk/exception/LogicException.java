package com.uphold.uphold_android_sdk.exception;

/**
 * LogicException exception.
 */

public class LogicException extends UpholdClientException {

    /**
     * Constructor.
     *
     * @param detailsMessage The exception message.
     */

    public LogicException(String detailsMessage) {
        super(detailsMessage);
    }

}
