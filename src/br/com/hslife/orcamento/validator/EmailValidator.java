package br.com.hslife.orcamento.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.hslife.orcamento.util.Util;

@FacesValidator(value="emailValidator")
public class EmailValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		 String email = "";
		 
		 if (value == null) return;
		 
		 if (value instanceof String) {
			 email = value.toString();
		 }
		 
		 if (!Util.validaEmail(email)) {
			 FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-Mail informado é inválido!", null);
			 throw new ValidatorException(mensagem);
		 }		
	}	
}
