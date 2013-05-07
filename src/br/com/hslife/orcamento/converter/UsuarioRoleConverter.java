package br.com.hslife.orcamento.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.hslife.orcamento.enumeration.TipoUsuario;

@FacesConverter(value="usuarioRoleConverter")
public class UsuarioRoleConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		try {
			if (valor.equalsIgnoreCase("Administrador")) 
				return TipoUsuario.ROLE_ADMIN;
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
			} else {
				return "Usuário";
			}
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
