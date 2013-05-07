package br.com.hslife.orcamento.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="auditoriadados")
public class AuditoriaDados {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String nomeAtributo;
	
	@Column(nullable=false)
	private String valorAtributo;
	
	@Column(nullable=false)
	private String situacaoOperacao; //BEFORE, AFTER
	
	public AuditoriaDados() {
	}
	
	public AuditoriaDados(String valor, String atributo, String operacao) {
		this.nomeAtributo = atributo;
		this.valorAtributo = valor;
		this.situacaoOperacao = operacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeAtributo() {
		return nomeAtributo;
	}

	public void setNomeAtributo(String nomeAtributo) {
		this.nomeAtributo = nomeAtributo;
	}

	public String getValorAtributo() {
		return valorAtributo;
	}

	public void setValorAtributo(String valorAtributo) {
		this.valorAtributo = valorAtributo;
	}

	public String getSituacaoOperacao() {
		return situacaoOperacao;
	}

	public void setSituacaoOperacao(String situacaoOperacao) {
		this.situacaoOperacao = situacaoOperacao;
	}
}
