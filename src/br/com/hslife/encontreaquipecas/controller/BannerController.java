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
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import br.com.hslife.encontreaquipecas.entity.Banner;
import br.com.hslife.encontreaquipecas.entity.Loja;
import br.com.hslife.encontreaquipecas.enumeration.AreaInteresse;
import br.com.hslife.encontreaquipecas.enumeration.TipoUsuario;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IBanner;
import br.com.hslife.encontreaquipecas.facade.ILoja;
import br.com.hslife.encontreaquipecas.util.Util;

@ManagedBean(name="bannerMB")
@SessionScoped
public class BannerController extends AbstractCRUDController<Banner>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6812416958718109307L;
	
	@ManagedProperty(name="service", value="#{bannerService}")
	private IBanner service;
	
	@ManagedProperty(name="lojaService", value="#{lojaService}")
	private ILoja lojaService;

	public BannerController() {
		super(new Banner());
		
		moduleTitle = "Banner";
	}
	
	@Override
	public String startUp() {
		try {
			if (getUsuarioLogado().getTipoUsuario().equals(TipoUsuario.ROLE_STORE)) {
				Loja loja = lojaService.buscarPorLogin(getUsuarioLogado().getLogin());
				if (loja.getAreaInteresse().equals(AreaInteresse.BANNER)) {
					this.find();
					return super.startUp();
				} else {
					warnMessage("Voc� n�oo est� habilitado para utilizar este servi�o!");
					return "";
				}
			} else {
				this.find();
				return super.startUp();
			}
		} catch (BusinessException e) {
			errorMessage(e.getMessage());
		}
		return "";
	}
	
	@Override
	public String find() {
		try {
			listEntity = new ArrayList<Banner>();
			if (getUsuarioLogado().getTipoUsuario().equals(TipoUsuario.ROLE_STORE)) {
				Loja loja = lojaService.buscarPorLogin(getUsuarioLogado().getLogin());
				listEntity.add(getService().buscarPorLoja(loja));
			} else {
				listEntity = getService().buscarTodos();
			}
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
			
	@Override
	protected void initializeEntity() {
		entity = new Banner();
	}
	
	@Override
	public String save() {
		try {
			ImageIcon temp = new ImageIcon(entity.getDados());
			if (temp.getIconWidth() !=  468 && temp.getIconHeight() != 60) {
				warnMessage("Banner deve ter as dimens�es 468px x 60px!");
			} else {
				if (getUsuarioLogado().getLogin().equals("admin")) {
					return super.save();
				} else {
					entity.setLoja(lojaService.buscarPorLogin(getUsuarioLogado().getLogin()));
					return super.save();
				}
			}
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public void carregarArquivo(FileUploadEvent event) throws Exception {
		UploadedFile item = event.getUploadedFile();
		if (entity == null) entity = new Banner();
		entity.setDados(item.getData());
		entity.setNomeArquivo(Util.removerAcentos(item.getName().replace(" ", ".")));
		entity.setContentType(item.getContentType());
		entity.setTamanho(item.getSize());
	}
	
	public void baixarArquivo() {
		if (entity == null || entity.getDados() == null || entity.getDados().length == 0) {
			warnMessage("Nenhum arquivo adicionado!");
		} else {
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			try {                        
				response.setContentType(entity.getContentType());
				response.setHeader("Content-Disposition","attachment; filename=" + entity.getNomeArquivo());
				response.setContentLength(entity.getDados().length);
				ServletOutputStream output = response.getOutputStream();
				output.write(entity.getDados(), 0, entity.getDados().length);
				FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				errorMessage(e.getMessage());
			}
		}
	}
	
	public void excluirArquivo() {
		if (entity == null || entity.getDados() == null || entity.getDados().length == 0) {
			warnMessage("Nenhum arquivo adicionado");
		} else {
			entity = new Banner();
			infoMessage("Arquivo exclu�do!");
		}
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

	public ILoja getLojaService() {
		return lojaService;
	}

	public void setLojaService(ILoja lojaService) {
		this.lojaService = lojaService;
	}

	public IBanner getService() {
		return service;
	}

	public void setService(IBanner service) {
		this.service = service;
	}
}