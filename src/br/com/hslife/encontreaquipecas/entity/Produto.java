package br.com.hslife.encontreaquipecas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.hslife.encontreaquipecas.exception.BusinessException;

@Entity
@Table(name="produto")
public class Produto extends EntityPersistence {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3083983823927814376L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String fabricante;
	
	@Column(nullable=false)
	private String modelo;
	
	@Column(nullable=false)
	private double preco;
	
	@Column(nullable=false)
	private String descricao;
	
	@Column(nullable=false)
	private int ano;
	
	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja loja;
	
	public Produto() {
		loja = new Loja();
	}

	@Override
	public String getLabel() {
		return this.nome;
	}
	
	@Override
	public void validate() throws BusinessException {
		if (loja == null) {
			throw new BusinessException("Informe a loja!");
		}
		
		if (nome == null || nome.trim().isEmpty()) {
			throw new BusinessException("Informe o nome do produto!");
		}
		
		if (descricao == null || descricao.trim().isEmpty()) {
			throw new BusinessException("Informe a descrição do produto!");
		}
		
		if (fabricante == null || fabricante.trim().isEmpty()) {
			throw new BusinessException("Informe o fabricante do produto!");
		}
		
		if (modelo == null || modelo.trim().isEmpty()) {
			throw new BusinessException("Informe o modelo do produto!");
		}
		
		if (preco < 0) {
			throw new BusinessException("Preço não pode ser menor que 0!");
		}
		
		if (ano < 0) {
			throw new BusinessException("Ano não pode ser menor que 0!");
		}
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

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}
}