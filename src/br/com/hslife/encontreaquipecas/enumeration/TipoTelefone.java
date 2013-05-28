package br.com.hslife.encontreaquipecas.enumeration;

public enum TipoTelefone {
	RESIDENCIAL("RESIDENCIAL"), COMERCIAL("COMERCIAL"), FAX("FAX"), OUTROS("OUTROS");
	
	private String descricao;
	
	private TipoTelefone(String descricao) {
		this.descricao = descricao;
	}

	public String toString() {
		return descricao;
	}
}
