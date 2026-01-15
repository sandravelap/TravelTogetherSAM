package com.sanalberto.jlg.viajesapi.Exceptions;

//Añadida una excepción por si la conexión a la base de datos falla
public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
