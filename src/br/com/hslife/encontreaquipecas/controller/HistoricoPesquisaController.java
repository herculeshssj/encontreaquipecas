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