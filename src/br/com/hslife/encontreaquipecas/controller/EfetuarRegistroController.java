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
import br.com.hslife.encontreaquipecas.entity.Telefone;
import br.com.hslife.encontreaquipecas.entity.Usuario;
import br.com.hslife.encontreaquipecas.enumeration.AreaInteresse;
import br.com.hslife.encontreaquipecas.enumeration.TipoTelefone;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.facade.IUsuario;

@ManagedBean(name="efetuarRegistroMB")
@SessionScoped
public class EfetuarRegistroController extends AbstractController<Usuario>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4039320765516292024L;

	@ManagedProperty(name="service", value="#{usuarioService}")
	private IUsuario service;
	
	private Loja loja;
	private Consumidor consumidor;
	private Usuario usuario;
	private Endereco endereco;
	private Telefone telefone;
	
	private String perfilUsuario;
	
	public EfetuarRegistroController() {
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
			errorMessage("Op��o inv�lida!");
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
				errorMessage("Op��o inv�lida!");
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
				errorMessage("Op��o inv�lida!");
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
		            .comCedente("EncontreAquiPe�as")  
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
		            .comLocaisDePagamento("EncontreAquiPe�as");  

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
	}
	
	public List<SelectItem> getListaAreaInteresse() {
		List<SelectItem> listaSelectItem = new ArrayList<SelectItem>();
		listaSelectItem.add(new SelectItem(AreaInteresse.BANNER, "Banner"));
		listaSelectItem.add(new SelectItem(AreaInteresse.PRODUTO, "Divulga��o de produto"));
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
}