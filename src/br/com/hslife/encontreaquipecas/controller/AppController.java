package br.com.hslife.encontreaquipecas.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import br.com.hslife.encontreaquipecas.component.UsuarioComponent;
import br.com.hslife.encontreaquipecas.entity.EntityPersistence;
import br.com.hslife.encontreaquipecas.entity.Usuario;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IUsuario;

public abstract class AppController<T extends EntityPersistence> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8728324648007202318L;
	
	protected Severity INFORMACAO = FacesMessage.SEVERITY_INFO;
	protected Severity AVISO = FacesMessage.SEVERITY_WARN;
	protected Severity ERRO = FacesMessage.SEVERITY_ERROR;
	protected Severity ERRO_FATAL = FacesMessage.SEVERITY_FATAL;
	
	protected T entity;
	protected Long idEntity;
	protected List<T> listEntity;
	
	protected String moduleTitle = "";
	
	@ManagedProperty(name="usuarioService", value="#{usuarioService}")
	private IUsuario usuarioService;
	
	@ManagedProperty(name="usuarioComponent", value="#{usuarioComponent}")
	private UsuarioComponent usuarioComponent;
	
	protected String goToListPage;
	protected String goToFormPage;
	protected String goToViewPage;
	
	protected String operationTitle;
	protected String operation; // valid: list, view, create, edit, delete, search
		
	public AppController(T entity) {
		this.entity = entity;
		goToListPage = "/modules/" + entity.getClass().getSimpleName() + "/list" + entity.getClass().getSimpleName();
		goToFormPage = "/modules/" + entity.getClass().getSimpleName() + "/form" + entity.getClass().getSimpleName();
		goToViewPage = "/modules/" + entity.getClass().getSimpleName() + "/view" + entity.getClass().getSimpleName();
		operation = "list";
	}
	
	protected abstract void initializeEntity();
	
	protected void loadCombos() {
		// TODO implementar a lógica de carregar combos nas classes filhas
	}
	
	/*
	 * Inclua o código que deve ser executado ao acessar o módulo especificado.
	 * Navegação padrão: valor da variável goToListPage
	 */
	public String startUp() {
		operation = "list";
		initializeEntity();
		return goToListPage;
	}
	
	protected FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public Usuario getUsuarioLogado() {
		if (getContext().getExternalContext().getSessionMap().get("usuarioLogado") == null) {
			Usuario u = new Usuario();
			try {
				u = usuarioComponent.getUsuarioLogado();
			} catch (Exception be) {
				viewMessage(be.getMessage(), ERRO);
			}
			u.setSenha("");
			getContext().getExternalContext().getSessionMap().put("usuarioLogado", u);
		}
		
		/*
		 * Teste com obtenção dos dados do SessionMap
		 *
		for (String obj : getContext().getExternalContext().getRequestParameterMap().keySet()) {
			System.out.print(obj + " - ");
			System.out.println(getContext().getExternalContext().getRequestParameterMap().get(obj));
		}
		*/
		return (Usuario)getContext().getExternalContext().getSessionMap().get("usuarioLogado");
	}
	
	public void viewMessage(String message) {
		viewMessage(message, INFORMACAO);
	}
	
	public void viewMessage(String message, Severity severity) {
		getContext().addMessage(null, new FacesMessage(severity, message, null));
	}
	
	public void infoMessage(String message) {
		viewMessage(message, INFORMACAO);
	}
	
	public void warnMessage(String message) {
		viewMessage(message, AVISO);
	}
	
	public void errorMessage(String message) {
		viewMessage(message, ERRO);
	}
	
	public void fatalErrorMessage(String message) {
		viewMessage(message, ERRO_FATAL);
	}
	
	public String list() {
		operation = "list";
		return goToListPage;
	}
	
	public String create() {
		operation = "create";
		loadCombos();
		return goToFormPage;
	}
	
	public String save() {
		viewMessage("Registro salvo com sucesso!");
		initializeEntity();
		return list();
	}

	public String view() {
		operation = "delete";
		return goToViewPage;
	}
	
	public String edit() {
		operation = "edit";
		loadCombos();
		return goToFormPage;
	}
	
	public String delete() {
		viewMessage("Registro excluido com sucesso!");
		initializeEntity();
		return list();
	}
	
	public String find(){
		return "";
	}
	
	public String cancel() {
		initializeEntity();
		return list();
	}
	
	public String getOperationTitle() {
		if (operation.equals("list")) {
			operationTitle = getModuleTitle();
		}
		if (operation.equals("create")) {
			operationTitle = getModuleTitle() + " - Novo";
		}
		if (operation.equals("edit")) {
			operationTitle = getModuleTitle() + " - Editar"; 
		}
		if (operation.equals("delete")) {
			operationTitle = getModuleTitle() + " - Excluir"; 
		}		
		if (operation.equals("view")) {
			operationTitle = getModuleTitle() + " - Visualizar"; 
		}
		if (operation.equals("search")) {
			operationTitle = getModuleTitle() + " - Pesquisar"; 
		}
		return operationTitle;
	}
	
	public List<Usuario> getListaUsuarios() {
		try {
			return usuarioService.getListaUsuarios();
		} catch (BusinessException be) {
			viewMessage(be.getMessage(), ERRO);
		}
		return new ArrayList<Usuario>();
	}
	
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public Long getIdEntity() {
		return idEntity;
	}

	public void setIdEntity(Long idEntity) {
		this.idEntity = idEntity;
	}

	public List<T> getListEntity() {
		return listEntity;
	}

	public void setListEntity(List<T> listEntity) {
		this.listEntity = listEntity;
	}

	public String getModuleTitle() {
		return moduleTitle;
	}

	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setUsuarioService(IUsuario usuarioService) {
		this.usuarioService = usuarioService;
	}

	public void setOperationTitle(String operationTitle) {
		this.operationTitle = operationTitle;
	}
}