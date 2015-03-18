package org.bitreserve.bitreserve_android_sdk.exception;

import retrofit.RetrofitError;

/**
 * BadRequestException exception.
 */

public class BadRequestException extends BitreserveClientException {

    /**
     * Constructor.
     *
     * @param detailMessage The exception message.
     * @param error The {@link RetrofitError}.
     * @param httpCode The http error code.
     */

    public BadRequestException(String detailMessage, RetrofitError error, Integer httpCode) {
        super(detailMessage, error, httpCode);
    }

}
