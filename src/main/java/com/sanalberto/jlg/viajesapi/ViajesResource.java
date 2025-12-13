package com.sanalberto.jlg.viajesapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sanalberto.jlg.viajesapi.dtos.ViajeDTO;
import com.sanalberto.jlg.viajesapi.services.ViajesServices;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.ArrayList;

@Path("/viajes")
public class ViajesResource {
    private ViajesServices viajesServices = new ViajesServices();
    @GET
    @Produces("application/json")
    public String mostrarViajes() {
        String respuesta = "";
        ArrayList<ViajeDTO> viajes = viajesServices.mostrarViajes();
        // Crea el constructor de parseadores gson.
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Crea el gson con la opción de indentar correctamente al generarlo.
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        // Genera el String a volcar en el archivo.
        respuesta = gson.toJson(viajes);

        // Devuelve un json con la información.
        return respuesta;
    }
}