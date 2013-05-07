package br.com.hslife.orcamento.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="auditoria")
public class Auditoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 170587402135053960L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String classe;
	
	@Column(nullable=false)
	private String usuario;
	
	@Column(nullable=false)
	private String ip;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;
	
	@Column(nullable=false)
	private String transacao; // INSERT, UPDATE, DELETE
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<AuditoriaDados> dadosAuditoria;
	
	public Auditoria() {
		data = Calendar.getInstance().getTime();
		dataHora = Calendar.getInstance().getTime();
		dadosAuditoria = new ArrayList<AuditoriaDados>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTransacao() {
		return transacao;
	}

	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}

	public List<AuditoriaDados> getDadosAuditoria() {
		return dadosAuditoria;
	}

	public void setDadosAuditoria(List<AuditoriaDados> dadosAuditoria) {
		this.dadosAuditoria = dadosAuditoria;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
}