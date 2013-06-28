package bg.uni_sofia.fmi.football_predictor.webclient.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import bg.uni_sofia.fmi.football_predictor.core.DataBaseObject;
import bg.uni_sofia.fmi.football_predictor.core.Team;
import bg.uni_sofia.fmi.football_predictor.core.HibernateUtils;
import bg.uni_sofia.fmi.football_predictor.core.solr.SolrQueryResponse;
import bg.uni_sofia.fmi.football_predictor.core.solr.SolrQueryUtil;
import bg.uni_sofia.fmi.football_predictor.core.Player;
import bg.uni_sofia.fmi.football_predictor.engine.Main;

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
    
    public String result = "";
    
    private HashMap<String, Collection<SolrQueryResponse>> news;

    public TeamManager() {
        teams = new ArrayList<Team>();
        team1Players = new ArrayList<Player>();
        team2Players = new ArrayList<Player>();
        news = new HashMap<String, Collection<SolrQueryResponse>>();
        
        Collection<DataBaseObject> databaseTeams =
        		HibernateUtils.getAllEntities(Team.class);
        
        for (DataBaseObject team : databaseTeams) {
        	teams.add((Team) team);
        }
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
        this.team1 = team1;
        
        team1Players.clear();
        for (Player player : HibernateUtils.getPlayers(team1)) {
        	team1Players.add(player);
        }
        
        Collection<SolrQueryResponse> teamNews = findNews(team1.getName());
        news.put(team1.getName(), teamNews);
    }

    private Collection<SolrQueryResponse> findNews(String name) {
    	try {
			Collection<SolrQueryResponse> news = SolrQueryUtil.find(name);
			return news;
    	} catch (Exception ex) {
    		log.error(ex);
    	}
    	
    	return new ArrayList<SolrQueryResponse>();
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
		
        team2Players.clear();
        for (Player player : HibernateUtils.getPlayers(team2)) {
        	team2Players.add(player);
        }
		
        Collection<SolrQueryResponse> teamNews = findNews(team2.getName());
        news.put(team2.getName(), teamNews);
	}

	public void changeEvent(AjaxBehaviorEvent event) {
    }
	
	public void changeEvent2(AjaxBehaviorEvent event) {
    }
	
	public void computeResult(AjaxBehaviorEvent event) {
		System.out.println("Computing result");
		result = "" +
			Main.guess(team1, team2, team1SelectedPlayers, team2SelectedPlayers);
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
	
	public Collection<SolrQueryResponse> getNews() {
		Collection<SolrQueryResponse> result =
				new ArrayList<SolrQueryResponse>();
		
		for(Collection<SolrQueryResponse> newsSet : news.values()) {
			result.addAll(newsSet);
		}
		
		return result;
	}
	
	public String getFormatedString(String value) {
		int maxLength = 50;
		if (value.length() < maxLength) {
			return value;
		}
		
		return value.substring(maxLength);
	}
}