package br.com.hslife.encontreaquipecas.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.hslife.encontreaquipecas.entity.Consumidor;
import br.com.hslife.encontreaquipecas.entity.Endereco;
import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.entity.Telefone;
import br.com.hslife.encontreaquipecas.entity.Usuario;
import br.com.hslife.encontreaquipecas.facade.IUsuario;

@ManagedBean(name="efetuarRegistroMB")
@SessionScoped
public class EfetuarRegistroController extends AbstractController<Usuario>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4039320765516292024L;

	@ManagedProperty(name="service", value="#{usuarioService}")
	private IUsuario service;
	
	private Loja loja;
	private Consumidor consumidor;
	private Usuario usuario;
	private Endereco endereco;
	private Telefone telefone;
	
	private String perfilUsuario;
	
	public EfetuarRegistroController() {
		super(new Usuario());		
	}
	
	@Override
	protected void initializeEntity() {
		loja = new Loja();
		consumidor = new Consumidor();
		perfilUsuario = null;
	}
	
	public String efetuarRegistroPasso1() {
		initializeEntity();
		return "efetuarRegistroPasso1";
	}
	
	public String efetuarRegistroPasso2() {
		if (perfilUsuario.equals("LOJA")) {
			loja = new Loja();
		} else if (perfilUsuario.equals("CONSUMIDOR")) {
			consumidor = new Consumidor();
		} else {
			errorMessage("Opção inválida!");
			return "";
		}
		return "efetuarRegistroPasso2";
	}
	
	public String efetuarRegistroPasso3() {
		return "efetuarRegistroPasso3";
	}
	
	public String efetuarRegistroPasso4() {
		return "efetuarRegistroPasso4";
	}
	
	public String efetuarRegistroPasso5() {
		return "efetuarRegistroPasso5";
	}
	
	public String finalizarRegistro() {
		return "login";
	}
	
	public IUsuario getService() {
		return service;
	}

	public void setService(IUsuario service) {
		this.service = service;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Consumidor getConsumidor() {
		return consumidor;
	}

	public void setConsumidor(Consumidor consumidor) {
		this.consumidor = consumidor;
	}

	public String getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(String perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}