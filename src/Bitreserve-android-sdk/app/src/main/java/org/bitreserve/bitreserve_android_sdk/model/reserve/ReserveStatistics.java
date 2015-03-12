package org.bitreserve.bitreserve_android_sdk.model.reserve;

import java.util.List;

/**
 * Reserve statistics model.
 */

public class ReserveStatistics {

    private final String currency;
    private final Total totals;
    private final List<Value> values;

    /**
     * Constructor.
     *
     * @param currency The currency from the reserve.
     * @param totals The commissions, transaction volume, assets and liabilities.
     * @param values The value of held in the associated currency in all supported forms.
     */

    public ReserveStatistics(String currency, Total totals, List<Value> values) {
        this.currency = currency;
        this.totals = totals;
        this.values = values;
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
     * Gets the commissions, transaction volume, assets and liabilities.
     *
     * @return the {@link Total}
     */

    public Total getTotals() {
        return totals;
    }


    /**
     * Gets the value of held in the associated currency in all supported forms.
     *
     * @return the {@link Value} of held in the associated currency in all supported forms
     */

    public List<Value> getValues() {
        return values;
    }

}
