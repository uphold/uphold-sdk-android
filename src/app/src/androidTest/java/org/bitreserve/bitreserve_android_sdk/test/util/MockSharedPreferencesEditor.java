package org.bitreserve.bitreserve_android_sdk.test.util;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Set;

/**
 * MockSharedPreferencesEditor.
 */

public class MockSharedPreferencesEditor implements SharedPreferences.Editor {

    private HashMap<String, Object> mockValues;

    public MockSharedPreferencesEditor(HashMap<String, Object> mockValues) {
        this.mockValues = mockValues;
    }

    @Override
    public SharedPreferences.Editor putString(String key, String value) {
        mockValues.put(key, value);

        return new MockSharedPreferencesEditor(mockValues);
    }

    @Override
    public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
        return null;
    }

    @Override
    public SharedPreferences.Editor putInt(String key, int value) {
        return null;
    }

    @Override
    public SharedPreferences.Editor putLong(String key, long value) {
        return null;
    }

    @Override
    public SharedPreferences.Editor putFloat(String key, float value) {
        return null;
    }

    @Override
    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        return null;
    }

    @Override
    public SharedPreferences.Editor remove(String key) {
        mockValues.remove(key);

        return new MockSharedPreferencesEditor(mockValues);
    }

    @Override
    public SharedPreferences.Editor clear() {
        return null;
    }

    @Override
    public boolean commit() {
        return false;
    }

    @Override
    public void apply() {

    }

}
