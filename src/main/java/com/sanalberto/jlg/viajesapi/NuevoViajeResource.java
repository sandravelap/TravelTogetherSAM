package com.sanalberto.jlg.viajesapi;

import com.google.gson.Gson;
import com.sanalberto.jlg.viajesapi.models.ViajeModel;
import com.sanalberto.jlg.viajesapi.services.InsertViajesServicios;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

// Nota personal: Para acceder al recurso, primero se debe acceder a la ruta de la clase "ApiApplication" y luego a la ruta del recurso.
// http://localhost:8088/travelTogether/ViajesAPI/NuevoViaje/

@Path("/NuevoViaje")
public class NuevoViajeResource {

    private ViajeModel viajeModel = null;
    private InsertViajesServicios insertViajesServicios = new InsertViajesServicios();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)

    // todo: comprobar que el usuario está auntenticado recuperando el token (usar repositorio de Jose Luis)
    public String guardarNuevoViaje(Object entradaJson){
        String estado = "";
        Gson gson = new Gson();

        viajeModel = gson.fromJson((String) entradaJson, ViajeModel.class);

        if(insertViajesServicios.saveNuevoViaje()){
            estado = "201 - VIAJE CREADO EXITOSAMENTE";
        }
        else{
            estado = "400 - INVALIDO";
        }

        return estado;
    }

    public ViajeModel transferirNuevoViaje(){
        return this.viajeModel;
    }

}
