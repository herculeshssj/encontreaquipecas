package br.com.hslife.encontreaquipecas.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.hslife.encontreaquipecas.entity.Produto;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IProduto;
import br.com.hslife.encontreaquipecas.model.CriterioProduto;

@ManagedBean(name="buscaMB")
@SessionScoped
public class BuscaController extends AbstractController<Produto>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 636294586015681186L;
	
	@ManagedProperty(name="service", value="#{produtoService}")
	private IProduto service;
	
	private CriterioProduto criterio;
	
	public BuscaController() {
		super(new Produto());
		criterio = new CriterioProduto();
	}

	@Override
	protected void initializeEntity() {
		criterio = new CriterioProduto();
		listEntity = new ArrayList<Produto>();
	}
	
	public String find() {
		try {
			listEntity = getService().buscarPorCriterioProduto(criterio);
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public String verDetalhes() {
		try {
			entity = getService().buscarPorID(idEntity);
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "/detalhes";
	}
	
	public String cancel() {
		return "/produto";
	}
	
	public List<String> getListaFabricantes() {
		try {
			return getService().buscarFabricantes();
		} catch (Exception be) {
			errorMessage(be.getMessage());
		}
		return new ArrayList<String>();
	}
	
	public List<String> getListaModelos() {
		try {
			return getService().buscarModelos();
		} catch (Exception be) {
			errorMessage(be.getMessage());
		}
		return new ArrayList<String>();
	}

	public List<String> getListaAnos() {
		try {
			return getService().buscarAnos();
		} catch (Exception be) {
			errorMessage(be.getMessage());
		}
		return new ArrayList<String>();
	}
	
	public IProduto getService() {
		return service;
	}

	public void setService(IProduto service) {
		this.service = service;
	}

	public CriterioProduto getCriterio() {
		return criterio;
	}

	public void setCriterio(CriterioProduto criterio) {
		this.criterio = criterio;
	}
}
