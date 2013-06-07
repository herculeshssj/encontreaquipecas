package br.com.hslife.encontreaquipecas.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.hslife.encontreaquipecas.entity.Consumidor;
import br.com.hslife.encontreaquipecas.entity.Endereco;
import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.entity.Usuario;
import br.com.hslife.encontreaquipecas.enumeration.AreaInteresse;
import br.com.hslife.encontreaquipecas.enumeration.TipoUsuario;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.ICadastro;
import br.com.hslife.encontreaquipecas.facade.IUsuario;

@ManagedBean(name="atualizarCadastroMB")
@SessionScoped
public class AtualizarCadastroController extends AbstractController<Usuario>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4039320765516292024L;

	@ManagedProperty(name="service", value="#{cadastroService}")
	private ICadastro service;
	
	@ManagedProperty(name="usuarioService", value="#{usuarioService}")
	private IUsuario usuarioService;
	
	private Loja loja;
	private Consumidor consumidor;
	private Endereco endereco;
	private Usuario usuario;
	
	private String loginUsuario;
	private String perfilUsuario;
	
	public AtualizarCadastroController() {
		super(new Usuario());
		
		moduleTitle = "Atualizar Dados Cadastrais";
	}

	@Override
	protected void initializeEntity() {
		loja = new Loja();
		consumidor = new Consumidor();
	}

	@Override
	public String startUp() {
		if (getUsuarioLogado().getLogin().equals("admin")) {
			return "/pages/Cadastro/listCadastro";
		} else {
			try {
				if (getUsuarioLogado().getTipoUsuario().equals(TipoUsuario.ROLE_USER)) {
					perfilUsuario = "CONSUMIDOR";
					consumidor = getService().buscarConsumidorPorLogin(getUsuarioLogado().getLogin());
					endereco = consumidor.getEndereco();
					usuario = consumidor.getUsuario();
				} else if (getUsuarioLogado().getTipoUsuario().equals(TipoUsuario.ROLE_STORE)) {
					perfilUsuario = "LOJA";
					loja = getService().buscarLojaPorLogin(getUsuarioLogado().getLogin());
					endereco = loja.getEndereco();
					usuario = loja.getUsuario();
				} else {
					errorMessage("Opção inválida!");
				}
			} catch (BusinessException be) {
				errorMessage(be.getMessage());
			}
			return "/pages/Cadastro/formCadastro";
		}
	}
	
	public String find() {
		try {
			listEntity = usuarioService.buscarTodosPorLogin(loginUsuario);
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";		
	}
	
	public String edit() {
		try {
			Usuario u = getService().buscarUsuarioPorId(idEntity);
			if (u.getTipoUsuario().equals(TipoUsuario.ROLE_USER)) {
				perfilUsuario = "CONSUMIDOR";
				consumidor = getService().buscarConsumidorPorLogin(u.getLogin());
				endereco = consumidor.getEndereco();
				usuario = consumidor.getUsuario();
			} else if (u.getTipoUsuario().equals(TipoUsuario.ROLE_STORE)) {
				perfilUsuario = "LOJA";
				loja = getService().buscarLojaPorLogin(u.getLogin());
				endereco = loja.getEndereco();
				usuario = loja.getUsuario();
			} else {
				errorMessage("Opção inválida!");
				return "";
			}
			return "/pages/Cadastro/formCadastro"; 
		} catch (Exception be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public void save() {
		
	}
	
	public List<SelectItem> getListaAreaInteresse() {
		List<SelectItem> listaSelectItem = new ArrayList<SelectItem>();
		listaSelectItem.add(new SelectItem(AreaInteresse.BANNER, "Banner"));
		listaSelectItem.add(new SelectItem(AreaInteresse.PRODUTO, "Divulgação de produto"));
		return listaSelectItem;
	}
	
	public ICadastro getService() {
		return service;
	}

	public void setService(ICadastro service) {
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

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public IUsuario getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuario usuarioService) {
		this.usuarioService = usuarioService;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(String perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/*
	private Loja loja;
	private Consumidor consumidor;
	private Usuario usuario;
	private Endereco endereco;
	private Telefone telefone;
	
	private String perfilUsuario;
	
	public AtualizarCadastroController() {
		super(new Usuario());		
	}
	
	@Override
	protected void initializeEntity() {
		loja = new Loja();
		consumidor = new Consumidor();
		endereco = new Endereco();
		telefone = new Telefone();
		usuario = new Usuario();
		perfilUsuario = null;
	}
	
	public String efetuarRegistroPasso1() {
		initializeEntity();
		return "efetuarRegistroPasso1";
	}
	
	public String efetuarRegistroPasso2() {
		if (perfilUsuario.equals("LOJA")) {
			loja = new Loja();
			endereco = new Endereco();
			telefone = new Telefone();
			usuario = new Usuario();
		} else if (perfilUsuario.equals("CONSUMIDOR")) {
			consumidor = new Consumidor();
			endereco = new Endereco();
			telefone = new Telefone();
			usuario = new Usuario();
		} else {
			errorMessage("Opção inválida!");
			return "";
		}
		return "efetuarRegistroPasso2";
	}
	
	public String retornarEfetuarRegistroPasso2() {
		return "efetuarRegistroPasso2";
	}
	
	public String efetuarRegistroPasso3() {
		try {
			telefone.validate();
			endereco.validate();
			if (perfilUsuario.equals("LOJA")) {
				loja.setEndereco(endereco);
				loja.getTelefones().add(telefone);
				loja.setUsuario(usuario);
				loja.validate();
				return "efetuarRegistroPasso3";
			} else if (perfilUsuario.equals("CONSUMIDOR")) {
				consumidor.setEndereco(endereco);
				consumidor.getTelefones().add(telefone);
				consumidor.setUsuario(usuario);
				consumidor.validate();
				return "efetuarRegistroPasso3";
			} else {
				errorMessage("Opção inválida!");
			}
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String efetuarRegistroPasso4() {
		try {
			if (perfilUsuario.equals("LOJA")) {
				getService().efetuarRegistro(loja);
			} else if (perfilUsuario.equals("CONSUMIDOR")) {
				getService().efetuarRegistro(consumidor);
			} else {
				errorMessage("Opção inválida!");
			}
			return "efetuarRegistroPasso4";
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String efetuarRegistroPasso5() {
		return "efetuarRegistroPasso5";
	}
	
	public String finalizarRegistro() {
		return "login";
	}
	
	public List<SelectItem> getListaAreaInteresse() {
		List<SelectItem> listaSelectItem = new ArrayList<SelectItem>();
		listaSelectItem.add(new SelectItem(AreaInteresse.BANNER, "Banner"));
		listaSelectItem.add(new SelectItem(AreaInteresse.PRODUTO, "Divulgação de produto"));
		return listaSelectItem;
	}
	
	public List<SelectItem> getListaTipoTelefone() {
		List<SelectItem> listaSelectItem = new ArrayList<SelectItem>();
		listaSelectItem.add(new SelectItem(TipoTelefone.RESIDENCIAL, "Residencial"));
		listaSelectItem.add(new SelectItem(TipoTelefone.COMERCIAL, "Comercial"));
		listaSelectItem.add(new SelectItem(TipoTelefone.FAX, "Fax"));
		listaSelectItem.add(new SelectItem(TipoTelefone.OUTROS, "Outros"));
		return listaSelectItem;
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
	*/
}