package br.com.hslife.orcamento.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.hslife.orcamento.entity.Usuario;
import br.com.hslife.orcamento.facade.IUsuario;

@ManagedBean(name="recuperarSenhaMB")
@RequestScoped
public class RecuperarSenhaController {

	@ManagedProperty(name="service", value="#{usuarioService}")
	private IUsuario service;
	
	private Usuario entity;
	
	public RecuperarSenhaController() {
		entity = new Usuario();
	}
	
	public String recuperarSenha() {
		try {
			getService().recuperarSenha(entity);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Senha alterada com sucesso!"));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Senha de acesso foi enviada para o e-mail cadastrado."));
			entity = new Usuario();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),null));
		}
		return "";
	}

	public IUsuario getService() {
		return service;
	}

	public void setService(IUsuario service) {
		this.service = service;
	}

	public Usuario getEntity() {
		return entity;
	}

	public void setEntity(Usuario entity) {
		this.entity = entity;
	}
}