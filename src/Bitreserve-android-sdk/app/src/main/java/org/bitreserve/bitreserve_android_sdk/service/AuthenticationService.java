package org.bitreserve.bitreserve_android_sdk.service;

import org.bitreserve.bitreserve_android_sdk.model.AuthenticationRequest;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Has the authentication webservice endpoints.
 */

public interface AuthenticationService {

    /**
     * Performs a request to get a user access token.
     *
     * @param otp The OTP code sent to the mobile device.
     * @param authorization The authorization
     * @param request The {@link AuthenticationResponse} information.
     * @param callback A callback to receive the request information.
     */

    @POST("/v0/me/tokens")
    void authenticateUser(@Header("X-Bitreserve-OTP") String otp, @Header("Authorization") String authorization, @Body AuthenticationRequest request, Callback<AuthenticationResponse> callback);

}
