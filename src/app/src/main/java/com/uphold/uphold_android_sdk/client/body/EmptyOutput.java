package com.uphold.uphold_android_sdk.client.body;

import java.io.IOException;
import java.io.OutputStream;

import retrofit.mime.TypedOutput;

/**
 * Empty output body.
 */

public class EmptyOutput implements TypedOutput {

    public static final TypedOutput INSTANCE = new EmptyOutput();

    /**
     * Constructor.
     */

    private EmptyOutput() {
    }

    /**
     * Gets the file name.
     *
     * @return null since it is not used.
     */

    @Override
    public String fileName() {
        return null;
    }

    /**
     * Gets the mime type.
     *
     * @return application/json to force the request format.
     */

    @Override
    public String mimeType() {
        return "application/json";
    }

    /**
     * Gets the body length.
     *
     * @return the body length.
     */

    @Override
    public long length() {
        return 0;
    }

    /**
     * Writes these bytes to the given output stream.
     *
     * @param out The output stream.
     *
     * @throws IOException if the output stream is not valid.
     */

    @Override
    public void writeTo(OutputStream out) throws IOException {
    }

}
