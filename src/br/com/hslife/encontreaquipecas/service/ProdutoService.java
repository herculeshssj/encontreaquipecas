/***
  
  	Copyright (c) 2013 Hércules S. S. José

    Este arquivo é parte do programa EncontreAquiPeças.
    

    EncontreAquiPeças é um software livre; você pode redistribui-lo e/ou 

    modificá-lo dentro dos termos da Licença Pública Geral Menor GNU como 

    publicada pela Fundação do Software Livre (FSF); na versão 2.1 da 

    Licença.
    

    Este programa é distribuído na esperança que possa ser útil, 

    mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÂO a 
    
    qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública 
    
    Geral Menor GNU em português para maiores detalhes.
    

    Você deve ter recebido uma cópia da Licença Pública Geral Menor GNU sob o 

    nome de "LICENSE.TXT" junto com este programa, se não, acesse o site HSlife
    
    no endereco www.hslife.com.br ou escreva para a Fundação do Software 
    
    Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301, USA.
    

    Para mais informações sobre o programa EncontreAquiPeças e seu autor acesse o 

    endereço www.hslife.com.br, pelo e-mail contato@hslife.com.br ou escreva para 

    Hércules S. S. José, Av. Ministro Lafaeyte de Andrade, 1683 - Bl. 3 Apt 404, 

    Marco II - Nova Iguaçu, RJ, Brasil.
  
*/

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