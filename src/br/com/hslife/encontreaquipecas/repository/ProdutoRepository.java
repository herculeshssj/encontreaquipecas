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