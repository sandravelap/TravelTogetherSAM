package com.sanalberto.jlg.viajesapi.repositories;


import com.sanalberto.jlg.viajesapi.Entities.Viaje;
import com.sanalberto.jlg.viajesapi.database.EmfSingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.util.List;


public class ViajesRepo {

    // Instancia la conexión a la base de datos.
    public static List<Viaje> getViajes(){
        List<Viaje> viajes;
        EntityManagerFactory emf = EmfSingleton.getInstance().getEmf();
        try (EntityManager em = emf.createEntityManager()) {


            // Crea la query JPL para recuperar la información.
            Query queryGetViajes = em.createQuery("select v from Viaje v");

            // Realiza la consulta y recupera los resultados.
            viajes =  queryGetViajes.getResultList();

        } catch (NoResultException e) {

            // Si la consulta no devuelve ningún resultado.
            viajes = null;

        }
        return viajes;
    }
}