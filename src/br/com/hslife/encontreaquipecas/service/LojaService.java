package br.com.hslife.encontreaquipecas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.ILoja;
import br.com.hslife.encontreaquipecas.repository.LojaRepository;

@Service("lojaService")
public class LojaService extends AbstractCRUDService<Loja> implements ILoja {

	@Autowired
	private LojaRepository repository;
	
	public LojaRepository getRepository() {
		return repository;
	}

	public void setRepository(LojaRepository repository) {
		this.repository = repository;
	}

	@Override
	public void validar(Loja entity) throws BusinessException {
		// TODO Auto-generated method stub		
	}

	@Override
	public List<Loja> buscarTodos() throws BusinessException {
		return getRepository().findAll();
	}

	@Override
	public Loja buscarPorLogin(String login) throws BusinessException {
		return getRepository().findLojaByLogin(login);
	}
	
	
}
