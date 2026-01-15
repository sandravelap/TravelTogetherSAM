package com.sanalberto.jlg.viajesapi.repositories;

import com.sanalberto.jlg.viajesapi.Entities.Usuario;
import com.sanalberto.jlg.viajesapi.Exceptions.DatabaseConnectionException;
import com.sanalberto.jlg.viajesapi.database.EmfSingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

public class GetUsuarioByAliasRepo {

    // Patrón Singleton para crear un único EntityManagerFactory
    EntityManagerFactory emf = EmfSingleton.getInstance().getEmf();
    public Usuario findByAlias(String alias){
        Usuario usuario = null;
        try(EntityManager em = emf.createEntityManager()){
            // Consulta que devuelve el usuario por su alias
            Query consulta = em.createQuery("SELECT u FROM Usuario u WHERE u.alias = :alias").setParameter("alias", alias);
            usuario = (Usuario) consulta.getSingleResult();
        } catch (Exception e) {
            throw new DatabaseConnectionException("Error crítico al conectar con la base de datos.", e);
        }
        return usuario;
    }
}
