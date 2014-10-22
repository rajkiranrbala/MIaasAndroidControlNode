package com.sjsu.taas.rest;

import com.sjsu.taas.controller.AndroidEmulatorManager;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

/**
 * Main class.
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
	
    public static String BASE_URI = "http://localhost:8080/node/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     * @throws UnknownHostException 
     */
    public static HttpServer startServer() throws UnknownHostException {
    	
    	BASE_URI = "http://"+  "0.0.0.0" + ":3345/node/";
    	
        // create a resource config that scans for JAX-RS resources and providers
        // in com.sjsu.taas package
        final ResourceConfig rc = new ResourceConfig().packages("com.sjsu.taas.rest")
                .register(MultiPartFeature.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI


        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    
    public static void  stopServer()
    {
    	server.stop();
        System.exit(0);
    }
    
    private static HttpServer server ;
    public static void main(String[] args) throws Exception {
        AndroidEmulatorManager.inititalize();
        server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

