/*
 */
package org.apache.tomcat.lite.io;

import java.io.IOException;


/**
 * Factory for IOChannels, with support for caching.
 * 
 * 
 * @author Costin Manolache
 */
public abstract class IOConnector {

    public static interface DataReceivedCallback {
        /** 
         * Called when data or EOF has been received.
         */
        public void handleReceived(IOChannel ch) throws IOException;
    }

    public static interface ConnectedCallback {
        public void handleConnected(IOChannel ch) throws IOException;
    }

    public static interface DataFlushedCallback {
        public void handleFlushed(IOChannel ch) throws IOException;
    }
    
    public abstract void acceptor(IOConnector.ConnectedCallback sc, 
                         CharSequence port, Object extra)
        throws IOException; 

    // TODO: failures ? 
    public abstract void connect(String host, int port, 
                                      IOConnector.ConnectedCallback sc) throws IOException;
    
    public void stop() {
        
    }
}