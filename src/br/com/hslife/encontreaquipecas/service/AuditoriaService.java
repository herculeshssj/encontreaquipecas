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

package br.com.hslife.encontreaquipecas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hslife.encontreaquipecas.entity.Auditoria;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IAuditoria;
import br.com.hslife.encontreaquipecas.model.CriterioAuditoria;
import br.com.hslife.encontreaquipecas.repository.AuditoriaRepository;

@Service("auditoriaService")
@Transactional
public class AuditoriaService implements IAuditoria {
	
	@Autowired
	private AuditoriaRepository repository;

	public AuditoriaRepository getRepository() {
		return repository;
	}

	public void setRepository(AuditoriaRepository repository) {
		this.repository = repository;
	}

	public Auditoria buscarPorId(Long id) throws BusinessException {
		return getRepository().findById(id);
	}
	
	public List<Auditoria> buscarPorCriterios(CriterioAuditoria criterio) throws BusinessException {
		return getRepository().findByCriteriosAuditoria(criterio);
	}
	
	public List<String> buscarClasses() throws BusinessException {
		return getRepository().findClasses();
	}
	
	public void excluir(Auditoria auditoria) throws BusinessException {
		getRepository().delete(auditoria);
	}
}