package br.com.hslife.encontreaquipecas.facade;

import java.util.List;

import br.com.hslife.encontreaquipecas.entity.Banner;
import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.service.ICRUDService;

public interface IBanner extends ICRUDService<Banner> {
	
	public Banner buscarPorLoja(Loja loja) throws BusinessException;
	
	public List<Banner> buscarTodos() throws BusinessException;
	
}
