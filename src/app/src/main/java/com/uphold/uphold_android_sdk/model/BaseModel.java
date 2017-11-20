package com.uphold.uphold_android_sdk.model;

import com.uphold.uphold_android_sdk.client.restadapter.UpholdRestAdapter;

import java.io.Serializable;

/**
 * Base model.
 */

public class BaseModel implements Serializable {

    protected transient UpholdRestAdapter upholdRestAdapter;

    /**
     * Constructor.
     */

    public BaseModel() {
        this.upholdRestAdapter = new UpholdRestAdapter();
    }

    /**
     * Gets the Uphold rest adapter.
     *
     * @return the Uphold rest adapter.
     */

    public UpholdRestAdapter getUpholdRestAdapter() {
        if (this.upholdRestAdapter == null) {
            return new UpholdRestAdapter();
        }

        return this.upholdRestAdapter;
    }

    /**
     * Sets the Uphold rest adapter.
     *
     * @param upholdRestAdapter the Uphold rest adapter.
     */

    public void setUpholdRestAdapter(UpholdRestAdapter upholdRestAdapter) {
        this.upholdRestAdapter = upholdRestAdapter;
    }

}
