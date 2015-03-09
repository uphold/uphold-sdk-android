package org.bitreserve.bitreserve_android_sdk.model.user;

/**
 * This class represents the current country for a user setting.
 */

public class InternationalizationUserSetting {

    private final String locale;

    /**
     * Constructor.
     *
     * @param locale The locale for the setting.
     */

    public InternationalizationUserSetting(String locale) {
        this.locale = locale;
    }

    /**
     * Gets the locale for the setting.
     *
     * @return the locale for the setting
     */

    public String getLocale() {
        return locale;
    }
}
