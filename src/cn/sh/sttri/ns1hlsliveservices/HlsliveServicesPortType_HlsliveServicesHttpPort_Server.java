
package cn.sh.sttri.ns1hlsliveservices;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.5.5
 * 2015-12-15T09:57:39.768+08:00
 * Generated source version: 2.5.5
 * 
 */
 
public class HlsliveServicesPortType_HlsliveServicesHttpPort_Server{

    protected HlsliveServicesPortType_HlsliveServicesHttpPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new HlsliveServicesPortTypeImpl();
        String address = "http://127.0.0.1:8081/HlsliveServices/services/HlsliveServices";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new HlsliveServicesPortType_HlsliveServicesHttpPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
