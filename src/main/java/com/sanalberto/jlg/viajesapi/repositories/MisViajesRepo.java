package com.sanalberto.jlg.viajesapi.repositories;

import com.sanalberto.jlg.viajesapi.Entities.Usuario;
import com.sanalberto.jlg.viajesapi.Entities.Viaje;
import com.sanalberto.jlg.viajesapi.database.EmfSingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.util.List;

public class MisViajesRepo {
    EntityManagerFactory emf = EmfSingleton.getInstance().getEmf();

    public List<Viaje> getViajesbyID(Usuario usuario){
        List<Viaje> viajes;

        try (EntityManager em = emf.createEntityManager()) {


            // Crea la query JPL para recuperar la información de los viajes por el ID del creador.
            Query queryGetViajes = em.createQuery("select v from Viaje v where v.idCreador.id = :id").setParameter("id", usuario.getId());

            // Realiza la consulta y recupera los resultados.
            viajes =  queryGetViajes.getResultList();

        } catch (NoResultException e) {

            // Si la consulta no devuelve ningún resultado.
            viajes = null;

        }
        return viajes;
    }
}
