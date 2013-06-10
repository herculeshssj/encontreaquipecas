package br.com.hslife.encontreaquipecas.facade;

import java.util.List;

import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.service.ICRUDService;

public interface ILoja extends ICRUDService<Loja> {

	public List<Loja> buscarTodos() throws BusinessException;
	
	public Loja buscarPorLogin(String login) throws BusinessException;
	
}
