package com.jamesward.restlet;

import org.restlet.Component;
import org.restlet.data.Protocol;

public class Webapp {

   public static void main(String[] args) throws Exception {

      String webPort = System.getenv("PORT");
      if(webPort == null || webPort.isEmpty()) {
          webPort = "8080";
      }

      try {
          // Create a new Component.
          Component component = new Component();

          // Add a new HTTP server listening on port 8182.
          component.getServers().add(Protocol.HTTP, Integer.parseInt(webPort));

          // Attach the sample application.
          component.getDefaultHost().attach(new HelloApplication());

          // Start the component.
          component.start();
      } catch (Exception e) {
          // Something is wrong.
          e.printStackTrace();
      }

   }

}
