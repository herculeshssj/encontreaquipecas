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

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.hslife.encontreaquipecas.entity.Auditoria;
import br.com.hslife.encontreaquipecas.entity.Usuario;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IAuditoria;
import br.com.hslife.encontreaquipecas.facade.IUsuario;
import br.com.hslife.encontreaquipecas.model.CriterioAuditoria;

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
			infoMessage("Registro excluido com sucesso!");
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
				infoMessage("Registros excluidos com sucesso!");
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