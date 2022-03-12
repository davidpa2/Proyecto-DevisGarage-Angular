package com.example.demo.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.entities.Cliente;
import com.example.demo.model.entities.Coch;
import com.example.demo.model.entities.Mecanico;
import com.example.demo.repository.ClienteRepositorio;
import com.example.demo.repository.CocheRepositorio;
import com.example.demo.repository.MecanicoRepositorio;
import com.fasterxml.jackson.annotation.JsonProperty;

@CrossOrigin
@RestController
public class CocheController {

	@Autowired
	CocheRepositorio cjr;
	@Autowired
	ClienteRepositorio clientRepo;
	@Autowired
	MecanicoRepositorio mecanicRepo;

	@GetMapping("/listartodos")
	public DTO listarTodos(HttpServletRequest request) {
		// cramos dto que vamos a devolver
		DTO dto = new DTO();
		// asumimos que va a salir mal
		dto.put("estado", "error");
		// lista de dto que meteremos en dto que devolveremos
		List<DTO> dtoCoche = new ArrayList<DTO>();
		// buscamos todos los coches
		List<Coch> lu = cjr.findAll();
		// recorremos y metemo en dto
		for (Coch u : lu) {
			DTO coche = new DTO();
			// creamos dto de objeto
			coche.put("id", u.getId());
			coche.put("marca", u.getMarca());
			coche.put("modelo", u.getModelo());
			coche.put("km", u.getKm());
			coche.put("averias", u.getAverias());
			coche.put("descripcionAveria", u.getDescripcionAveria());
			coche.put("estado", u.getEstado());
			coche.put("fotoCoche", u.getFotoCoche());
			coche.put("fechaReparacion", u.getFechaReparacion());
			coche.put("costeReparacion", u.getCosteReparacion());
			coche.put("cliente", u.getCliente());
			coche.put("mecanico", u.getMecanico());
			// metemos dto en lista dtos
			dtoCoche.add(coche);

		}

		dto.put("estado", "correcto");
		dto.put("listaCoches", dtoCoche);
		return dto;
	}

	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	public DTO clientePorId(@ModelAttribute("id") int id) {

		DTO dto = new DTO();
		dto.put("estado", "error");

		Coch coche = cjr.findById(id);

		System.out.println(coche.getId());
		dto.put("coche", coche);
		dto.put("estado", "correcto");
		return dto;
	}

	@PostMapping("/registroCoche")
	public DTO registroCoche(@RequestBody DatosRegistrarCoche c) {

		DTO dto = new DTO();
		dto.put("estado", "error");

		Coch coche = new Coch();

		coche.setMarca(c.marca);
		coche.setModelo(c.modelo);
		coche.setKm(c.km);
		coche.setAverias(c.averias);
		coche.setDescripcionAveria(c.descripcionAveria);
		coche.setFotoCoche(c.fotoCoche);
		coche.setEstado(c.estado);
		coche.setCliente(c.cliente);
		coche.setMecanico(c.mecanico);

		cjr.save(coche);

		dto.put("estado", "error");
		return dto;
	}

	static class DatosRegistrarCoche {
		@JsonProperty("marca")
		String marca;
		@JsonProperty("modelo")
		String modelo;
		@JsonProperty("km")
		String km;
		@JsonProperty("averias")
		int averias;
		@JsonProperty("descripcionAveria")
		String descripcionAveria;
		@JsonProperty("fotoCoche")
		byte[] fotoCoche;
		@JsonProperty("estado")
		String estado;
		@JsonProperty("cliente")
		Cliente cliente;
		@JsonProperty("mecanico")
		Mecanico mecanico;

		public DatosRegistrarCoche(String marca, String modelo, String km, int averias, String descripcionAveria,
				byte[] fotoCoche, String estado, Cliente cliente, Mecanico mecanico) {
			super();
			this.marca = marca;
			this.modelo = modelo;
			this.km = km;
			this.averias = averias;
			this.descripcionAveria = descripcionAveria;
			this.fotoCoche = fotoCoche;
			this.estado = estado;
			this.cliente = cliente;
			this.mecanico = mecanico;
		}
	}

	@RequestMapping(value = "/actualizarEstadoCoche/{id}", method = RequestMethod.GET)
	@ResponseBody
	// actualizarEstadoCochepublic DTO actualizarEstadoCoche(HttpServletRequest
	// request) {
	public DTO actualizarEstadoCoche(@ModelAttribute("id") int id) {
		// cramos dto que vamos a devolver
		DTO dto = new DTO();
		// asumimos que va a salir mal
		dto.put("estado", "error");

		// buscar el coche por id para actualizarlo
		Coch coche = cjr.findById(id);

		if (coche.getEstado().equals("en cola")) {
			coche.setEstado("reparacion");

		} else if (coche.getEstado().equals("reparacion")) {
			coche.setEstado("completado");
			LocalDate fechaActual = LocalDate.now();
			coche.setFechaReparacion("" + fechaActual);
			dto.put("sii", "se ha completado");
		} else {
			dto.put("payaso", "vaya tela");
		}

		cjr.save(coche);

		// Si no ha habido ningún problema se puede establecer como correcto
		dto.put("estado", "correcto");
		dto.put("coche", coche.getEstado());
		return dto;
	}

	@RequestMapping(value = "/facturasMecanico/{id}", method = RequestMethod.GET)
	public DTO facturasMecanico(@PathVariable(value = "id") int id) {
		DTO dto = new DTO();
		// asumimos que va a salir mal
		dto.put("estado", "error");
		// lista de dto que meteremos en dto que devolveremos
		List<DTO> dtoCoche = new ArrayList<DTO>();
		// buscamos todos los coches
		List<Coch> lu = cjr.findAllFacturas(id);
		// recorremos y metemo en dto
		for (Coch u : lu) {
			DTO coche = new DTO();
			// creamos dto de objeto
			coche.put("id", u.getId());
			coche.put("marca", u.getMarca());
			coche.put("modelo", u.getModelo());
			coche.put("km", u.getKm());
			coche.put("averias", u.getAverias());
			coche.put("descripcionAveria", u.getDescripcionAveria());
			coche.put("estado", u.getEstado());
			coche.put("fotoCoche", u.getFotoCoche());
			coche.put("fechaReparacion", u.getFechaReparacion());
			coche.put("costeReparacion", u.getCosteReparacion());
			coche.put("cliente", u.getCliente());
			coche.put("mecanico", u.getMecanico());
			// metemos dto en lista dtos
			dtoCoche.add(coche);
		}
		dto.put("estado", "correcto");
		dto.put("listaFacturas", dtoCoche);
		return dto;
	}

	@GetMapping("/cochesEnCola")
	public DTO cochesEnCola(HttpServletRequest request) {
		// cramos dto que vamos a devolver
		DTO dto = new DTO();
		// asumimos que va a salir mal
		dto.put("estado", "error");
		// lista de dto que meteremos en dto que devolveremos
		List<DTO> dtoCoche = new ArrayList<DTO>();
		// buscamos todos los coches
		List<Coch> listaCoches = cjr.cochesEnCola();
		// recorremos y metemo en dto
		for (Coch c : listaCoches) {
			DTO coche = new DTO();
			// creamos dto de objeto
			coche.put("id", c.getId());
			coche.put("marca", c.getMarca());
			coche.put("modelo", c.getModelo());
			coche.put("km", c.getKm());
			coche.put("averias", c.getAverias());
			coche.put("descripcionAveria", c.getDescripcionAveria());
			coche.put("estado", c.getEstado());
			coche.put("fotoCoche", c.getFotoCoche());
			coche.put("cliente", c.getCliente());
			coche.put("mecanico", c.getMecanico());
			// metemos dto en lista dtos
			dtoCoche.add(coche);
		}

		dto.put("estado", "correcto");
		dto.put("listaCochesEnCola", dtoCoche);
		return dto;
	}

	@GetMapping("/cochesEnReparacion")
	public DTO cochesEnReparacion(HttpServletRequest request) {
		// cramos dto que vamos a devolver
		DTO dto = new DTO();
		// asumimos que va a salir mal
		dto.put("estado", "error");
		// lista de dto que meteremos en dto que devolveremos
		List<DTO> dtoCoche = new ArrayList<DTO>();
		// buscamos todos los coches
		List<Coch> listaCoches = cjr.cochesEnReparacion();
		// recorremos y metemo en dto
		for (Coch c : listaCoches) {
			DTO coche = new DTO();
			// creamos dto de objeto
			coche.put("id", c.getId());
			coche.put("marca", c.getMarca());
			coche.put("modelo", c.getModelo());
			coche.put("km", c.getKm());
			coche.put("averias", c.getAverias());
			coche.put("descripcionAveria", c.getDescripcionAveria());
			coche.put("estado", c.getEstado());
			coche.put("fotoCoche", c.getFotoCoche());
			coche.put("cliente", c.getCliente());
			coche.put("mecanico", c.getMecanico());
			// metemos dto en lista dtos
			dtoCoche.add(coche);
		}

		dto.put("estado", "correcto");
		dto.put("listaCochesEnReparacion", dtoCoche);
		return dto;
	}

	@GetMapping("/cochesCompletados")
	public DTO cochesCompletados(HttpServletRequest request) {
		// cramos dto que vamos a devolver
		DTO dto = new DTO();
		// asumimos que va a salir mal
		dto.put("estado", "error");
		// lista de dto que meteremos en dto que devolveremos
		List<DTO> dtoCoche = new ArrayList<DTO>();
		// buscamos todos los coches
		List<Coch> listaCoches = cjr.cochesCompletados();
		// recorremos y metemo en dto
		for (Coch c : listaCoches) {
			DTO coche = new DTO();
			// creamos dto de objeto
			coche.put("id", c.getId());
			coche.put("marca", c.getMarca());
			coche.put("modelo", c.getModelo());
			coche.put("km", c.getKm());
			coche.put("averias", c.getAverias());
			coche.put("descripcionAveria", c.getDescripcionAveria());
			coche.put("estado", c.getEstado());
			coche.put("fotoCoche", c.getFotoCoche());
			coche.put("cliente", c.getCliente());
			coche.put("mecanico", c.getMecanico());
			// metemos dto en lista dtos
			dtoCoche.add(coche);
		}

		dto.put("estado", "correcto");
		dto.put("listaCochesCompletados", dtoCoche);
		return dto;
	}

	@RequestMapping(value = "/actualizarCosteReparacion/{id}/{coste}", method = RequestMethod.GET)
	public DTO actualizarCosteReparacion(@PathVariable(value = "id") int id, @PathVariable(value = "coste") String coste) {
		DTO dto = new DTO();
		// asumimos que va a salir mal
		dto.put("estado", "error");
		// buscar el coche para poder actualizar su coste
		Coch coche = cjr.findById(id);

		coche.setCosteReparacion(Integer.parseInt(coste));
		
		cjr.save(coche);

		dto.put("estado", "correcto");
		return dto;
	}

	/*
	 * @GetMapping("/listartodos") public List<DTO> listarTodos(HttpServletRequest
	 * request) { List<DTO> listaCochesDTO = new ArrayList<DTO>(); List<Coch> lu
	 * =cjr.findAll(); for (Coch u : lu) { DTO dtoCoche=new DTO();
	 * dtoCoche.put("id",u.getId()); dtoCoche.put("marca", u.getMarca());
	 * dtoCoche.put("modelo", u.getModelo()); dtoCoche.put("km", u.getKm());
	 * dtoCoche.put("averias", u.getAverias()); dtoCoche.put("descripcionAveria",
	 * u.getDescripcionAveria()); dtoCoche.put("estado", u.getEstado());
	 * dtoCoche.put("fotoCoche", u.getFotoCoche()); dtoCoche.put("cliente",
	 * u.getCliente()); dtoCoche.put("mecanico", u.getMecanico());
	 * listaCochesDTO.add(dtoCoche); } return listaCochesDTO; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @GetMapping("/anadir") public void anadir() {
	 * 
	 * cjr.save(new Usuario
	 * (null,"Christian","passw77","admin",null,Calendar.getInstance().getTime()));
	 * 
	 * }
	 * 
	 * @GetMapping("/actualizar") public void actualizar() { Usuario u=new
	 * Usuario(6,"pueblo","nue","user",null,Calendar.getInstance().getTime());
	 * cjr.save(u);
	 * 
	 * }
	 * 
	 * @GetMapping("/borrar") public int removeCurso() { cjr.deleteById(1); return
	 * 0; }
	 * 
	 * @PostMapping("/borrarpost") public int
	 * removeCursoPorid(@ModelAttribute("identi") DatosBajaUsuario u) {
	 * cjr.deleteById(u.id); return 0; }
	 * 
	 * @PostMapping("/autentica") public DTO
	 * autenticaUsuario(@ModelAttribute("persona") DatosAutenticacionUsuario u) {
	 * DTO dto = new DTO(); dto.put("usuario", u.usuario); dto.put("password",
	 * u.password); List<DTO> lista=new ArrayList<DTO>();
	 * 
	 * return dto;}
	 * 
	 * 
	 * @PostMapping(path="/anadirnuevo",consumes=MediaType.APPLICATION_JSON_VALUE)
	 * public void autenticaUsuario(@RequestBody DatosAltaUsuario
	 * u,HttpServletRequest request) {
	 * 
	 * cjr.save(new Usuario
	 * (null,u.usuario,u.password,u.rol,Base64.decodeBase64((String)
	 * u.imagen),u.fecha));
	 * 
	 * }
	 * 
	 * 
	 * 
	 * class DatosAutenticacionUsuario{ String usuario; String password;
	 * 
	 * public DatosAutenticacionUsuario(String usuario, String password) { super();
	 * this.usuario = usuario; this.password = password; } }
	 * 
	 * 
	 * static class DatosAltaUsuario{ String usuario; String password; String rol;
	 * String imagen; Date fecha;
	 * 
	 * 
	 * public DatosAltaUsuario(String usuario, String password, String rol, String
	 * imagen, Date fecha) { super(); this.usuario = usuario; this.password =
	 * password; this.rol=rol; this.imagen=imagen; this.fecha=fecha; } } static
	 * class DatosBajaUsuario{ int id;
	 * 
	 * 
	 * public DatosBajaUsuario(int id) { super(); this.id=id; } } static class
	 * DatosActualizaUsuario{ int id; String usuario; String password; String rol;
	 * 
	 * 
	 * public DatosActualizaUsuario(int id,String usuario, String password, String
	 * rol) { super(); this.id=id; this.usuario = usuario; this.password = password;
	 * this.rol=rol; } }
	 */

}
