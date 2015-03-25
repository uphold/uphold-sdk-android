package org.bitreserve.bitreserve_android_sdk.model;

import retrofit.client.Response;

/**
 * Response model.
 */

public class ResponseModel {

    private final Response response;

    /**
     * Constructor.
     *
     * @param response The {@link retrofit.client.Response} of the request.
     */

    public ResponseModel(Response response) {
        this.response = response;
    }

    /**
     * Gets the response.
     *
     * @return the {@link retrofit.client.Response} of the request.
     */

    public Response getResponse() {
        return response;
    }

}
