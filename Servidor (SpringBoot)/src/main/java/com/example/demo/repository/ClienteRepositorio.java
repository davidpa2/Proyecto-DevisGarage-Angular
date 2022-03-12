package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entities.Cliente;

@Repository("clienteJpaRepositorio")
public interface ClienteRepositorio extends JpaRepository<Cliente,Serializable>{
	public abstract List<Cliente> findAll();
	public abstract Cliente findById(int id);
	@Transactional
	public abstract void deleteById(int id);
	@Transactional
	public abstract Cliente save(Cliente u);
}
