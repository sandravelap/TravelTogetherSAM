package com.sanalberto.jlg.viajesapi;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// Esta interfaz simplemente se aplica a aquellas peticiones o métodos que requieran verificar el token.
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtTokenNeeded { }