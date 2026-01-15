package com.sanalberto.jlg.viajesapi.services;

import com.sanalberto.jlg.viajesapi.Entities.Usuario;
import com.sanalberto.jlg.viajesapi.Entities.Viaje;
import com.sanalberto.jlg.viajesapi.Exceptions.DatabaseConnectionException;
import com.sanalberto.jlg.viajesapi.dtos.MisViajesDTO;
import com.sanalberto.jlg.viajesapi.dtos.ViajeDTO;
import com.sanalberto.jlg.viajesapi.repositories.GetUsuarioByAliasRepo;
import com.sanalberto.jlg.viajesapi.repositories.MisViajesRepo;

import java.util.ArrayList;
import java.util.List;

public class MisViajesServices {
    private static GetUsuarioByAliasRepo getUsuarioByAliasRepo = new GetUsuarioByAliasRepo();
    private static MisViajesRepo misViajesRepo = new MisViajesRepo();


    public static ArrayList<MisViajesDTO> mostrarViajesbyAlias(String alias){

        ArrayList<MisViajesDTO> misViajes = new ArrayList<>();
        //Llamamos a la función que nos permite obtener los datos del usuario a través del alias
       try {
           Usuario usuario = getUsuarioByAliasRepo.findByAlias(alias);
           MisViajesDTO misViajesDTO;
           // Función que recupera los viajes del usuario por su ID
           List<Viaje> viajesDAO = misViajesRepo.getViajesbyID(usuario);
           for (Viaje viaje : viajesDAO) {
               misViajesDTO = new MisViajesDTO();
               misViajesDTO.setNombre(viaje.getNombre());
               misViajesDTO.setDescripcion(viaje.getDescripcion());
               misViajesDTO.setParticipantes(viaje.getParticipantes());
               misViajesDTO.setFechaInicio(viaje.getFechaInicio());
               misViajesDTO.setFechaFin(viaje.getFechaFin());
               misViajesDTO.setTabaco(viaje.getTabaco());
               misViajesDTO.setMascota(viaje.getMascota());
               misViajes.add(misViajesDTO);
           }
       } catch (DatabaseConnectionException e) {
           System.err.println("Error de infraestructura: " + e.getMessage());
           throw e;
       }
        return misViajes;
    }
}
