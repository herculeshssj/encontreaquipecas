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
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Emissor;
import br.com.caelum.stella.boleto.Sacado;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
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
	
	private boolean mudouAreaInteresse;
	private AreaInteresse areaInteresseAnterior;
	
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
					areaInteresseAnterior = loja.getAreaInteresse();
					mudouAreaInteresse = false;
				} else {
					errorMessage("Op��o inv�lida!");
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
				areaInteresseAnterior = loja.getAreaInteresse();
				mudouAreaInteresse = false;
			} else {
				errorMessage("Op��o inv�lida!");
				return "";
			}
			return "/pages/Cadastro/formCadastro"; 
		} catch (Exception be) {
			errorMessage(be.getMessage());
		}
		return "";
	}
	
	public void save() {
		try {
			endereco.validate();			
			if (perfilUsuario.equals("LOJA")) {
				loja.setEndereco(endereco);
				loja.setUsuario(usuario);
				loja.validate();
				getService().atualizarCadastro(loja);
				infoMessage("Dados atualizados com sucesso!");
				if (!areaInteresseAnterior.equals(loja.getAreaInteresse())) {
					mudouAreaInteresse = true;
				}
			} else if (perfilUsuario.equals("CONSUMIDOR")) {
				consumidor.setEndereco(endereco);
				consumidor.setUsuario(usuario);
				consumidor.validate();
				getService().atualizarCadastro(consumidor);
				infoMessage("Dados atualizados com sucesso!");
			} else {
				errorMessage("Op��o inv�lida!");
			}			
		} catch (BusinessException be) {
			errorMessage(be.getMessage());
		}
	}
	
	public void gerarBoleto() {
		Calendar dataAtual = Calendar.getInstance();
		Datas datas = Datas.novasDatas()
			    .comDocumento(dataAtual.get(Calendar.DAY_OF_MONTH), dataAtual.get(Calendar.MONTH),
			    		dataAtual.get(Calendar.YEAR))
			    .comProcessamento(dataAtual.get(Calendar.DAY_OF_MONTH), dataAtual.get(Calendar.MONTH),
			    		dataAtual.get(Calendar.YEAR))
			    .comVencimento(dataAtual.get(Calendar.DAY_OF_MONTH), dataAtual.get(Calendar.MONTH),
			    		dataAtual.get(Calendar.YEAR));  

			Emissor emissor = Emissor.novoEmissor()  
		            .comCedente("EncontreAquiPeças")  
		            .comAgencia(1824).comDigitoAgencia('4')  
		            .comContaCorrente(76000)  
		            .comNumeroConvenio(1207113)  
		            .comDigitoContaCorrente('5')  
		            .comCarteira(18)  
		            .comNossoNumero(9000206);  

		        Sacado sacado = Sacado.novoSacado()  
			    .comNome(usuario.getNome())  
		            .comCpf(loja.getCnpj()) 
		            .comEndereco(endereco.getLabel());  

		        Banco banco = new BancoDoBrasil();  

		        // Setar o valor do boleto de acordo com o tipo de serviço
		        double valorBoleto = 0.0;
		        if (loja.getAreaInteresse().equals(AreaInteresse.BANNER)) {
		        	valorBoleto = 50;
		        } else {
		        	valorBoleto = 150;
		        }
		        
		        
			Boleto boleto = Boleto.novoBoleto()  
		            .comBanco(banco)  
		            .comDatas(datas)  
		            .comDescricoes()  
		            .comEmissor(emissor)  
		            .comSacado(sacado)		            
		            .comValorBoleto(valorBoleto)  
		            .comNumeroDoDocumento("1234")  
		            .comInstrucoes("Pagamento do servi�o prestado")  
		            .comLocaisDePagamento("EncontreAquiPecas");  

		        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);  

		        // Para gerar um boleto em PDF  
		        gerador.geraPDF("BancoDoBrasil.pdf");  

		        // Para gerar um boleto em PNG  
		        gerador.geraPNG("BancoDoBrasil.png");  

		        // Para gerar um array de bytes a partir de um PDF  
		        byte[] bPDF = gerador.geraPDF();  

		        // Para gerar um array de bytes a partir de um PNG  
		        // byte[] bPNG = gerador.geraPNG();
		
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			try {			
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition","attachment; filename=boleto.pdf");
				response.setContentLength(bPDF.length);
				ServletOutputStream output = response.getOutputStream();
				output.write(bPDF, 0, bPDF.length);
				FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				errorMessage(e.getMessage());
			}
			mudouAreaInteresse = false;
	}
	
	public List<SelectItem> getListaAreaInteresse() {
		List<SelectItem> listaSelectItem = new ArrayList<SelectItem>();
		listaSelectItem.add(new SelectItem(AreaInteresse.BANNER, "Banner"));
		listaSelectItem.add(new SelectItem(AreaInteresse.PRODUTO, "Divulga��o de produto"));
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

	public boolean isMudouAreaInteresse() {
		return mudouAreaInteresse;
	}

	public void setMudouAreaInteresse(boolean mudouAreaInteresse) {
		this.mudouAreaInteresse = mudouAreaInteresse;
	}

	public AreaInteresse getAreaInteresseAnterior() {
		return areaInteresseAnterior;
	}

	public void setAreaInteresseAnterior(AreaInteresse areaInteresseAnterior) {
		this.areaInteresseAnterior = areaInteresseAnterior;
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