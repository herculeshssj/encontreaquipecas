package br.com.hslife.orcamento.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.hslife.orcamento.enumeration.TipoUsuario;

@Entity
@Table(name="usuario")
@SuppressWarnings("serial")
public class Usuario extends EntityPersistence {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=100, nullable=false)
	private String nome;
	
	@Column(length=50, nullable=false, unique=true)
	private String login;
	
	@Column(length=40, nullable=false)
	private String senha;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	
	@Column(length=40, nullable=true)
	private String email;
	
	@Column(length=10)
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;
	
	@Column
	private boolean ativo;
	
	public Usuario() {
		dataCriacao = new Date();
		ativo = true;
		login = "";
		tipoUsuario = TipoUsuario.ROLE_USER;
	}
	
	@Override
	public String getLabel() {
		return this.nome;
	}
	
	@Override
	public void validate() {
				
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}