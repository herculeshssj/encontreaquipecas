package br.com.hslife.encontreaquipecas.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.hslife.encontreaquipecas.entity.Consumidor;
import br.com.hslife.encontreaquipecas.entity.HistoricoPesquisa;

@Repository
public class HistoricoPesquisaRepository extends AbstractCRUDRepository<HistoricoPesquisa> {

	public HistoricoPesquisaRepository() {
		super(new HistoricoPesquisa());
	}
	
	@SuppressWarnings("unchecked")
	public List<HistoricoPesquisa> findByConsumidor(Consumidor consumidor) {
		Criteria criteria = getSession().createCriteria(HistoricoPesquisa.class);
		criteria.add(Restrictions.eq("consumidor.id", consumidor.getId()));
		return criteria.list();
	}
	
	public Consumidor findLojaByLogin(String loginUsuario) {
		String sql = "select c.* from consumidor c inner join usuario u on u.id = c.idUsuario where u.login = '" + loginUsuario +"'";		
		Query query = getSession().createSQLQuery(sql).addEntity(Consumidor.class);		
		return (Consumidor)query.uniqueResult();
	}
}