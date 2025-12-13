package com.sanalberto.jlg.viajesapi.services;

import com.sanalberto.jlg.viajesapi.Entities.Viaje;
import com.sanalberto.jlg.viajesapi.dtos.ViajeDTO;
import com.sanalberto.jlg.viajesapi.repositories.ViajesRepo;

import java.util.ArrayList;
import java.util.List;

public class ViajesServices {
    private static ViajesRepo viajesRepo = new ViajesRepo();
    public static ArrayList<ViajeDTO> mostrarViajes(){
        ArrayList<ViajeDTO> viajes = new ArrayList<>();
        ViajeDTO viajeDTO;
        List<Viaje> viajesDAO = viajesRepo.getViajes();
        for (Viaje viaje : viajesDAO) {
            viajeDTO = new ViajeDTO();
            viajeDTO.setNombre(viaje.getNombre());
            viajeDTO.setDescripcion(viaje.getDescripcion());
            viajes.add(viajeDTO);
        }
        return viajes;
    }
}