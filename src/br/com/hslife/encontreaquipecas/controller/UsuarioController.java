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
		moduleTitle = "Usuários";
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
		moduleTitle = "Usuários";
		return "/pages/" + entity.getClass().getSimpleName() + "/list" + entity.getClass().getSimpleName(); 
	}
	
	public String create() {
		operation = "create";
		moduleTitle = "Usuários - Novo";
		initializeEntity();
		return "/pages/" + entity.getClass().getSimpleName() + "/form" + entity.getClass().getSimpleName();
	}
	
	public String save() {
		try {
			if (entity.getId() == null) {
				getService().cadastrar(entity, novaSenha, confirmaSenha);
				infoMessage("Usuário cadastrado com sucesso!");
			} else {
				getService().alterar(entity, novaSenha, confirmaSenha);
				infoMessage("Usuário alterado com sucesso!");
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
			moduleTitle = "Usuários - Editar";
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