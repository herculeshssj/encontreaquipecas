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

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import br.com.hslife.encontreaquipecas.component.UsuarioComponent;
import br.com.hslife.encontreaquipecas.entity.Usuario;

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
				if (usuarioComponent.getUsuarioLogado() != null) {
					u = usuarioComponent.getUsuarioLogado();
					u.setSenha(null);
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", u);
					
				}
			} catch (Exception e) {
				errorMessage(e.getMessage());
				e.printStackTrace();
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