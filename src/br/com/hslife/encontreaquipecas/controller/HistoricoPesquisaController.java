package br.com.hslife.encontreaquipecas.controller;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.hslife.encontreaquipecas.entity.HistoricoPesquisa;
import br.com.hslife.encontreaquipecas.facade.IHistoricoPesquisa;

@ManagedBean(name="historicoPesquisaMB")
@SessionScoped
public class HistoricoPesquisaController extends AbstractCRUDController<HistoricoPesquisa>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6812416958718109307L;
	
	@ManagedProperty(name="service", value="#{historicoPesquisaService}")
	private IHistoricoPesquisa service;
	
	public HistoricoPesquisaController() {
		super(new HistoricoPesquisa());
		
		moduleTitle = "Pesquisas realizadas";
	}
	
	@Override
	public String startUp() {
		return super.startUp();
	}
	
	@Override
	protected void initializeEntity() {
		entity = new HistoricoPesquisa();
		listEntity = new ArrayList<HistoricoPesquisa>();
	}
	
	@Override
	public String save() {
		/*
		try {
			if (!getUsuarioLogado().getLogin().equals("admin")) {				
				entity.setLoja(lojaService.buscarPorLogin(getUsuarioLogado().getLogin()));							
			}
			return super.save();
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
		*/
		return super.save();
	}
	
	@Override
	public String find() {
		/*
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
		*/
		return super.find();
	}

	public IHistoricoPesquisa getService() {
		return service;
	}

	public void setService(IHistoricoPesquisa service) {
		this.service = service;
	}
}