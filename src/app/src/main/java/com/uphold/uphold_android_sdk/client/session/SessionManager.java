package com.uphold.uphold_android_sdk.client.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.uphold.uphold_android_sdk.model.Token;

/**
 * Uphold session manager.
 */

public enum SessionManager {

    INSTANCE;

    private final String CACHED_ACCESS_TOKEN_KEY = "com.uphold.uphold_android_sdk.SessionManager.CachedAccessToken";
    private final String SHARED_PREFERENCES = "com.uphold.uphold_android_sdk.SessionManager.SharedPreferences";
    private SharedPreferences sharedPreferences;

    /**
     * Constructor.
     */

    SessionManager() {
        if (com.uphold.uphold_android_sdk.client.UpholdClient.getApplicationContext() != null) {
            initSharedPreferences();
        }
    }

    /**
     * Gets the bearer token.
     *
     * @return the bearer token.
     */

    public String getBearerToken() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(CACHED_ACCESS_TOKEN_KEY, null);
        }

        if (com.uphold.uphold_android_sdk.client.UpholdClient.getApplicationContext() != null) {
            initSharedPreferences();

            return sharedPreferences.getString(CACHED_ACCESS_TOKEN_KEY, null);
        }

        return null;
    }

    /**
     * Sets the bearer token.
     *
     * @param token The bearer token.
     */

    public void setBearerToken(Token token) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(CACHED_ACCESS_TOKEN_KEY, token.getBearerToken()).apply();
        }

        if (com.uphold.uphold_android_sdk.client.UpholdClient.getApplicationContext() != null) {
            initSharedPreferences();
            sharedPreferences.edit().putString(CACHED_ACCESS_TOKEN_KEY, token.getBearerToken()).apply();
        }
    }

    /**
     * Invalidates current session.
     */

    public void invalidateSession() {
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove(CACHED_ACCESS_TOKEN_KEY).apply();
        }

        if (com.uphold.uphold_android_sdk.client.UpholdClient.getApplicationContext() != null) {
            initSharedPreferences();
            sharedPreferences.edit().remove(CACHED_ACCESS_TOKEN_KEY).apply();
        }
    }

    /**
     *  Initialize the shared preferences object.
     */

    private void initSharedPreferences() {
        sharedPreferences = com.uphold.uphold_android_sdk.client.UpholdClient.getApplicationContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

}
