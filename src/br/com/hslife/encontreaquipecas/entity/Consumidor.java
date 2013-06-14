package br.com.hslife.encontreaquipecas.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.hslife.encontreaquipecas.exception.BusinessException;

@Entity
@Table(name="consumidor")
@SuppressWarnings("serial")
public class Consumidor extends EntityPersistence {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idEndereco")
	private Endereco endereco;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Telefone> telefones;
	
	public Consumidor() {
		usuario = new Usuario();
		endereco = new Endereco();
		telefones = new ArrayList<Telefone>();
	}
	
	@Override
	public String getLabel() {
		return this.usuario.getLabel();
	}
	
	@Override
	public void validate() throws BusinessException {
		if (endereco == null) {
			throw new BusinessException("Informe o endereço!");
		}
		
		if (usuario == null) {
			throw new BusinessException("Informe o usuário!");
		}
		
		if (telefones == null || telefones.size() == 0) {
			throw new BusinessException("Informe pelo menos um telefone!");
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
}