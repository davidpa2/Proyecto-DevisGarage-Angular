package com.example.demo.seguridad;

import java.security.Key;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.model.entities.Mecanico;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class AutenticadorJWT {
	
	private static Key key = null; // Clave de encriptación que usaremos para generar el JWT. Diferente en cada ejecución
	
	/**
	 * A partir de un usuario, nos genera un JWT que contiene el id del mismo en forma de String
	 */
	public static String codificaJWT (Mecanico usu) {
		String jws = Jwts.builder().setSubject("" + usu.getId()).
				signWith(SignatureAlgorithm.HS512, getGeneratedKey()).compact();
		return jws;
	}
	
	/**
	 * From a jwt, it locates the user id whose id it keeps inside
	 */
	public static int getIdUsuarioDesdeJWT (String jwt) {
		try {
			String stringIdUsuario = Jwts.parser().setSigningKey(getGeneratedKey()).parseClaimsJws(jwt).getBody().getSubject();
			int idUsuario = Integer.parseInt(stringIdUsuario);
			return idUsuario;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	
	/**
	 * Obtiene el id de un usuario almacenado en un JWT que proviene de un request
	 */
	public static int getIdUsuarioDesdeJwtIncrustadoEnRequest (HttpServletRequest request) {
		String autHeader = request.getHeader("Authorization");
		System.out.println("El authHeader " + autHeader);
		if (autHeader != null && autHeader.length() > 8) {
			String jwt = autHeader.substring(7);
			System.out.println("El jwt " + jwt);
			return getIdUsuarioDesdeJWT(jwt);
		}
		else {
			return -1; /** Imposible generar usuario**/
		}
	}
	
	/**
	 * Genera una nueva clave, cada vez que se inicia el servidor, para encriptar los JWT
	 * @return
	 */
	private static Key getGeneratedKey () {
		if (key == null) {
			key = MacProvider.generateKey();
		}
		return key;
	}

}
