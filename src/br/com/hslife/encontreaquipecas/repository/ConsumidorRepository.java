package br.com.hslife.encontreaquipecas.repository;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.hslife.encontreaquipecas.entity.Consumidor;

@Repository
@Transactional
public class ConsumidorRepository extends AbstractCRUDRepository<Consumidor> {
	
	public ConsumidorRepository() {
		super(new Consumidor());
	}
	
	public Consumidor findConsumidorByLogin(String loginUsuario) {
		String sql = "select c.* from consumidor c inner join usuario u on u.id = c.idUsuario where u.login = '" + loginUsuario +"'";		
		Query query = getSession().createSQLQuery(sql).addEntity(Consumidor.class);		
		return (Consumidor)query.uniqueResult();
	}
}