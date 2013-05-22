package br.com.hslife.encontreaquipecas.facade;

import java.util.List;

import br.com.hslife.encontreaquipecas.entity.Auditoria;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.model.CriterioAuditoria;

public interface IAuditoria {
	
	public Auditoria buscarPorId(Long id) throws BusinessException;
	
	public List<Auditoria> buscarPorCriterios(CriterioAuditoria criterio) throws BusinessException;
	
	public List<String> buscarClasses() throws BusinessException;
	
	public void excluir(Auditoria auditoria) throws BusinessException;

}
