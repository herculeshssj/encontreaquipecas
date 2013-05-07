package br.com.hslife.orcamento.entity;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import br.com.hslife.orcamento.exception.BusinessException;

@SuppressWarnings("serial")
public abstract class EntityPersistence implements Serializable {
	
	public abstract Long getId();
	
	public abstract String getLabel();
	
	public abstract void validate() throws BusinessException;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Long.signum(getId() ^ (getId() >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getId() == null) return false;
		if (getClass() != obj.getClass()) return false;

		EntityPersistence other = (EntityPersistence) obj;
		if (!getId().equals(other.getId())) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[" + this.getId() + "]";
	}
	
	public Map<String, String> getFieldValues() {
		Map<String, String> camposClasse = new HashMap<String, String>();
		try {
			for (Method method : this.getClass().getDeclaredMethods()) {
				if (method.getName().substring(0, 2).equals("is") || method.getName().substring(0, 3).equals("get")) {
					
					// Extrai o nome do atributo a partir no nome do método get ou is
					String nomeAtributo = "";
					if (method.getName().substring(0, 2).equals("is")) {
						nomeAtributo = method.getName().substring(2);
					} else {
						nomeAtributo = method.getName().substring(3);
					}
					
					// Extrai o valor do método Getter
					Object valorAtributo = method.invoke(this);
					
					if (valorAtributo == null)					
						camposClasse.put(nomeAtributo, "null");
					else
						camposClasse.put(nomeAtributo, valorAtributo.toString());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return camposClasse;
	}
}
