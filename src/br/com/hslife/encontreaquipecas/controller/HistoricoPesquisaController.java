package br.com.hslife.encontreaquipecas.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.hslife.encontreaquipecas.entity.HistoricoPesquisa;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
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
		try {
			listEntity = getService().buscarPorConsumidor(getService().buscarPorLogin(getUsuarioLogado().getLogin()));
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return super.startUp();
	}
	
	@Override
	protected void initializeEntity() {
		entity = new HistoricoPesquisa();
	}

	public IHistoricoPesquisa getService() {
		return service;
	}

	public void setService(IHistoricoPesquisa service) {
		this.service = service;
	}
}