package bg.uni_sofia.fmi.football_predictor.webclient.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

@SuppressWarnings("serial")
@ManagedBean(name = "teamManager")
@ViewScoped
public class TeamManager implements Serializable {
    static Logger log = Logger.getLogger(TeamManager.class.getName());
    
    private List<Team> teams;
    
    private Team team1;
    private List<Player> team1Players;
    private List<Player> team1SelectedPlayers;
    
//    public String[] team2SelectedPlayers;
    
    private List<Player> team2Players;
    private Team team2;
    private List<Player> team2SelectedPlayers;
    
    public String result = "Foo";

    public TeamManager() {
        teams = new ArrayList<Team>();
        team1Players = new ArrayList<Player>();
        team2Players = new ArrayList<Player>();
        
        Team team = new Team("Chavdar");
        teams.add(team);
        team = new Team("Fool");
        teams.add(team);
    }

    public List<Team> getTeams() {
        System.out.println(teams);
        return teams;
    }
    
    public void setTeams(List<Team> teams) {
    	this.teams = teams;
    }

    public Team getTeam1() {
      return team1;
    }
    
    public void setTeam1(Team team1) {
        System.out.println("larodi");
//        this.team1 = new Team(team1);
    }

    public List<Player> getTeam1Players() {
        return team1Players;
    }

    public void setTeam1Players(List<Player> team1Players) {
        this.team1Players = team1Players;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public List<Player> getTeam2Players() {
		return team2Players;
	}

	public void setTeam2Players(List<Player> team2Players) {
		this.team2Players = team2Players;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public void changeEvent(AjaxBehaviorEvent event) {
    	System.out.println("Alabala");
        Player player1 = new Player("Larodi");
        Player player2 = new Player("Mityo");

        team1Players.add(player1);
        team1Players.add(player2);
        result += "Bar";
    }
	
	public void changeEvent2(AjaxBehaviorEvent event) {
    	System.out.println("Alabala2");
        Player player1 = new Player("Larodi2");
        Player player2 = new Player("Mityo2");

        team2Players.add(player1);
        team2Players.add(player2);
        result += "Bar";
    }
	
	public void computeResult(AjaxBehaviorEvent event) {
		System.out.println("Computing result");
		result += " Result";
	}

	public List<Player> getTeam1SelectedPlayers() {
		return team1SelectedPlayers;
	}

	public void setTeam1SelectedPlayers(List<Player> team1SelectedPlayers) {
		this.team1SelectedPlayers = team1SelectedPlayers;
	}
	
	public List<Player> getTeam2SelectedPlayers() {
		return team2SelectedPlayers;
	}

	public void setTeam2SelectedPlayers(
			List<Player> team2SelectedPlayers) {
		this.team2SelectedPlayers = team2SelectedPlayers;
	}
}
