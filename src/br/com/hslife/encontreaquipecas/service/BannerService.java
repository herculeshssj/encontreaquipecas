package br.com.hslife.encontreaquipecas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hslife.encontreaquipecas.entity.Banner;
import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IBanner;
import br.com.hslife.encontreaquipecas.repository.BannerRepository;

@Service("bannerService")
public class BannerService extends AbstractCRUDService<Banner> implements IBanner {
	
	@Autowired
	private BannerRepository repository;

	public BannerRepository getRepository() {
		return repository;
	}

	public void setRepository(BannerRepository repository) {
		this.repository = repository;
	}

	@Override
	public void validar(Banner entity) throws BusinessException {
		
		
	}

	@Override
	public Banner buscarPorLoja(Loja loja) throws BusinessException {
		return getRepository().findByLoja(loja);
	}

	public List<Banner> buscarTodos() throws BusinessException {
		return getRepository().findAll();
	}
}