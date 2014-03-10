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
	
	@SuppressWarnings("unchecked")
	public List<Loja> findAll() {
		String sql = "select l.* from loja l inner join usuario u on u.id = l.idUsuario";		
		Query query = getSession().createSQLQuery(sql).addEntity(Loja.class);		
		return query.list();
	}
}