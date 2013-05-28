package br.com.hslife.encontreaquipecas.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.hslife.encontreaquipecas.entity.Consumidor;

@Repository
@Transactional
public class ConsumidorRepository extends AbstractCRUDRepository<Consumidor> {
	
	public ConsumidorRepository() {
		super(new Consumidor());
	}
}