package com.uphold.uphold_android_sdk.client.restadapter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * SSLSocketFactory factory.
 *
 * This ssl socket factory should enable the protocol TLSv1.2 on devices with Android API level 16 or higher.
 */

public class TLSV12SSLSocketFactory extends SSLSocketFactory {

    private SSLSocketFactory internalSSLSocketFactory;

    /**
     * Constructor.
     *
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */

    public TLSV12SSLSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, null, null);

        internalSSLSocketFactory = context.getSocketFactory();
    }

    /**
     * Returns the names of the cipher suites that are enabled by default.
     *
     * @return the names of the cipher suites that are enabled by default.
     */

    @Override
    public String[] getDefaultCipherSuites() {
        return internalSSLSocketFactory.getDefaultCipherSuites();
    }

    /**
     * Returns the names of the cipher suites that are supported and could be enabled for an SSL connection.
     *
     * @return the names of the cipher suites that are supported.
     */

    @Override
    public String[] getSupportedCipherSuites() {
        return internalSSLSocketFactory.getSupportedCipherSuites();
    }
    
    /**
     * Creates an {@code SSLSocket} over the specified socket that is connected to the specified host at the specified port.
     *
     * @param host The host.
     * @param port The port.
     *
     * @return the ssl socket.
     *
     * @throws IOException if creating the socket fails.
     */

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return enableTLSV12OnSocket(internalSSLSocketFactory.createSocket(host, port));
    }

    /**
     * Creates an {@code SSLSocket} over the specified socket that is connected to the specified host at the specified port.
     *
     * @param host The host.
     * @param port The port.
     *
     * @return the ssl socket.
     *
     * @throws IOException if creating the socket fails.
     */

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return enableTLSV12OnSocket(internalSSLSocketFactory.createSocket(host, port));
    }

    /**
     * Creates an {@code SSLSocket} over the specified socket that is connected to the specified host at the specified port.
     *
     * @param socket The socket.
     * @param host The host.
     * @param port The port.
     * @param autoClose A boolean indicating if the socket should be automatically closed.
     *
     * @return the ssl socket.
     *
     * @throws IOException if creating the socket fails.
     */

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        return enableTLSV12OnSocket(internalSSLSocketFactory.createSocket(socket, host, port, autoClose));
    }

    /**
     * Creates an {@code SSLSocket} over the specified socket that is connected to the specified host at the specified port.
     *
     * @param host The host.
     * @param port The port.
     * @param localHost The local host.
     * @param localPort The local port.
     *
     * @return the ssl socket.
     *
     * @throws IOException if creating the socket fails.
     */

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        return enableTLSV12OnSocket(internalSSLSocketFactory.createSocket(host, port, localHost, localPort));
    }

    /**
     * Creates an {@code SSLSocket} over the specified socket that is connected to the specified address at the specified port.
     *
     * @param address The address.
     * @param port The port.
     * @param localAddress The local address.
     * @param localPort The local port.
     *
     * @return the ssl socket.
     *
     * @throws IOException
     */

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return enableTLSV12OnSocket(internalSSLSocketFactory.createSocket(address, port, localAddress, localPort));
    }

    /**
     * Enables only the protocol TLSv1.2 for the requests made by the client.
     *
     * @param socket The socket.
     *
     * @return the ssl socket.
     */

    private Socket enableTLSV12OnSocket(Socket socket) {
        if(socket != null && (socket instanceof SSLSocket)) {
            ((SSLSocket)socket).setEnabledProtocols(new String[] {"TLSv1.2"});
        }

        return socket;
    }

}