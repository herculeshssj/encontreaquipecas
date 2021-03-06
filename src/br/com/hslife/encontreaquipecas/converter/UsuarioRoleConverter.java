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
				return "Usu�rio";
			}
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
