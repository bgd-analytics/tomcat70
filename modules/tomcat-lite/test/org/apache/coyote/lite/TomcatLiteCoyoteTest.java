/*
 */
package org.apache.coyote.lite;

import java.io.IOException;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.lite.LiteProtocolHandler;
import org.apache.tomcat.lite.http.DefaultHttpConnector;
import org.apache.tomcat.lite.http.HttpChannel;
import org.apache.tomcat.lite.http.HttpConnector;
import org.apache.tomcat.lite.io.BBuffer;

public class TomcatLiteCoyoteTest extends TestCase {

    static Tomcat tomcat;
    LiteProtocolHandler litePH; 
    
    public void setUp() {
        if (tomcat == null) {
            try {
                tomcat = new Tomcat();
                tomcat.setPort(8885);
                tomcat.setBaseDir("output/build");
                
                tomcat.addWebapp("/examples", "examples");
                tomcat.addWebapp("/", "ROOT");

                
                //tomcat.addServlet(ctx, "name", "class");
                // ctx.addServletMapping("/foo/*", "name");
                
                litePH = setUp(tomcat);
                
                tomcat.start();
            } catch (LifecycleException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ServletException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public static LiteProtocolHandler setUp(Tomcat tomcat) {
        Connector connector = new Connector(LiteProtocolHandler.class.getName());
        tomcat.getService().addConnector(connector);
        connector.setPort(8885);
        tomcat.setConnector(connector);
        LiteProtocolHandler ph = 
            (LiteProtocolHandler) connector.getProtocolHandler();
        return ph;
    }

    
    public void testSimple() throws IOException {
        HttpConnector clientCon = DefaultHttpConnector.get();
        HttpChannel ch = clientCon.get("localhost", 8885);
        ch.getRequest().setRequestURI("/examples/servlets/servlet/HelloWorldExample");
        ch.sendRequest();
        BBuffer res = ch.readAll(null, 0);
        
        assertTrue(res.toString().indexOf("<title>Hello World!</title>") >= 0);
    }
    
    
}