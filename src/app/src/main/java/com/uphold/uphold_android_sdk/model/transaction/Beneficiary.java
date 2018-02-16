package com.uphold.uphold_android_sdk.model.transaction;

import java.io.Serializable;

/**
 * Beneficiary model.
 */

public class Beneficiary implements Serializable {

    private final BeneficiaryAddress address;
    private final String name;
    private final String relationship;

    /**
     * Constructor.
     *
     * @param address The address.
     * @param name The name.
     * @param relationship The relationship.
     */

    public Beneficiary(BeneficiaryAddress address, String name, String relationship) {
        this.address = address;
        this.name = name;
        this.relationship = relationship;
    }

    /**
     * Gets the address.
     *
     * @return return the address.
     */

    public BeneficiaryAddress getAddress() {
        return address;
    }

    /**
     * Gets the name.
     *
     * @return return the name.
     */

    public String getName() {
        return name;
    }

    /**
     * Gets the relationship.
     *
     * @return return the relationship.
     */

    public String getRelationship() {
        return relationship;
    }

}
