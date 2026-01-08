package com.sanalberto.jlg.viajesapi.services;

import com.sanalberto.jlg.viajesapi.repositories.LoginRepo;

public class LoginServices {
    private LoginRepo loginRepo = new LoginRepo();

    public String login(String correo, String password) {
        String respuesta = null;
        // Si hay un usuario con los datos devolvemos una respuesta
        if (loginRepo.findAdmin(correo, password)) {
            respuesta = "TOKEN-JWT-";
        }

        return respuesta; // Fallo de autenticación
    }
}
