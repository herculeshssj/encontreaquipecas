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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.hslife.encontreaquipecas.enumeration.TipoTelefone;
import br.com.hslife.encontreaquipecas.exception.BusinessException;

@Entity
@Table(name="telefone")
@SuppressWarnings("serial")
public class Telefone extends EntityPersistence {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=5, nullable=false)
	private String ddd;
	
	@Column(length=10, nullable=false)
	private String numero;
	
	@Column(length=10)
	@Enumerated(EnumType.STRING)
	private TipoTelefone tipoTelefone;
	
	@Override
	public String getLabel() {
		StringBuilder textToString = new StringBuilder();
		textToString.append("(" + ddd + ") ");
		textToString.append(numero + ", ");
		return textToString.toString();
	}
	
	@Override
	public void validate() throws BusinessException {
		if (this.ddd == null || this.ddd.trim().isEmpty()) {
			throw new BusinessException("Informe o DDD!");
		}
		
		if (this.ddd.length() > 5) {
			throw new BusinessException("DDD deve ser menor que 5 caracteres!");
		}
		
		if (this.numero == null || this.numero.trim().isEmpty()) {
			throw new BusinessException("Informe o n�mero!");
		}
		
		if (this.numero.length() > 10) {
			throw new BusinessException("N�mero deve ser menor que 10 caracteres!");
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}
}