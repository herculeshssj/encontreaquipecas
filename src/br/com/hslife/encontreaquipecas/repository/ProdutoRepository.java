package br.com.hslife.encontreaquipecas.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.entity.Produto;

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
}
