package com.example.demo.model.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the coches database table.
 * 
 */
@Entity
@Table(name="coches")
@NamedQuery(name="Coch.findAll", query="SELECT c FROM Coch c")
@NamedQuery(name="Coch.cochesEnCola", query = "select c from Coch c where estado = 'en cola'")
@NamedQuery(name="Coch.cochesEnReparacion", query = "select c from Coch c where estado = 'reparacion'")
@NamedQuery(name="Coch.cochesCompletados", query = "select c from Coch c where estado = 'completado' and costeReparacion = 0")
@NamedQuery(name="Coch.findAllFacturas", query="SELECT c FROM Coch c where mecanico_id = ?1 and costeReparacion > 0 order by fechaReparacion desc")
public class Coch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private int averias;

	private String descripcionAveria;

	private String estado;

	private byte[] fotoCoche;

	private String km;

	private String marca;

	private String modelo;
	
	private String fechaReparacion;
	
	private int costeReparacion;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	private Cliente cliente;

	//bi-directional many-to-one association to Mecanico
	@ManyToOne
	private Mecanico mecanico;

	//bi-directional one-to-one association to Plataforma
	@OneToOne(mappedBy="coch")
	@JsonIgnore
	private Plataforma plataforma;

	public Coch() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAverias() {
		return this.averias;
	}

	public void setAverias(int averias) {
		this.averias = averias;
	}

	public String getDescripcionAveria() {
		return this.descripcionAveria;
	}

	public void setDescripcionAveria(String descripcionAveria) {
		this.descripcionAveria = descripcionAveria;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public byte[] getFotoCoche() {
		return this.fotoCoche;
	}

	public void setFotoCoche(byte[] fotoCoche) {
		this.fotoCoche = fotoCoche;
	}

	public String getKm() {
		return this.km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public String getFechaReparacion() {
		return this.fechaReparacion;
	}

	public void setFechaReparacion(String fechaReparacion) {
		this.fechaReparacion = fechaReparacion;
	}

	public int getCosteReparacion() {
		return costeReparacion;
	}

	public void setCosteReparacion(int costeReparacion) {
		this.costeReparacion = costeReparacion;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Mecanico getMecanico() {
		return this.mecanico;
	}

	public void setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	public Plataforma getPlataforma() {
		return this.plataforma;
	}

	public void setPlataforma(Plataforma plataforma) {
		this.plataforma = plataforma;
	}

}