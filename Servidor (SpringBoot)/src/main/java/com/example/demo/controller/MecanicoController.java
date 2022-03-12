package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.entities.Mecanico;
import com.example.demo.repository.MecanicoRepositorio;
import com.example.demo.seguridad.AutenticadorJWT;
import com.fasterxml.jackson.annotation.JsonProperty;

@CrossOrigin
@RestController
public class MecanicoController {

	@Autowired
	MecanicoRepositorio mecanicRepo;
	
	@PostMapping(path = "/autentica", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO autenticaUsuario(@RequestBody DatosIniciarMecanico datos, HttpServletRequest request) {
		DTO dto = new DTO();

		// Busca el usuario con ese nombre de usuario y password
		try {
			Mecanico mec = mecanicRepo.findByEmailAndPass(datos.email, datos.password);
			System.out.println("Email = " + datos.email);
			System.out.println("Pass = " + datos.password);
			if (mec != null) {
				Mecanico usuAutenticado = mec;
				dto.put("jwt", AutenticadorJWT.codificaJWT(usuAutenticado));
			}
		} catch (Exception e) {
			// RequestDispatcher rd=request.getRequestDispatcher("/usuario/listartodos");
			//dto.put("jwt", "Error");
		}
		// Devuelve el usuario autenticado si es encontrado
		dto.put("estado", "correcto");
		return dto;
	}
	
	static class DatosIniciarMecanico {
		@JsonProperty("email")
		String email;
		@JsonProperty("password")
		String password;
		
		public DatosIniciarMecanico(String email, String password) {
			super();
			this.email = email;
			this.password = password;
		}
	}
	
	@GetMapping("/datosautenticado")
	public DTO listarAutenticado(HttpServletRequest request) {
		DTO dtoUsuario = new DTO();
		System.out.println("El request => " + request);
		int idUsuarioAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
		System.out.println("Id de autenticado => " + idUsuarioAutenticado);
		Mecanico mecanico = mecanicRepo.findById(idUsuarioAutenticado);
		dtoUsuario.put("id", mecanico.getId());
		dtoUsuario.put("nombre", mecanico.getNombre());
		dtoUsuario.put("apellidos", mecanico.getApellidos());
		dtoUsuario.put("dni", mecanico.getDni());
		dtoUsuario.put("tlf", mecanico.getTlf());
		dtoUsuario.put("rol", mecanico.getRol());
		dtoUsuario.put("email", mecanico.getEmail());
		dtoUsuario.put("password", mecanico.getPassword());
		
		return dtoUsuario;
	}
	
	@GetMapping("/listarMecanicos")
	public DTO listarTodos(HttpServletRequest request) {
		// cramos dto que vamos a devolver
		DTO dto = new DTO();
		// asumimos que va a salir mal
		dto.put("estado", "error");
		// lista de dto que meteremos en dto que devolveremos
		List<DTO> dtoMecanicos = new ArrayList<DTO>();
		// buscamos todos los coches
		List<Mecanico> lu = mecanicRepo.findAllNoDespedidos();
		// recorremos y metemo en dto
		for (Mecanico u : lu) {
			DTO mecanico = new DTO();
			// creamos dto de objeto
			mecanico.put("id", u.getId());
			mecanico.put("nombre", u.getNombre());
			mecanico.put("apellidos", u.getApellidos());
			mecanico.put("dni", u.getDni());
			mecanico.put("tlf", u.getTlf());
			mecanico.put("rol", u.getRol());
			mecanico.put("email", u.getEmail());
			mecanico.put("password", u.getPassword());
			// metemos dto en lista dtos
			dtoMecanicos.add(mecanico);

		}

		dto.put("estado", "correcto");
		dto.put("listaMecanicos", dtoMecanicos);
		return dto;
	}
	
	/*@PostMapping("/buscarMecanicoId")
	public DTO buscarMecanicoId(@RequestBody IdMecanico id) {
		// cramos dto que vamos a devolver
		DTO dto = new DTO();
		// asumimos que va a salir mal
		dto.put("estado", "error");
		// lista de dto que meteremos en dto que devolveremos
		List<DTO> dtoMecanicos = new ArrayList<DTO>();
		// buscamos todos los coches
		Mecanico lu = mecanicRepo.findById(id.id);
		// recorremos y metemo en dto
			DTO mecanico = new DTO();
			// creamos dto de objeto
			mecanico.put("id", lu.getId());
			mecanico.put("nombre", lu.getNombre());
			mecanico.put("apellidos", lu.getApellidos());
			mecanico.put("dni", lu.getDni());
			mecanico.put("tlf", lu.getTlf());
			mecanico.put("rol", lu.getRol());
			mecanico.put("email", lu.getEmail());
			mecanico.put("password", lu.getPassword());
			// metemos dto en lista dtos
			dtoMecanicos.add(mecanico);

		dto.put("estado", "correcto");
		dto.put("listaMecanicos", dtoMecanicos);
		return dto;
	}*/
	
	@RequestMapping("/buscarMecanicoId/{id}")
	public DTO buscarMecanicoId(@PathVariable(value="id") int id) {
		DTO dto = new DTO();
		// asumimos que va a salir mal
		dto.put("estado", "error");
		// lista de dto que meteremos en dto que devolveremos
		List<DTO> dtoMecanicos = new ArrayList<DTO>();
		// buscamos todos los coches
		Mecanico lu = mecanicRepo.findById(id);
		// recorremos y metemo en dto
			DTO mecanico = new DTO();
			// creamos dto de objeto
			mecanico.put("id", lu.getId());
			mecanico.put("nombre", lu.getNombre());
			mecanico.put("apellidos", lu.getApellidos());
			mecanico.put("dni", lu.getDni());
			mecanico.put("tlf", lu.getTlf());
			mecanico.put("rol", lu.getRol());
			mecanico.put("email", lu.getEmail());
			mecanico.put("password", lu.getPassword());
			// metemos dto en lista dtos
			dtoMecanicos.add(mecanico);

		dto.put("estado", "correcto");
		dto.put("listaMecanicos", dtoMecanicos);
		return dto;
	}
	
	@PostMapping("/registroMecanico")
	public DTO registroMecanico(@RequestBody DatosRegistrarMecanico c) {
		
		DTO dto = new DTO();
		dto.put("estado", "error");
		
		Mecanico mecanico = new Mecanico();
		
		mecanico.setNombre(c.nombre);
		mecanico.setApellidos(c.apellidos);
		mecanico.setDni(c.dni);
		mecanico.setTlf(c.tlf);
		mecanico.setRol(c.rol);
		mecanico.setEmail(c.gmail);
		mecanico.setPassword(c.password);
		
		mecanicRepo.save(mecanico);
		
		dto.put("idCliente", mecanico.getId());
		dto.put("estado", "correcto");
		return dto;
	}

	static class DatosRegistrarMecanico {
		@JsonProperty("nombre")
		String nombre;
		@JsonProperty("apellidos")
		String apellidos;
		@JsonProperty("dni")
		String dni;
		@JsonProperty("tlf")
		String tlf;
		@JsonProperty("rol")
		String rol;
		@JsonProperty("gmail")
		String gmail;
		@JsonProperty("password")
		String password;
		
		public DatosRegistrarMecanico(String nombre, String apellidos, String dni, String tlf, String rol, String gmail, String password) {
			super();
			this.nombre = nombre;
			this.apellidos = apellidos;
			this.dni = dni;
			this.tlf = tlf;
			this.rol = rol;
			this.gmail = gmail;
			this.password = password;
		}
	}
	
	@PostMapping("/modificarMecanico")
	public DTO modificarMecanico(@RequestBody DatosModificarMecanico c) {
		
		DTO dto = new DTO();
		dto.put("estado", "error");
		
		Mecanico mecanico = mecanicRepo.findById(c.id);

		mecanico.setNombre(c.nombre);
		mecanico.setApellidos(c.apellidos);
		mecanico.setDni(c.dni);
		mecanico.setTlf(c.tlf);
		
		mecanicRepo.save(mecanico);
		
		dto.put("idCliente", mecanico.getId());
		dto.put("estado", "correcto");
		return dto;
	}
	
	static class DatosModificarMecanico {
		@JsonProperty("id")
		int id;
		@JsonProperty("nombre")
		String nombre;
		@JsonProperty("apellidos")
		String apellidos;
		@JsonProperty("dni")
		String dni;
		@JsonProperty("tlf")
		String tlf;
		
		public DatosModificarMecanico(int id, String nombre, String apellidos, String dni, String tlf) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.apellidos = apellidos;
			this.dni = dni;
			this.tlf = tlf;
		}
	}
	
	@RequestMapping("/despedirMecanico/{id}")
	public DTO despedirMecanico(@PathVariable(value="id") int id) {
		
		DTO dto = new DTO();
		dto.put("estado", "error");
		
		Mecanico mecanico = mecanicRepo.findById(id);

		mecanico.setDespedido(1);
		
		mecanicRepo.save(mecanico);
		
		dto.put("estado", "correcto");
		return dto;
	}
	
}
