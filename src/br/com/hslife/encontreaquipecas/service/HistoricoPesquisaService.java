package br.com.hslife.encontreaquipecas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hslife.encontreaquipecas.entity.Consumidor;
import br.com.hslife.encontreaquipecas.entity.HistoricoPesquisa;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IHistoricoPesquisa;
import br.com.hslife.encontreaquipecas.repository.HistoricoPesquisaRepository;

@Service("historicoPesquisaService")
public class HistoricoPesquisaService extends AbstractCRUDService<HistoricoPesquisa> implements IHistoricoPesquisa {

	@Autowired
	private HistoricoPesquisaRepository repository;

	
	public HistoricoPesquisaRepository getRepository() {
		return repository;
	}



	public void setRepository(HistoricoPesquisaRepository repository) {
		this.repository = repository;
	}



	@Override
	public void validar(HistoricoPesquisa entity) throws BusinessException {
		// TODO Auto-generated method stub		
	}



	@Override
	public List<HistoricoPesquisa> buscarPorConsumidor(Consumidor consumidor) throws BusinessException {
		return getRepository().findByConsumidor(consumidor);
	}



	@Override
	public Consumidor buscarPorLogin(String login) throws BusinessException {
		return getRepository().findLojaByLogin(login);
	}
}