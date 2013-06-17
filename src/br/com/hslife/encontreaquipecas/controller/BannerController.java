package br.com.hslife.encontreaquipecas.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import br.com.hslife.encontreaquipecas.entity.Banner;
import br.com.hslife.encontreaquipecas.entity.Loja;
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
	protected void initializeEntity() {
		entity = new Banner();
		listEntity = new ArrayList<Banner>();
	}

	@Override
	public String save() {
		try {
			if (getUsuarioLogado().getLogin().equals("admin")) {
				return super.save();
			} else {
				entity.setLoja(lojaService.buscarPorLogin(getUsuarioLogado().getLogin()));
				return super.save();
			}			
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public void carregarArquivo(FileUploadEvent event) throws Exception {
		UploadedFile item = event.getUploadedFile();
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
			infoMessage("Arquivo excluído!");
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