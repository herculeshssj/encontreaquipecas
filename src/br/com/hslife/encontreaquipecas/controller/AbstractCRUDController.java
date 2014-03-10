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

package br.com.hslife.encontreaquipecas.controller;

import br.com.hslife.encontreaquipecas.entity.EntityPersistence;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.service.ICRUDService;

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
			infoMessage("Registro exclu�do com sucesso!");
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