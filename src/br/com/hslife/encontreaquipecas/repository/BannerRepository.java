package br.com.hslife.encontreaquipecas.repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.hslife.encontreaquipecas.entity.Banner;
import br.com.hslife.encontreaquipecas.entity.Loja;

@Repository
public class BannerRepository extends AbstractCRUDRepository<Banner> {

	public BannerRepository() {
		super(new Banner());
	}
	
	public Banner findByLoja(Loja loja) {
		Criteria criteria = getSession().createCriteria(Banner.class);
		criteria.add(Restrictions.eq("loja.id", loja.getId()));
		return (Banner)criteria.uniqueResult();
	}
}