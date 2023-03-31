package com.sample.rest;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 *
 */
public class Main {
  // Base URI the Grizzly HTTP server will listen on
//    public static final String BASE_URI = "http://localhost:8080/myapp";
  public static final String BASE_URI;
  private static final String protocol;
  private static final Optional<String> host;
  private static final Optional<String> port;
  private static final String path;

  static {
    protocol = "http://";
    host = Optional.ofNullable(System.getenv("HOSTNAME"));
    port = Optional.ofNullable(System.getenv("PORT"));
    path = "myapp";
    BASE_URI = protocol + host.orElse("localhost") + ":" + port.orElse("8080") + "/"
        + path + "/";
  }

  /**
   * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
   * application.
   * 
   * @return Grizzly HTTP server.
   */
  public static HttpServer startServer() {
    // create a resource config that scans for JAX-RS resources and providers
    // in com.sample.rest package
    final ResourceConfig rc = new ResourceConfig().packages("com.sample.rest");

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
  public static void main(String[] args) throws IOException {
    final HttpServer server = startServer();
    System.out.println(String.format("Jersey app started with endpoints available at "
        + "%s%nHit Ctrl-C to stop it...", BASE_URI));
    System.in.read();
    server.stop();
  }
}
