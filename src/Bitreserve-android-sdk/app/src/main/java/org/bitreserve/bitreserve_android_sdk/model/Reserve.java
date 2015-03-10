package org.bitreserve.bitreserve_android_sdk.model;

import org.bitreserve.bitreserve_android_sdk.model.reserve.Total;
import org.bitreserve.bitreserve_android_sdk.model.reserve.Value;
import java.util.List;

/**
 * This class represents the reserve model.
 */

public class Reserve {

    private final String currency;
    private final List<Value> values;
    private final Total totals;

    /**
     * Constructor.
     *
     * @param currency The currency from the reserve.
     * @param values The value of held in the associated currency in all supported forms.
     * @param totals The commissions, transaction volume, assets and liabilities.
     */

    public Reserve(String currency, List<Value> values, Total totals) {
        this.currency = currency;
        this.values = values;
        this.totals = totals;
    }

    /**
     * Gets the currency from the reserve.
     *
     * @return the currency from the reserve
     */

    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the value of held in the associated currency in all supported forms.
     *
     * @return the {@link Value} of held in the associated currency in all supported forms
     */

    public List<Value> getValues() {
        return values;
    }

    /**
     * Gets the commissions, transaction volume, assets and liabilities.
     *
     * @return the {@link Total}
     */

    public Total getTotals() {
        return totals;
    }
}
