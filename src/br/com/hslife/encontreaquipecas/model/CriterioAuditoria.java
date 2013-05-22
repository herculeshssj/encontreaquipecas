package br.com.hslife.encontreaquipecas.model;

import java.util.Date;

public class CriterioAuditoria {

	private String usuario;
	
	private String transacao;
	
	private String classe;
	
	private Date inicio;
	
	private Date fim;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getTransacao() {
		return transacao;
	}

	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}	
}