package br.com.hslife.encontreaquipecas.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.hslife.encontreaquipecas.entity.Auditoria;
import br.com.hslife.encontreaquipecas.model.CriterioAuditoria;

@Repository
public class AuditoriaRepository extends AbstractRepository {
	
	public void delete(Auditoria entity) {
		getSession().delete(entity);
	}
	
	public Auditoria findById(Long id) {
		Criteria criteria = getSession().createCriteria(Auditoria.class).setFetchMode("dadosAuditoria", FetchMode.JOIN);
		criteria.add(Restrictions.eq("id", id));
		return (Auditoria)criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Auditoria> findByCriteriosAuditoria(CriterioAuditoria criterio) {
		Criteria criteria = getSession().createCriteria(Auditoria.class);
		
		if (criterio.getUsuario() != null && !criterio.getUsuario().isEmpty()) {
			criteria.add(Restrictions.eq("usuario", criterio.getUsuario()));
		}
		
		if (criterio.getClasse() != null && !criterio.getClasse().isEmpty()) {
			criteria.add(Restrictions.eq("classe", criterio.getClasse()));
		}
		
		if (criterio.getTransacao() != null && !criterio.getTransacao().isEmpty()) {
			criteria.add(Restrictions.eq("transacao", criterio.getTransacao()));
		}
		
		if (criterio.getInicio() != null) {
			criteria.add(Restrictions.ge("data", criterio.getInicio()));
		}
		
		if (criterio.getFim() != null) {
			criteria.add(Restrictions.le("data", criterio.getFim()));
		}
		
		return criteria.addOrder(Order.asc("dataHora")).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findClasses() {
		return getSession().createSQLQuery("select distinct classe from auditoria order by classe asc").list();
	}
}