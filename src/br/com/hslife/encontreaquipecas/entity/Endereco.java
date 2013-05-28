package br.com.hslife.encontreaquipecas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.hslife.encontreaquipecas.exception.BusinessException;

@Entity
@Table(name="endereco")
@SuppressWarnings("serial")
public class Endereco extends EntityPersistence {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=70, nullable=false)
	private String tipoLogradouro;
	
	@Column(length=200, nullable=false)
	private String logradouro;
	
	@Column(length=100, nullable=true)
	private String complemento;
	
	@Column(length=10, nullable=false)
	private String numero;
	
	@Column(length=50, nullable=false)
	private String bairro;
	
	@Column(length=100, nullable=false)
	private String cidade;
	
	@Column(length=2, nullable=false)
	private String estado;
	
	@Column(length=8, nullable=false)
	private String cep;
	
	@Override
	public String getLabel() {
		StringBuilder textToString = new StringBuilder();
		textToString.append(tipoLogradouro + " ");
		textToString.append(logradouro + ", ");
		textToString.append(numero + " - ");
		if (complemento != null && !complemento.trim().isEmpty()) {
			textToString.append(complemento + " - ");
		}
		textToString.append(bairro + " - ");
		textToString.append(cidade + ", ");
		textToString.append(estado + " -  ");
		textToString.append("CEP: " + cep);
		return textToString.toString();
	}
	
	@Override
	public void validate() throws BusinessException {
		if (this.tipoLogradouro == null || this.tipoLogradouro.trim().isEmpty()) {
			throw new BusinessException("Informe o tipo de logradouro!");
		}
		
		if (this.tipoLogradouro.length() > 70) {
			throw new BusinessException("Tipo de logradouro deve ser menor que 70 caracteres!");
		}
		
		if (this.logradouro == null || this.logradouro.trim().isEmpty()) {
			throw new BusinessException("Informe o logradouro!");
		}
		
		if (this.logradouro.length() > 200) {
			throw new BusinessException("Logradouro deve ser menor que 200 caracteres!");
		}
		
		if (this.complemento != null && this.complemento.length() > 100) {
			throw new BusinessException("Complemento deve ser menor que 100 caracteres!");
		}
		
		if (this.numero == null || this.numero.trim().isEmpty()) {
			throw new BusinessException("Informe o número!");
		}
		
		if (this.numero.length() > 10) {
			throw new BusinessException("Número deve ser menor que 10 caracteres!");
		}
		
		if (this.bairro == null || this.bairro.trim().isEmpty()) {
			throw new BusinessException("Informe o bairro!");
		}
		
		if (this.bairro.length() > 50) {
			throw new BusinessException("Bairro deve ser menor que 50 caracteres!");
		}
		
		if (this.cidade == null || this.cidade.trim().isEmpty()) {
			throw new BusinessException("Informe a cidade!");
		}
		
		if (this.cidade.length() > 100) {
			throw new BusinessException("Cidade deve ser menor que 100 caracteres!");
		}
		
		if (this.estado == null || this.estado.trim().isEmpty()) {
			throw new BusinessException("Informe o estado!");
		}
		
		if (this.estado.length() > 2) {
			throw new BusinessException("Estado deve ser menor que 70 caracteres!");
		}
		
		if (this.cep == null || this.cep.trim().isEmpty()) {
			throw new BusinessException("Informe o CEP!");
		}
		
		if (this.cep.length() > 8) {
			throw new BusinessException("CEP deve ser menor que 8 caracteres!");
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
}