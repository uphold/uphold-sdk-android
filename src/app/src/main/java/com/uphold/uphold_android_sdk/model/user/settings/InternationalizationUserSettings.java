package com.uphold.uphold_android_sdk.model.user.settings;

import com.uphold.uphold_android_sdk.model.user.settings.internationalizationusersettings.InternationalizationUserSetting;

import java.io.Serializable;

/**
 * Internationalization user settings model.
 */

public class InternationalizationUserSettings implements Serializable {

    private final InternationalizationUserSetting language;
    private final InternationalizationUserSetting dateTimeFormat;
    private final InternationalizationUserSetting numberFormat;

    /**
     * Constructor.
     *
     * @param language The internationalization language user settings.
     * @param dateTimeFormat The internationalization date time format user settings.
     * @param numberFormat The internationalization number format user settings.
     */

    public InternationalizationUserSettings(InternationalizationUserSetting language, InternationalizationUserSetting dateTimeFormat, InternationalizationUserSetting numberFormat) {
        this.language = language;
        this.dateTimeFormat = dateTimeFormat;
        this.numberFormat = numberFormat;
    }

    /**
     * Gets the language internationalization user settings.
     *
     * @return {@link InternationalizationUserSetting} for the language
     */

    public InternationalizationUserSetting getLanguage() {
        return language;
    }

    /**
     * Gets the language internationalization user settings.
     *
     * @return {@link InternationalizationUserSetting} for the date time format
     */

    public InternationalizationUserSetting getDateTimeFormat() {
        return dateTimeFormat;
    }

    /**
     * Gets the language internationalization user settings.
     *
     * @return {@link InternationalizationUserSetting} for the number format
     */

    public InternationalizationUserSetting getNumberFormat() {
        return numberFormat;
    }

}
