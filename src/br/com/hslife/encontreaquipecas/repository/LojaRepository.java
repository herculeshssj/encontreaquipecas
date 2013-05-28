package br.com.hslife.encontreaquipecas.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.hslife.encontreaquipecas.entity.Loja;

@Repository
@Transactional
public class LojaRepository extends AbstractCRUDRepository<Loja> {
	
	public LojaRepository() {
		super(new Loja());
	}
}