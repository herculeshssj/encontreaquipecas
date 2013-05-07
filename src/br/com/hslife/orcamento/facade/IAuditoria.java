package br.com.hslife.orcamento.facade;

import java.util.List;

import br.com.hslife.orcamento.entity.Auditoria;
import br.com.hslife.orcamento.exception.BusinessException;
import br.com.hslife.orcamento.model.CriterioAuditoria;

public interface IAuditoria {
	
	public Auditoria buscarPorId(Long id) throws BusinessException;
	
	public List<Auditoria> buscarPorCriterios(CriterioAuditoria criterio) throws BusinessException;
	
	public List<String> buscarClasses() throws BusinessException;
	
	public void excluir(Auditoria auditoria) throws BusinessException;

}
