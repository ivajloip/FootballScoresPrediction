package bg.uni_sofia.fmi.football_predictor.webclient.beans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "playerValidator")
public class PlayerValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		value.toString();
//		String email = String.valueOf(value);
//		boolean valid = true;
//		if (value == null) {
//			valid = false;
//		} else if (!email.contains("@")) {
//			valid = false;
//		} else if (!email.contains(".")) {
//			valid = false;
//		} else if (email.contains(" ")) {
//			valid = false;
//		}
//		if (!valid) {
//			FacesMessage message = new FacesMessage(
//					FacesMessage.SEVERITY_ERROR, "Invalid email address",
//					"The email address you entered is not valid.");
//			throw new ValidatorException(message);
//		}
	}
}
