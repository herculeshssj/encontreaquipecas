package br.com.hslife.orcamento.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.hslife.orcamento.entity.Auditoria;
import br.com.hslife.orcamento.entity.Usuario;
import br.com.hslife.orcamento.exception.BusinessException;
import br.com.hslife.orcamento.facade.IAuditoria;
import br.com.hslife.orcamento.facade.IUsuario;
import br.com.hslife.orcamento.model.CriterioAuditoria;

@ManagedBean(name="auditoriaMB")
@SessionScoped
@SuppressWarnings({"serial"})
public class AuditoriaController extends AbstractController<Auditoria> {

	@ManagedProperty(name="service", value="#{auditoriaService}")
	private IAuditoria service;
	
	@ManagedProperty(name="usuarioService", value="#{usuarioService}")
	private IUsuario usuarioService;
	
	private CriterioAuditoria criterio;

	public AuditoriaController() {
		super(new Auditoria());
		criterio = new CriterioAuditoria();
		
		moduleTitle = "Auditoria do Sistema";
	}
	
	@Override
	protected void initializeEntity() {
		entity = new Auditoria();
		listEntity = new ArrayList<Auditoria>();
	}
	
	public String find() {
		try {
			listEntity = getService().buscarPorCriterios(criterio);	
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String view() {
		try {
			entity = getService().buscarPorId(idEntity);
			return "/pages/Auditoria/viewAuditoria";
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String delete() {
		try {
			entity = getService().buscarPorId(idEntity);
			getService().excluir(entity);
			infoMessage("Registro excluído com sucesso!");
			initializeEntity();
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String excluirLog() {
		if (listEntity == null || listEntity.isEmpty()) {
			infoMessage("Nenhum resultado encontrado!");
		} else {
			try {
				for (Auditoria auditoria : listEntity) {
					entity = getService().buscarPorId(auditoria.getId());
					getService().excluir(entity);
				}				
				infoMessage("Registros excluídos com sucesso!");
				initializeEntity();
			} catch (BusinessException be) {
				errorMessage(be.getMessage());
			}			
		}
		return "";
	}
	
	public String cancel() {
		initializeEntity();
		return startUp();
	}
	
	public List<Usuario> getListaUsuarios() {
		try {
			return usuarioService.getListaUsuarios();
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return new ArrayList<Usuario>();
	}
	
	public List<String> getListaClasses() {
		try {
			return getService().buscarClasses();
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return new ArrayList<String>();
	}
	
	/* Métodos Getters e Setters */

	public CriterioAuditoria getCriterio() {
		return criterio;
	}

	public void setCriterio(CriterioAuditoria criterio) {
		this.criterio = criterio;
	}

	public void setService(IAuditoria service) {
		this.service = service;
	}

	public IAuditoria getService() {
		return service;
	}

	public void setUsuarioService(IUsuario usuarioService) {
		this.usuarioService = usuarioService;
	}
}