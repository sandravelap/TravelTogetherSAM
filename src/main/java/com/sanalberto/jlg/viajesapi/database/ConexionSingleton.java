package com.sanalberto.jlg.viajesapi.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSingleton {

    private ConexionSingleton(){};

    private static Connection conexion = null;

    private static final String url = "jdbc:mysql://localhost:3306/traveltogether";
    private static final String usuario = "root";
    private static final String password = "root";

    public static Connection conectar(){

        if(conexion == null){
            try{
                conexion = DriverManager.getConnection(url, usuario, password);
            }
            catch(SQLException e1){
                System.out.println("Imposible conectar.");
                System.out.println("ERROR: ha habido un error al conectar con el SGBD -> "+e1.getMessage());
                e1.printStackTrace();
            }
        }

        return conexion;
    }

    public void desconectar(){
        try{
            conexion.close();
        }
        catch (SQLException e1){
            System.out.println("ERROR: ha habido un problema al desconectar el SGBD -> "+e1.getMessage());
            e1.printStackTrace();
        }
        finally {
            conexion = null;
        }
    }

}