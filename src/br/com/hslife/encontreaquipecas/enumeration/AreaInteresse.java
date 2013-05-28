package br.com.hslife.encontreaquipecas.enumeration;

public enum AreaInteresse {
	PRODUTO("PRODUTO"), BANNER("BANNER");
	
	private String descricao;
	
	private AreaInteresse(String descricao) {
		this.descricao = descricao;
	}

	public String toString() {
		return descricao;
	}
}
