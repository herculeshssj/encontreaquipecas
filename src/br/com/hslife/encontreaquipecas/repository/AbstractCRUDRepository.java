package br.com.hslife.encontreaquipecas.repository;

import org.springframework.transaction.annotation.Transactional;

import br.com.hslife.encontreaquipecas.entity.EntityPersistence;

@Transactional
public abstract class AbstractCRUDRepository<E extends EntityPersistence> extends AbstractRepository implements IRepository<E>{
	
	private E entity;
	
	public AbstractCRUDRepository(E entity) {
		this.entity = entity;
	}
	
	public void save(E entity) {
		getSession().persist(entity);
	}
	
	public void update(E entity) {
		getSession().merge(entity);
	}
	
	public void delete(E entity) {
		getSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	public E findById(Long id) {
		return (E)getSession().get(entity.getClass(), id);
	}
}