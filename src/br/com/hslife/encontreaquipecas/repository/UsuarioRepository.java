package br.com.hslife.encontreaquipecas.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.hslife.encontreaquipecas.entity.Usuario;

@Repository
@Transactional
public class UsuarioRepository extends AbstractCRUDRepository<Usuario> {
	
	public UsuarioRepository() {
		super(new Usuario());
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findAll() {
		return getSession().createCriteria(Usuario.class).addOrder(Order.asc("nome")).list();
	}
	
	public Usuario findByLogin(String login) {
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("login", login));
		return (Usuario)criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> findAllByLogin(String login) {
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.ilike("login", login, MatchMode.ANYWHERE));
		return criteria.addOrder(Order.asc("nome")).list();
	}
}