package com.sanalberto.jlg.viajesapi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sanalberto.jlg.viajesapi.Exceptions.DatabaseConnectionException;
import com.sanalberto.jlg.viajesapi.dtos.MisViajesDTO;
import com.sanalberto.jlg.viajesapi.services.MisViajesServices;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/misViajes")
public class MisViajesResource {
    //Se añade esta clase para que el backend no bloquee la llamada desde el frontend
    @OPTIONS
    @Path("{path: .*}")
    public Response options() {
        return Response.ok().build();
    }
    private MisViajesServices MisViajesServices = new MisViajesServices();


    @GET
    @Produces("application/json")
    //Esta anotación indica que la petición requiere token
    @JwtTokenNeeded
    //Llamamos al Context para indicar que recibimos el token del frontend
    public String mostrarViajesbyAlias(@Context SecurityContext securityContext) {
        // De esta forma extraemos el alias del token para poder utilizarlo
        String alias = securityContext.getUserPrincipal().getName();
        String respuesta = "";
        // Crea el constructor de parseadores gson.
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Crea el gson con la opción de indentar correctamente al generarlo.
        Gson gson = gsonBuilder.setPrettyPrinting().create();
      try {
          List<MisViajesDTO> misViajes = MisViajesServices.mostrarViajesbyAlias(alias);



          // Genera el String a volcar en el archivo.
          respuesta = gson.toJson(misViajes);
      } catch (DatabaseConnectionException e) {
          respuesta = "Error al conectar a la base de datos";

      }

        // Devuelve un json con la información.
        return respuesta;
    }
}