package com.uphold.uphold_android_sdk.test.unit.util;

import android.content.SharedPreferences;
import android.test.mock.MockContext;

import java.util.HashMap;

/**
 * MockSharedPreferencesContext.
 */

public class MockSharedPreferencesContext extends MockContext {

    private HashMap<String, Object> mockValues;

    public MockSharedPreferencesContext() {
        mockValues = new HashMap<>();
    }

    public MockSharedPreferencesContext(HashMap<String, Object> mockValues) {
        this.mockValues = mockValues;
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return new MockSharedPreferences(mockValues);
    }

}
