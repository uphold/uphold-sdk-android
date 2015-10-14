package com.uphold.uphold_android_sdk.exception;

import retrofit.RetrofitError;

/**
 * NotFoundException exception.
 */

public class NotFoundException extends UpholdClientException {

    /**
     * Constructor.
     *
     * @param detailMessage The exception message.
     * @param error The {@link RetrofitError}.
     * @param httpCode The http error code.
     */

    public NotFoundException(String detailMessage, RetrofitError error, Integer httpCode) {
        super(detailMessage, error, httpCode);
    }

}
