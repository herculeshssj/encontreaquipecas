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
			throw new BusinessException("Informe a descri��o do produto!");
		}
		
		if (fabricante == null || fabricante.trim().isEmpty()) {
			throw new BusinessException("Informe o fabricante do produto!");
		}
		
		if (modelo == null || modelo.trim().isEmpty()) {
			throw new BusinessException("Informe o modelo do produto!");
		}
		
		if (preco < 0) {
			throw new BusinessException("Pre�o n�o pode ser menor que 0!");
		}
		
		if (ano < 0) {
			throw new BusinessException("Ano n�o pode ser menor que 0!");
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