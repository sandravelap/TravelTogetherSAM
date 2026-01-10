package com.sanalberto.jlg.viajesapi;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.sanalberto.jlg.viajesapi.Entities.Usuario;
import com.sanalberto.jlg.viajesapi.dtos.LoginDTO;
import com.sanalberto.jlg.viajesapi.services.LoginServices;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Path("/login")
public class LoginResource {
    // Anotación para aceptar peticiones desde cualquier sitio
    @OPTIONS
    @Path("{path: .*}")
    public Response options() {
        return Response.ok().build();
    }

    private LoginServices loginServices = new LoginServices();
    @POST

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response loginAdmin(LoginDTO loginDTO) {

        Usuario usuario = loginServices.login(loginDTO);

// Si el usuario no es nulo creamos el token con el alias del usuario
        if (usuario != null) {
            String token = generateJWT(usuario.getAlias());

            // 3. Devolver el token en un mapa o POJO
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return Response.ok(response).build();
        } else {
            // Si no existe el usuario devolvemos un 401 (No autorizado)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }


    // Función que genera el token
    private String generateJWT(String username) {
        Algorithm algorithm = Algorithm.HMAC256("tu_clave_secreta_super_segura");
        return JWT.create()
                .withIssuer("tu_app")
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .sign(algorithm);
    }
}