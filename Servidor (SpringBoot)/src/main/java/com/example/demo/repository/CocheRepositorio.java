package com.example.demo.repository;
import java.io.Serializable;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entities.Coch;
import com.example.demo.model.entities.Mecanico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


@Repository("cocheJpaRepositorio")
public interface CocheRepositorio extends JpaRepository<Coch,Serializable>{
	public abstract List<Coch> findAll();
	public abstract Coch findById(int id);
	public abstract List<Coch> cochesEnCola();
	public abstract List<Coch> cochesEnReparacion();
	public abstract List<Coch> cochesCompletados();
	@Transactional
	public abstract void deleteById(int id);
	@Transactional
	public abstract Coch  save(Coch u);
	
	public abstract List<Coch> findAllFacturas(int idMecanico);
	
}
