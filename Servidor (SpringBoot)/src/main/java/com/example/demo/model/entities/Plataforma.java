package com.example.demo.model.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the plataforma database table.
 * 
 */
@Entity
@NamedQuery(name="Plataforma.findAll", query="SELECT p FROM Plataforma p")
public class Plataforma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private byte ocupado;

	//bi-directional one-to-one association to Coch
	@OneToOne
	@JoinColumn(name="id")
	@JsonIgnore
	private Coch coch;

	public Plataforma() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getOcupado() {
		return this.ocupado;
	}

	public void setOcupado(byte ocupado) {
		this.ocupado = ocupado;
	}

	public Coch getCoch() {
		return this.coch;
	}

	public void setCoch(Coch coch) {
		this.coch = coch;
	}

}