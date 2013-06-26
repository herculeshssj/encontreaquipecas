package br.com.hslife.encontreaquipecas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.entity.Produto;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IProduto;
import br.com.hslife.encontreaquipecas.model.CriterioProduto;
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
	
	@Override
	public List<String> buscarFabricantes() throws BusinessException {
		return getRepository().findFabricantes();
	}
	
	@Override
	public List<String> buscarModelos() throws BusinessException {
		return getRepository().findModelos();
	}
	
	@Override
	public List<String> buscarAnos() throws BusinessException {
		return getRepository().findAnos();
	}
	
	public List<String> buscarNomes() throws BusinessException {
		return getRepository().findNomes();
	}
	
	@Override
	public List<Produto> buscarPorCriterioProduto(CriterioProduto criterio) throws BusinessException {
		return getRepository().findByCriterioProduto(criterio);
	}
}