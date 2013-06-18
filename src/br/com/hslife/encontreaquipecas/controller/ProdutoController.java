package br.com.hslife.encontreaquipecas.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.entity.Produto;
import br.com.hslife.encontreaquipecas.enumeration.AreaInteresse;
import br.com.hslife.encontreaquipecas.enumeration.TipoUsuario;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.ILoja;
import br.com.hslife.encontreaquipecas.facade.IProduto;

@ManagedBean(name="produtoMB")
@SessionScoped
public class ProdutoController extends AbstractCRUDController<Produto>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6812416958718109307L;
	
	@ManagedProperty(name="service", value="#{produtoService}")
	private IProduto service;
	
	@ManagedProperty(name="lojaService", value="#{lojaService}")
	private ILoja lojaService;
	
	private String nomeProduto;

	public ProdutoController() {
		super(new Produto());
		
		moduleTitle = "Produtos";
	}
	
	@Override
	public String startUp() {
		try {
			if (getUsuarioLogado().getTipoUsuario().equals(TipoUsuario.ROLE_STORE)) {
				Loja loja = lojaService.buscarPorLogin(getUsuarioLogado().getLogin());
				if (loja.getAreaInteresse().equals(AreaInteresse.PRODUTO)) {
					return super.startUp();
				} else {
					warnMessage("Você não está habilitado para utilizar este serviço!");
					return "";
				}
			} else {
				return super.startUp();
			}
		} catch (BusinessException e) {
			errorMessage(e.getMessage());
		}
		return "";
	}
	
	@Override
	protected void initializeEntity() {
		entity = new Produto();
		listEntity = new ArrayList<Produto>();
	}
	
	@Override
	public String find() {
		try {
			if (getUsuarioLogado().getLogin().equals("admin")) {
				listEntity = getService().buscarPorNome(nomeProduto);
			} else {
				listEntity = getService().buscarPorNomeELoja(nomeProduto, lojaService.buscarPorLogin(getUsuarioLogado().getLogin()));
			}			
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return super.find();
	}

	public List<Loja> getListaLoja() {
		try {
			if (getUsuarioLogado().getLogin().equals("admin")) {
				return lojaService.buscarTodos();
			} else {
				ArrayList<Loja> lista = new ArrayList<Loja>();
				lista.add(lojaService.buscarPorLogin(getUsuarioLogado().getLogin()));
				return lista;
			}
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return new ArrayList<Loja>();
	}
	
	public IProduto getService() {
		return service;
	}

	public void setService(IProduto service) {
		this.service = service;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public ILoja getLojaService() {
		return lojaService;
	}

	public void setLojaService(ILoja lojaService) {
		this.lojaService = lojaService;
	}
}