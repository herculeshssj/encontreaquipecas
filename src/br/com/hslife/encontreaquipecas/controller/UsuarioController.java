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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.hslife.encontreaquipecas.entity.Usuario;
import br.com.hslife.encontreaquipecas.enumeration.TipoUsuario;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IUsuario;

@ManagedBean(name="usuarioMB")
@SessionScoped
public class UsuarioController extends AbstractController<Usuario> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8083426925486732400L;
	
	@ManagedProperty(name="service", value="#{usuarioService}")
	private IUsuario service;
	
	private String loginUsuario;	
	
	private String novaSenha;
	private String confirmaSenha;

	public UsuarioController() {
		super(new Usuario());
	}

	@Override
	public String startUp() {
		moduleTitle = "Usu�rios";
		return super.startUp();
	}
	
	@Override
	protected void initializeEntity() {
		entity = new Usuario();
		listEntity = new ArrayList<Usuario>();
		novaSenha = "";
		confirmaSenha = "";
	}
	
	public String find() {
		try {
			if (getUsuarioLogado().getTipoUsuario().equals(TipoUsuario.ROLE_ADMIN)) {
				listEntity = getService().buscarTodosPorLogin(loginUsuario);
			} else {
				listEntity = new ArrayList<Usuario>();
				listEntity.add(getUsuarioLogado());
			}			
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String list() {
		operation = "list";
		moduleTitle = "Usu�rios";
		return "/pages/" + entity.getClass().getSimpleName() + "/list" + entity.getClass().getSimpleName(); 
	}
	
	public String create() {
		operation = "create";
		moduleTitle = "Usu�rios - Novo";
		initializeEntity();
		return "/pages/" + entity.getClass().getSimpleName() + "/form" + entity.getClass().getSimpleName();
	}
	
	public String save() {
		try {
			if (entity.getId() == null) {
				getService().cadastrar(entity, novaSenha, confirmaSenha);
				infoMessage("Usu�rio cadastrado com sucesso!");
			} else {
				getService().alterar(entity, novaSenha, confirmaSenha);
				infoMessage("Usu�rio alterado com sucesso!");
			}			
			initializeEntity();
			return list();
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String edit() {
		try {
			entity = getService().buscarPorId(idEntity);
			operation = "edit";
			moduleTitle = "Usu�rios - Editar";
			return "/pages/" + entity.getClass().getSimpleName() + "/form" + entity.getClass().getSimpleName(); 
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String cancel() {
		initializeEntity();
		return list();
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public IUsuario getService() {
		return service;
	}

	public void setService(IUsuario service) {
		this.service = service;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
}