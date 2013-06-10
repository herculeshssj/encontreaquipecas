package br.com.hslife.encontreaquipecas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.entity.Produto;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IProduto;
import br.com.hslife.encontreaquipecas.repository.ProdutoRepository;

@Service("produtoService")
public class ProdutoService extends AbstractCRUDService<Produto> implements IProduto {

	@Autowired
	private ProdutoRepository repository;

	public ProdutoRepository getRepository() {
		return repository;
	}

	public void setRepository(ProdutoRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void validar(Produto entity) throws BusinessException {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public List<Produto> buscarPorNome(String nomeProduto) throws BusinessException {
		return getRepository().findByNome(nomeProduto);
	}
	
	@Override
	public List<Produto> buscarPorNomeELoja(String nomeProduto, Loja loja) throws BusinessException {
		return getRepository().findByNomeAndLoja(nomeProduto, loja);
	}
}
