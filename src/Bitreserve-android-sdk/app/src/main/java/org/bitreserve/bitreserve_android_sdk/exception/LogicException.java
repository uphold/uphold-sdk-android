package org.bitreserve.bitreserve_android_sdk.exception;

/**
 * LogicException exception.
 */

public class LogicException extends BitreserveClientException {

    /**
     * Constructor.
     *
     * @param detailsMessage The exception message.
     */

    public LogicException(String detailsMessage) {
        super(detailsMessage);
    }

}
