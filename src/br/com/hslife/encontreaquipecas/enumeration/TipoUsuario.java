package br.com.hslife.encontreaquipecas.enumeration;

public enum TipoUsuario {
	ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");
	
	private String descricao;
	
	private TipoUsuario(String descricao) {
		this.descricao = descricao;
	}

	public String toString() {
		return descricao;
	}
}
