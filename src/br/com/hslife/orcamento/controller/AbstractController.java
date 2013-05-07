package br.com.hslife.orcamento.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import br.com.hslife.orcamento.component.UsuarioComponent;
import br.com.hslife.orcamento.entity.Usuario;

public abstract class AbstractController<E> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 700192832217328103L;
	
	protected E entity;
	protected Long idEntity;
	protected List<E> listEntity;
	
	protected String moduleTitle = "";
	protected String actionTitle = "";
	
	protected String operation = "list";
	
	@ManagedProperty(name="usuarioComponent", value="#{usuarioComponent}")
	private UsuarioComponent usuarioComponent;
		
	public AbstractController(E entity) {
		this.entity = entity;
	}
	
	protected abstract void initializeEntity();
	
	public String getModuleTitle() {
		if (moduleTitle != null && !moduleTitle.isEmpty())
			return moduleTitle + actionTitle;
		else
			return entity.getClass().getSimpleName() + actionTitle;
	}
	
	public String startUp() {
		operation = "list";
		return "/pages/" + entity.getClass().getSimpleName() + "/list" + entity.getClass().getSimpleName();
	}
	
	public Usuario getUsuarioLogado() {
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado") == null) {
			Usuario u = new Usuario();
			try {
				u = usuarioComponent.getUsuarioLogado();
				u.setSenha(null);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", u);
			} catch (Exception e) {
				errorMessage(e.getMessage());
			}
		}
		return (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
	}
	
	public void infoMessage(String mensage) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensage, null));
	}
	
	public void warnMessage(String mensage) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensage, null));
	}
	
	public void errorMessage(String mensage) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensage, null));
	}
	
	public void fatalErrorMessage(String mensage) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, mensage, null));
	}
	
	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public List<E> getListEntity() {
		return listEntity;
	}

	public void setListEntity(List<E> listEntity) {
		this.listEntity = listEntity;
	}

	public Long getIdEntity() {
		return idEntity;
	}

	public void setIdEntity(Long idEntity) {
		this.idEntity = idEntity;
	}

	public void setUsuarioComponent(UsuarioComponent usuarioComponent) {
		this.usuarioComponent = usuarioComponent;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
}