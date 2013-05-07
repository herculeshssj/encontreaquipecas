package br.com.hslife.orcamento.repository;

import br.com.hslife.orcamento.entity.EntityPersistence;

public interface IRepository<E extends EntityPersistence> {

	public void save(final E entity);
	
	public void update(final E entity);
	
	public void delete(final E entity);
	
	public E findById(final Long id);
	
}
