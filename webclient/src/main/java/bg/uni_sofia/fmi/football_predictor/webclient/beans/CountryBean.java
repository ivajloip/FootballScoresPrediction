package bg.uni_sofia.fmi.football_predictor.webclient.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

@ManagedBean(name = "teamManager1")
@SessionScoped
public class CountryBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String localeCode = "en"; // default value

	public void countryLocaleCodeChanged(ValueChangeEvent e) {
		// assign new value to localeCode
		localeCode = e.getNewValue().toString();

	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	static Logger log = Logger.getLogger(TeamManager.class.getName());

	public static class Team implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		public String name;

		public Team(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
        @Override
        public String toString() {
        	return name;
        }
	}

	public static class Player implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		public String name;

		public Player(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
        @Override
        public String toString() {
        	return name;
        }
	}

	private List<Team> teams;
	private List<Player> team1Players;
	private Team team1;
	private List<Player> team1SelectedPlayers;
	public String result = "Foo";

	public CountryBean() {
		teams = new ArrayList<Team>();
		team1Players = new ArrayList<Player>();
		Team team = new Team("Chavdar");
		teams.add(team);
		team = new Team("Fool");
		teams.add(team);
	}

	public Team getTeam1() {
		return team1;
	}

	public List<Team> getTeams() {
		log.error("Kur");
		System.out.println(teams);
		return teams;
	}

	public List<Player> getTeam1Players() {
		return team1Players;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public void setTeam1Players(List<Player> team1Players) {
		this.team1Players = team1Players;
	}

	public List<Player> getTeam1SelectedPlayers() {
		return team1SelectedPlayers;
	}

	public void setTeam1SelectedPlayers(List<Player> team1SelectedPlayers) {
		this.team1SelectedPlayers = team1SelectedPlayers;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void handleEvent(ValueChangeEvent event) {
		System.out.println("Alabala");
		Player player1 = new Player("Larodi");
		Player player2 = new Player("Mityo");

		team1Players.add(player1);
		team1Players.add(player2);
		result += "Bar";
	}

}