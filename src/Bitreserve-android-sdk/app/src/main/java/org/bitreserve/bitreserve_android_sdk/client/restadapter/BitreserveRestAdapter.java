package org.bitreserve.bitreserve_android_sdk.client.restadapter;

import org.bitreserve.bitreserve_android_sdk.config.GlobalConfigurations;
import org.bitreserve.bitreserve_android_sdk.model.Token;
import org.bitreserve.bitreserve_android_sdk.util.Header;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Bitreserve rest adapter.
 */

public class BitreserveRestAdapter {

    /**
     * Generates a bitreserve rest adapter.
     *
     * @param token The token (if available) of the user.
     *
     * @return the {@link RestAdapter}.
     */

    public static RestAdapter getRestAdapter(final Token token) {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                HashMap<String, String> map = Header.getHeaders();

                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    request.addHeader(key, value);
                }

                if (token != null && !TextUtils.isEmpty(token.getToken())) {
                    request.addHeader("Authorization", Header.encodeCredentialsForBasicAuthorization(token.getToken(), "X-OAuth-Basic"));
                }
            }
        };

        return new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setRequestInterceptor(requestInterceptor).setLogLevel(GlobalConfigurations.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE).build();
    }

}
