package br.com.hslife.encontreaquipecas.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.hslife.encontreaquipecas.enumeration.AreaInteresse;
import br.com.hslife.encontreaquipecas.exception.BusinessException;
import br.com.hslife.encontreaquipecas.util.Util;

@Entity
@Table(name="loja")
@SuppressWarnings("serial")
public class Loja extends EntityPersistence {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(length=200, nullable=false)
	private String contato;
	
	@Column(length=14, nullable=false)
	private String cnpj;
	
	@Column(length=10, nullable=false)
	private String inscricaoEstadual;
	
	@Column(length=100, nullable=false)
	private String site;
	
	@Column(length=10)
	@Enumerated(EnumType.STRING)
	private AreaInteresse areaInteresse;
	
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idEndereco")
	private Endereco endereco;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Telefone> telefones;
	
	public Loja() {
		usuario = new Usuario();
		endereco = new Endereco();
		telefones = new ArrayList<Telefone>();
	}
	
	@Override
	public String getLabel() {
		return this.usuario.getLabel();
	}
	
	@Override
	public void validate() throws BusinessException {
		if (endereco == null) {
			throw new BusinessException("Informe o endereço!");
		}
		
		if (usuario == null) {
			throw new BusinessException("Informe o usuário!");
		}
		
		if (telefones == null || telefones.size() == 0) {
			throw new BusinessException("Informe pelo menos um telefone!");
		}
		
		if (this.contato == null || this.contato.trim().isEmpty()) {
			throw new BusinessException("Informe o contato!");
		}
		
		if (this.contato.length() > 10) {
			throw new BusinessException("Contato deve ser menor que 10 caracteres!");
		}
		
		if (this.cnpj == null || this.cnpj.trim().isEmpty()) {
			throw new BusinessException("Informe o CNPJ!");
		}
		
		if (this.cnpj.length() != 14) {
			throw new BusinessException("CNPJ deve ter exatos 14 caracteres!");
		}
		
		if (!Util.validCnpj(this.cnpj)) {
			throw new BusinessException("CNPJ informado não é válido!");
		}
		
		if (this.inscricaoEstadual == null || this.inscricaoEstadual.trim().isEmpty()) {
			throw new BusinessException("Informe a inscrição estadual!");
		}
		
		if (this.inscricaoEstadual.length() > 14) {
			throw new BusinessException("Inscrição estadual deve ser menor que 14 caracteres!");
		}
		
		if (this.site != null && this.site.length() > 100) {
			throw new BusinessException("Site deve ser menor que 100 caracteres!");
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public AreaInteresse getAreaInteresse() {
		return areaInteresse;
	}

	public void setAreaInteresse(AreaInteresse areaInteresse) {
		this.areaInteresse = areaInteresse;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
}