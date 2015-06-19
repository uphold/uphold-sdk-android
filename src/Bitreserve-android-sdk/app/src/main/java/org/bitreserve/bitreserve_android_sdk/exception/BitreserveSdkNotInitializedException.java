package org.bitreserve.bitreserve_android_sdk.exception;

/**
 * LogicException exception.
 */

public class BitreserveSdkNotInitializedException extends BitreserveClientException {

    /**
     * Constructor.
     *
     * @param detailsMessage The exception message.
     */

    public BitreserveSdkNotInitializedException(String detailsMessage) {
        super(detailsMessage);
    }

}
