package org.bitreserve.bitreserve_android_sdk.model;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;

/**
 * Base model.
 */

public class BaseModel {

    private BitreserveRestAdapter bitreserveRestAdapter;

    /**
     * Constructor.
     */

    public BaseModel() {
        this.bitreserveRestAdapter = new BitreserveRestAdapter();
    }

    /**
     * Gets the bitreserve rest adapter.
     *
     * @return the bitreserve rest adapter.
     */

    public BitreserveRestAdapter getBitreserveRestAdapter() {
        return bitreserveRestAdapter;
    }

    /**
     * Sets the bitreserve rest adapter.
     *
     * @param bitreserveRestAdapter the bitreserve rest adapter.
     */

    public void setBitreserveRestAdapter(BitreserveRestAdapter bitreserveRestAdapter) {
        this.bitreserveRestAdapter = bitreserveRestAdapter;
    }

}
