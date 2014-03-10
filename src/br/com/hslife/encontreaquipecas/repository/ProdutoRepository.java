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

package br.com.hslife.encontreaquipecas.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.entity.Produto;
import br.com.hslife.encontreaquipecas.model.CriterioProduto;

@Repository
public class ProdutoRepository extends AbstractCRUDRepository<Produto> {

	public ProdutoRepository() {
		super(new Produto());
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> findByNome(String nomeProduto) {
		Criteria criteria = getSession().createCriteria(Produto.class);
		criteria.add(Restrictions.ilike("nome", nomeProduto, MatchMode.ANYWHERE));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> findByNomeAndLoja(String nomeProduto, Loja loja) {
		Criteria criteria = getSession().createCriteria(Produto.class);
		criteria.add(Restrictions.ilike("nome", nomeProduto, MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("loja.id", loja.getId()));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> findByCriterioProduto(CriterioProduto criterio) {
		Criteria criteria = getSession().createCriteria(Produto.class);
		if (criterio.getNome() != null && !criterio.getNome().trim().isEmpty()) {
			criteria.add(Restrictions.eq("nome", criterio.getNome()));
		}
		if (criterio.getFabricante() != null && !criterio.getFabricante().trim().isEmpty()) {
			criteria.add(Restrictions.eq("fabricante", criterio.getFabricante()));
		}
		if (criterio.getModelo() != null && !criterio.getModelo().trim().isEmpty()) {
			criteria.add(Restrictions.eq("modelo", criterio.getModelo()));
		}
		if (criterio.getAno() != null && !criterio.getAno().trim().isEmpty()) {
			criteria.add(Restrictions.eq("ano", Integer.parseInt(criterio.getAno())));
		}
		return criteria.addOrder(Order.asc("preco")).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findFabricantes() {
		return getSession().createSQLQuery("select distinct fabricante from produto order by fabricante asc").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findModelos() {
		return getSession().createSQLQuery("select distinct modelo from produto order by modelo asc").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findAnos() {
		return getSession().createSQLQuery("select distinct ano from produto order by ano asc").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findNomes() {
		return getSession().createSQLQuery("select distinct nome from produto order by nome asc").list();
	}
}