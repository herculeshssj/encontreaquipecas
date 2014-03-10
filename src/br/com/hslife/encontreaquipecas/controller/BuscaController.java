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

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.hslife.encontreaquipecas.entity.Banner;
import br.com.hslife.encontreaquipecas.entity.HistoricoPesquisa;
import br.com.hslife.encontreaquipecas.entity.Produto;
import br.com.hslife.encontreaquipecas.enumeration.TipoUsuario;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IBanner;
import br.com.hslife.encontreaquipecas.facade.IHistoricoPesquisa;
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
	
	@ManagedProperty(name="bannerService", value="#{bannerService}")
	private IBanner bannerService;
	
	@ManagedProperty(name="historicoPesquisaService", value="#{historicoPesquisaService}")
	private IHistoricoPesquisa historicoPesquisaService;
	
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
			
			// Salva a busca quando o usuário está logado
			if (getUsuarioLogado() != null && getUsuarioLogado().getTipoUsuario().equals(TipoUsuario.ROLE_USER)) {
				HistoricoPesquisa hp = new HistoricoPesquisa();
				hp.setAno(criterio.getAno());
				hp.setFabricante(criterio.getFabricante());
				hp.setModelo(criterio.getModelo());
				hp.setNome(criterio.getNome());
				hp.setConsumidor(historicoPesquisaService.buscarPorLogin(getUsuarioLogado().getLogin()));
				historicoPesquisaService.cadastrar(hp);
			}			
			return "/produto";
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
			be.getCause().printStackTrace();
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
	
	public void paint (OutputStream stream, Object object) throws IOException {
		stream.write(getListaBanner().get((Integer)object).getDados());
   }
	
	public List<Banner> getListaBanner() {
		try {
			return bannerService.buscarTodos();
		} catch (Exception be) {
			errorMessage(be.getMessage());
		}
		return new ArrayList<Banner>();
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
	
	public List<String> getListaNomes() {
		try {
			return getService().buscarNomes();
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

	public void setBannerService(IBanner bannerService) {
		this.bannerService = bannerService;
	}

	public void setHistoricoPesquisaService(
			IHistoricoPesquisa historicoPesquisaService) {
		this.historicoPesquisaService = historicoPesquisaService;
	}
}