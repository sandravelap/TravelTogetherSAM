package com.sanalberto.jlg.viajesapi.services;

import com.sanalberto.jlg.viajesapi.NuevoViajeResource;
import com.sanalberto.jlg.viajesapi.database.ConexionSingleton;
import com.sanalberto.jlg.viajesapi.models.ViajeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertViajesServicios {

    public InsertViajesServicios(){}

    NuevoViajeResource nuevoViajeResource = new NuevoViajeResource();
    ViajeModel viajeModelTransferido = nuevoViajeResource.transferirNuevoViaje();

    public boolean saveNuevoViaje(){

        boolean respuesta = false;

        if(viajeModelTransferido.checkPetConfiguration(viajeModelTransferido.getPet_configuration())){
            try(Connection conexion = ConexionSingleton.conectar()){
                PreparedStatement sentencia = conexion.prepareStatement("insert into viaje values (?, ?, ?, ?, ?, ?, ?, ?, ?);");

                // todo: ¿id?
                sentencia.setInt(2, viajeModelTransferido.getUser_id());
                sentencia.setString(3, viajeModelTransferido.getTrip_name());
                sentencia.setString(4, viajeModelTransferido.getTrip_description());
                sentencia.setInt(5, viajeModelTransferido.getParticipants());
                sentencia.setDate(6, viajeModelTransferido.getInit_date());
                sentencia.setDate(7, viajeModelTransferido.getEnd_date());
                sentencia.setBoolean(8, viajeModelTransferido.isTobacco());
                sentencia.setString(9, viajeModelTransferido.getPet_configuration());

                sentencia.executeUpdate();
                respuesta = true;
            }
            catch (SQLException e) {
                System.out.println("BBDD >> Error en InsertViajesServices saveNuevoViaje");
            }
        }

        return respuesta;
    }

}