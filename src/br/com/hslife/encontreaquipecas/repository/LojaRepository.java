package br.com.hslife.encontreaquipecas.repository;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.hslife.encontreaquipecas.entity.Loja;

@Repository
@Transactional
public class LojaRepository extends AbstractCRUDRepository<Loja> {
	
	public LojaRepository() {
		super(new Loja());
	}
	
	public Loja findLojaByLogin(String loginUsuario) {
		String sql = "select l.* from loja l inner join usuario u on u.id = l.idUsuario where u.login = '" + loginUsuario +"'";		
		Query query = getSession().createSQLQuery(sql).addEntity(Loja.class);		
		return (Loja)query.uniqueResult();
	}
}