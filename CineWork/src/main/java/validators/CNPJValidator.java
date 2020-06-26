package validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import model.Empresa;

@FacesValidator("cnpjValidator")
public class CNPJValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		Empresa empresa = (Empresa) ((HttpSession) context.getExternalContext().getSession(false)).getAttribute("empresaLogada");
	}

}
