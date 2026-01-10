package com.sanalberto.jlg.viajesapi.repositories;

import com.sanalberto.jlg.viajesapi.Entities.Usuario;
import com.sanalberto.jlg.viajesapi.database.EmfSingleton;
import com.sanalberto.jlg.viajesapi.dtos.LoginDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class LoginRepo {
    //Cargamos el patrón Singleton del EntityManagerFactory
    EntityManagerFactory emf = EmfSingleton.getInstance().getEmf();


    public Usuario findAdmin(LoginDTO loginDTO) {

        Usuario usuario = null;
        try (EntityManager em = emf.createEntityManager()) {
            // Buscamos si existe un usuario con el correo y la contraseña y devolvemos el usuario
            Query consulta = em.createQuery("select u from Usuario u where u.correo = :correo and u.pass = :password").
                    setParameter("correo", loginDTO.getCorreo()).setParameter("password", loginDTO.getPassword());

            usuario = (Usuario) consulta.getSingleResult();
        } catch (NoResultException e) {
            usuario = null; // El usuario no existe
        }
        return usuario;
    }
}