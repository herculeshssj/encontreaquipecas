package br.com.hslife.encontreaquipecas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.hslife.encontreaquipecas.exception.BusinessException;

@Entity
@Table(name="historicopesquisa")
public class HistoricoPesquisa extends EntityPersistence {

	/**
	 * 
	 */
	private static final long serialVersionUID = 515754721041310308L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=true)
	private String nome;

	@Column(nullable=true)
	private String fabricante;
	
	@Column(nullable=true)
	private String modelo;
	
	@Column(nullable=true)
	private String ano;
	
	@ManyToOne
	@JoinColumn(name="idConsumidor")
	private Consumidor consumidor;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date realizadoEm;
	
	public HistoricoPesquisa() {
		realizadoEm = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Consumidor getConsumidor() {
		return consumidor;
	}

	public void setConsumidor(Consumidor consumidor) {
		this.consumidor = consumidor;
	}

	@Override
	public String getLabel() {
		return this.nome;
	}

	@Override
	public void validate() throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	public Date getRealizadoEm() {
		return realizadoEm;
	}

	public void setRealizadoEm(Date realizadoEm) {
		this.realizadoEm = realizadoEm;
	}
}