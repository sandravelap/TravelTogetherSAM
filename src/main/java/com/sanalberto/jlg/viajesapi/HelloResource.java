package com.sanalberto.jlg.viajesapi;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

// todo: por confirmar.
// Nota personal: Para acceder al recurso, primero se debe acceder a la ruta de la clase "HelloApplication" y luego a la ruta del recurso.
// http://localhost:8088/travelTogether/api/hello-api/

@Path("/NuevoViaje")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hola desde la API de los viajes!";
    }

    public String guardarNuevoViaje(){
        return "";
    }
}
