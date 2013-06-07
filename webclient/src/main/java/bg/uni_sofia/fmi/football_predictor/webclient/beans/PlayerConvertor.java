package bg.uni_sofia.fmi.football_predictor.webclient.beans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("football.player")
public class PlayerConvertor implements Converter {

	@Override
	public Player getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return new Player(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2.toString();
	}

}
