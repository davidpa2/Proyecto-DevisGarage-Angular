package com.example.demo.repository;
import java.io.Serializable;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.entities.Mecanico;

@Repository("mecanicoJpaRepositorio")
public interface MecanicoRepositorio extends JpaRepository<Mecanico,Serializable> {
	public abstract List<Mecanico> findAll();
	public abstract List<Mecanico> findAllNoDespedidos();
	public abstract Mecanico findById(int id);
	@Transactional
	public abstract void deleteById(int id);
	@Transactional
	public abstract Mecanico save(Mecanico u);
	
	public abstract Mecanico findByEmailAndPass(String gmail, String password);
}
