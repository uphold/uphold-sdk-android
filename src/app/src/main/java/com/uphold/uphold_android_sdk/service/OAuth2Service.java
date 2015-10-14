package com.uphold.uphold_android_sdk.service;

import com.uphold.uphold_android_sdk.model.AuthenticationResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * OAuth2 Service.
 */

public interface OAuth2Service {

    /**
     * Performs a request to get a user access token.
     *
     * @param authorization The authorization.
     * @param code The code from the authorize.
     * @param grantType The grant type.
     * @param callback A callback to receive the request information.
     */

    @FormUrlEncoded
    @POST("/oauth2/token")
    void requestToken(@Header("Authorization") String authorization, @Field("code") String code, @Field("grant_type") String grantType, Callback<AuthenticationResponse> callback);

}
