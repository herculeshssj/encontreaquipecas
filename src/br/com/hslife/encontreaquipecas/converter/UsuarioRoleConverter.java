package br.com.hslife.encontreaquipecas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.hslife.encontreaquipecas.enumeration.TipoUsuario;

@FacesConverter(value="usuarioRoleConverter")
public class UsuarioRoleConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		try {
			if (valor.equalsIgnoreCase("Administrador")) 
				return TipoUsuario.ROLE_ADMIN;
			else if (valor.equalsIgnoreCase("Loja")) 
				return TipoUsuario.ROLE_STORE;
			else
				return TipoUsuario.ROLE_USER;			
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object valor) {
		try {
			if ( ((TipoUsuario)valor).equals(TipoUsuario.ROLE_ADMIN)) {
				return "Administrador";
			} else if ( ((TipoUsuario)valor).equals(TipoUsuario.ROLE_STORE)) {
				return "Loja";	
			} else {
				return "Usuário";
			}
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
