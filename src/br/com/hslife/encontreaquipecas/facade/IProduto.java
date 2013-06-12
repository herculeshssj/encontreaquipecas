package br.com.hslife.encontreaquipecas.facade;

import java.util.List;

import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.entity.Produto;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.model.CriterioProduto;
import br.com.hslife.encontreaquipecas.service.ICRUDService;

public interface IProduto extends ICRUDService<Produto> {
	
	public List<Produto> buscarPorNome(String nomeProduto) throws BusinessException;

	public List<Produto> buscarPorNomeELoja(String nomeProduto, Loja loja) throws BusinessException;
	
	public List<String> buscarFabricantes() throws BusinessException;
	
	public List<String> buscarModelos() throws BusinessException;
	
	public List<String> buscarAnos() throws BusinessException;
	
	public List<Produto> buscarPorCriterioProduto(CriterioProduto criterio) throws BusinessException;
	
}
