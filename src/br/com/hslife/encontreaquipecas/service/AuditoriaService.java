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