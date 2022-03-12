package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.entities.Cliente;
import com.example.demo.model.entities.Mecanico;
import com.example.demo.repository.ClienteRepositorio;
import com.fasterxml.jackson.annotation.JsonProperty;

@CrossOrigin
@RestController
public class ClienteController {

	@Autowired
	ClienteRepositorio clientRepo;
	
	@GetMapping("/listarClientes")
	public DTO listarTodos(HttpServletRequest request) {
		// cramos dto que vamos a devolver
		DTO dto = new DTO();
		// asumimos que va a salir mal
		dto.put("estado", "error");
		// lista de dto que meteremos en dto que devolveremos
		List<DTO> dtoCliente = new ArrayList<DTO>();
		// buscamos todos los coches
		List<Cliente> cl = clientRepo.findAll();
		// recorremos y metemo en dto
		for (Cliente u : cl) {
			DTO cliente = new DTO();
			// creamos dto de objeto
			cliente.put("id", u.getId());
			cliente.put("nombre", u.getNombre());
			cliente.put("apellidos", u.getApellidos());
			cliente.put("dni", u.getDni());
			cliente.put("tlf", u.getTlf());
			cliente.put("gmail", u.getGmail());
			// metemos dto en lista dtos
			dtoCliente.add(cliente);
		}

		dto.put("estado", "correcto");
		dto.put("listaClientes", dtoCliente);
		return dto;
	}
	
	@PostMapping("/registroCliente")
	public DTO registroCoche(@RequestBody DatosRegistrarCliente c) {
		
		DTO dto = new DTO();
		dto.put("estado", "error");
		
		Cliente cliente = new Cliente();
		
		cliente.setNombre(c.nombre);
		cliente.setApellidos(c.apellidos);
		cliente.setDni(c.dni);
		cliente.setTlf(c.tlf);
		cliente.setGmail(c.gmail);
		
		clientRepo.save(cliente);
		
		dto.put("idCliente", cliente.getId());
		dto.put("estado", "correcto");
		return dto;
	}

	static class DatosRegistrarCliente {
		@JsonProperty("nombre")
		String nombre;
		@JsonProperty("apellidos")
		String apellidos;
		@JsonProperty("dni")
		String dni;
		@JsonProperty("tlf")
		String tlf;
		@JsonProperty("gmail")
		String gmail;
		
		public DatosRegistrarCliente(String nombre, String apellidos, String dni, String tlf, String gmail) {
			super();
			this.nombre = nombre;
			this.apellidos = apellidos;
			this.dni = dni;
			this.tlf = tlf;
			this.gmail = gmail;
		}
	}
	
	@RequestMapping("/clientePorId/{id}")
	public DTO clientePorId(@PathVariable(value="id") int id) {
		
		DTO dto = new DTO();
		dto.put("estado", "error");
		
		Cliente cliente = clientRepo.findById(id);
		
		dto.put("cliente", cliente);
		dto.put("estado", "correcto");
		return dto;
	}
}
