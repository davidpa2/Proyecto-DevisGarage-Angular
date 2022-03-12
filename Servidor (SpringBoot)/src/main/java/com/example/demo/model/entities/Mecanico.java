package com.example.demo.model.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the mecanicos database table.
 * 
 */
@Entity
@Table(name="mecanicos")
@NamedQuery(name="Mecanico.findAll", query="SELECT m FROM Mecanico m")
@NamedQuery(name="Mecanico.findAllNoDespedidos", query="SELECT m FROM Mecanico m where despedido = 0")
@NamedQuery(name="Mecanico.findByEmailAndPass", query = "select m from Mecanico m where email = ?1 and password = ?2")
public class Mecanico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String apellidos;

	private String dni;

	private String nombre;

	private String rol;

	private String tlf;
	
	private String email;
	
	private String password;
	
	private int despedido;

	//bi-directional many-to-one association to Coch
	@OneToMany(mappedBy="mecanico")
	@JsonIgnore
	private List<Coch> coches;

	public Mecanico() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getTlf() {
		return this.tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getDespedido() {
		return despedido;
	}

	public void setDespedido(int despedido) {
		this.despedido = despedido;
	}

	public List<Coch> getCoches() {
		return this.coches;
	}

	public void setCoches(List<Coch> coches) {
		this.coches = coches;
	}

	public Coch addCoch(Coch coch) {
		getCoches().add(coch);
		coch.setMecanico(this);

		return coch;
	}

	public Coch removeCoch(Coch coch) {
		getCoches().remove(coch);
		coch.setMecanico(null);

		return coch;
	}

}