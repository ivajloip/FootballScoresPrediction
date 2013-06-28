package bg.uni_sofia.fmi.football_predictor.webclient.beans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import bg.uni_sofia.fmi.football_predictor.core.Team;

@FacesConverter("football.team")
public class TeamConvertor implements Converter {

	@Override
	public Team getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		return new Team(value, "England");
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2.toString();
	}

}
