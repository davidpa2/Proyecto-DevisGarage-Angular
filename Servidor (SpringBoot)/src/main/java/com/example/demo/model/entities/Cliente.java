package com.example.demo.model.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String apellidos;

	private String dni;

	private String gmail;

	private String nombre;

	private String tlf;

	//bi-directional many-to-one association to Coch
	@OneToMany(mappedBy="cliente")
	@JsonIgnore
	private List<Coch> coches;

	public Cliente() {
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

	public String getGmail() {
		return this.gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTlf() {
		return this.tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public List<Coch> getCoches() {
		return this.coches;
	}

	public void setCoches(List<Coch> coches) {
		this.coches = coches;
	}

	public Coch addCoch(Coch coch) {
		getCoches().add(coch);
		coch.setCliente(this);

		return coch;
	}

	public Coch removeCoch(Coch coch) {
		getCoches().remove(coch);
		coch.setCliente(null);

		return coch;
	}

}