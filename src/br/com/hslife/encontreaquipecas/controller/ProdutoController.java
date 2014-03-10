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
	public String save() {
		try {
			if (!getUsuarioLogado().getLogin().equals("admin")) {				
				entity.setLoja(lojaService.buscarPorLogin(getUsuarioLogado().getLogin()));							
			}
			return super.save();
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
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