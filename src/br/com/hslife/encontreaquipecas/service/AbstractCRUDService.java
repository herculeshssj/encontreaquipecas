package br.com.hslife.encontreaquipecas.service;

import br.com.hslife.encontreaquipecas.entity.EntityPersistence;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.repository.AbstractCRUDRepository;

public abstract class AbstractCRUDService<E extends EntityPersistence> {
	
	protected abstract AbstractCRUDRepository<E> getRepository();
	
	public void cadastrar(E entity) throws BusinessException {
		getRepository().save(entity);		
	}

	public void alterar(E entity) throws BusinessException {
		getRepository().update(entity);		
	}

	public void excluir(E entity) throws BusinessException {
		getRepository().delete(entity);		
	}

	public E buscarPorID(Long id) throws BusinessException {
		return getRepository().findById(id);
	}
}
