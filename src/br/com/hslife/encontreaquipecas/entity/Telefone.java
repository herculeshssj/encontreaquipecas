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
			throw new BusinessException("Informe o número!");
		}
		
		if (this.numero.length() > 10) {
			throw new BusinessException("Número deve ser menor que 10 caracteres!");
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