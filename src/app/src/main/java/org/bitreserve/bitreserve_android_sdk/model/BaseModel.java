package org.bitreserve.bitreserve_android_sdk.model;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;

import java.io.Serializable;

/**
 * Base model.
 */

public class BaseModel implements Serializable {

    private transient BitreserveRestAdapter bitreserveRestAdapter;

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
        if (this.bitreserveRestAdapter == null) {
            return new BitreserveRestAdapter();
        }

        return this.bitreserveRestAdapter;
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
