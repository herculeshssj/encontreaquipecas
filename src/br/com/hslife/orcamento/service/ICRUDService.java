package br.com.hslife.orcamento.service;

import br.com.hslife.orcamento.entity.EntityPersistence;
import br.com.hslife.orcamento.exception.BusinessException;

public interface ICRUDService<E extends EntityPersistence> {
	
	public void cadastrar(E entity) throws BusinessException;
	
	public void alterar(E entity) throws BusinessException;
	
	public void excluir(E entity) throws BusinessException;
	
	public E buscarPorID(Long id) throws BusinessException;
	
	public void validar(E entity) throws BusinessException;

}