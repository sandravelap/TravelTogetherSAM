package com.sanalberto.jlg.viajesapi.database;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EmfSingleton {

    // Instancia el singleton de la factoría.
    private static EmfSingleton emfSInstancia = new EmfSingleton();

    // Unidad de persistencia en la que están las entidades.
    static private final String PERSISTENCE_UNIT_NAME = "default";

    // La factoría se define como privada.
    private EntityManagerFactory emf = null;

    // Metodo que devuelve la instancia del singleton que permite acceder a la factoría.
    public static EmfSingleton getInstance() {
        return emfSInstancia;
    }

    private EmfSingleton() {
    }

    // Los Entity Manager se crearán a partir de la factoría que devuelve este metodo.
    public EntityManagerFactory getEmf() {

        // La factoría solo se crea la primera vez que se llama al metodo.
        if (this.emf == null)
            this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        return this.emf;

    }

}