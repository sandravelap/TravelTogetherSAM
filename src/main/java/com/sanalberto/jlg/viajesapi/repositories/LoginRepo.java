package com.sanalberto.jlg.viajesapi.repositories;

import com.sanalberto.jlg.viajesapi.database.EmfSingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

public class LoginRepo {
    //Cargamos el patrón Singleton del EntityManagerFactory
    EntityManagerFactory emf = EmfSingleton.getInstance().getEmf();


    public Boolean findAdmin(String correo, String password) {
        Boolean isRegistered = false;
        try (EntityManager em = emf.createEntityManager()) {
            // Buscamos si existe un usuario con el correo y la contraseña y devolvemos true o false
            Long ocurrencias = em.createQuery("select count (u) from Usuario u where u.correo = :correo and u.pass = :password", Long.class).
                    setParameter("correo", correo).setParameter("password", password).getSingleResult();
            if (ocurrencias == 1) {
                isRegistered = true;
            }

        } catch (NoResultException e) {
            isRegistered= false; // El usuario no existe
        }
        return isRegistered;
    }
}
