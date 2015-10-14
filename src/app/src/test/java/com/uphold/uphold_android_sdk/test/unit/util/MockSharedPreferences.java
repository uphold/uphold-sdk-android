package com.uphold.uphold_android_sdk.test.unit.util;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * MockSharedPreferences.
 */

public class MockSharedPreferences implements SharedPreferences {

    private HashMap<String, Object> mockValues;

    public MockSharedPreferences(HashMap<String, Object> mockValues) {
        this.mockValues = mockValues;
    }

    @Override
    public Map<String, ?> getAll() {
        return null;
    }

    @Override
    public String getString(String key, String defValue) {
        if (mockValues.containsKey(key)) {
            return mockValues.get(key).toString();
        }

        return defValue;
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return null;
    }

    @Override
    public int getInt(String key, int defValue) {
        return 0;
    }

    @Override
    public long getLong(String key, long defValue) {
        return 0;
    }

    @Override
    public float getFloat(String key, float defValue) {
        return 0;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return false;
    }

    @Override
    public boolean contains(String key) {
        return false;
    }

    @Override
    public Editor edit() {
        return new MockSharedPreferencesEditor(mockValues);
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

}
