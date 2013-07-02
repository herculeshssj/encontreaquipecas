package br.com.hslife.encontreaquipecas.facade;

import java.util.List;

import br.com.hslife.encontreaquipecas.entity.Consumidor;
import br.com.hslife.encontreaquipecas.entity.HistoricoPesquisa;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.service.ICRUDService;

public interface IHistoricoPesquisa extends ICRUDService<HistoricoPesquisa> {

	public List<HistoricoPesquisa> buscarPorConsumidor(Consumidor consumidor) throws BusinessException;
	
	public Consumidor buscarPorLogin(String login) throws BusinessException;
	
}
