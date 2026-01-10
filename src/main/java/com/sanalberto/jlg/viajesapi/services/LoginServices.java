package com.sanalberto.jlg.viajesapi.services;

import com.sanalberto.jlg.viajesapi.Entities.Usuario;
import com.sanalberto.jlg.viajesapi.dtos.LoginDTO;
import com.sanalberto.jlg.viajesapi.repositories.LoginRepo;

public class LoginServices {
    private LoginRepo loginRepo = new LoginRepo();

    public Usuario login(LoginDTO loginDTO) {
        // Devolvemos el usuario si existe
        Usuario usuario = loginRepo.findAdmin(loginDTO);



        return usuario; // Fallo de autenticación
    }
}

