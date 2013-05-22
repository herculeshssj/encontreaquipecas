package br.com.hslife.encontreaquipecas.repository;

import br.com.hslife.encontreaquipecas.entity.EntityPersistence;

public interface IRepository<E extends EntityPersistence> {

	public void save(final E entity);
	
	public void update(final E entity);
	
	public void delete(final E entity);
	
	public E findById(final Long id);
	
}
