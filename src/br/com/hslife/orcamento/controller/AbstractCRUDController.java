package br.com.hslife.orcamento.controller;

import br.com.hslife.orcamento.entity.EntityPersistence;
import br.com.hslife.orcamento.exception.BusinessException;
import br.com.hslife.orcamento.service.ICRUDService;

@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractCRUDController<E extends EntityPersistence> extends AbstractController<E>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8568004710331653761L;
	
	protected String goToListPage;
	protected String goToFormPage;
	protected String goToViewPage;
	
	public AbstractCRUDController(E entity) {
		super(entity);
		goToListPage = "/pages/" + entity.getClass().getSimpleName() + "/list" + entity.getClass().getSimpleName();
		goToFormPage = "/pages/" + entity.getClass().getSimpleName() + "/form" + entity.getClass().getSimpleName();
		goToViewPage = "/pages/" + entity.getClass().getSimpleName() + "/view" + entity.getClass().getSimpleName();
	}
	
	protected abstract ICRUDService getService();
	
	protected void validate(String action) throws BusinessException {
		entity.validate();
		getService().validar(entity);
	}

	@Override
	public String startUp() {
		return list();
	}
	
	public String list() {
		operation = "list";
		actionTitle = "";
		return goToListPage;
	}
	
	public String create() {
		operation = "create";
		actionTitle = " - Novo";
		return goToFormPage;
	}
	
	public String save() {
		try {
			if (entity.getId() == null) {
				validate(operation);
				getService().cadastrar(entity);
				infoMessage("Registro cadastrado com sucesso!");
			} else {
				validate(operation);
				getService().alterar(entity);
				infoMessage("Registro alterado com sucesso!");
			}
			initializeEntity();
			return list();
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String view() {
		try {
			entity = (E) getService().buscarPorID(idEntity);
			operation = "delete";
			actionTitle = " - Excluir";
			return goToViewPage;
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String edit() {
		try {
			entity = (E) getService().buscarPorID(idEntity);
			operation = "edit";
			actionTitle = " - Editar";
			return goToFormPage;
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String delete() {
		try {
			validate(operation);
			getService().excluir(entity);
			infoMessage("Registro excluído com sucesso!");
			initializeEntity();
			return list();
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String find() {
		return "";
	}
	
	public String cancel() {
		initializeEntity();
		return list();
	}
}