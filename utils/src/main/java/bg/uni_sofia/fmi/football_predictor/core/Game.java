package bg.uni_sofia.fmi.football_predictor.core;

import java.sql.Date;
import java.util.ArrayList;

public class Game extends DataBaseObject{

	private Team homeTeam;
	private Team awwayTeam;
	// final result
	private int scoreHome;
	private int scoreAway;
	// half time result
	private int scoreHomeHalf;
	private int scoreAwayHalf;
	// ball possession
	private int posHome;
	private int posAway;
	
	private ArrayList<Player> homePlayers = new ArrayList<Player>();
	private ArrayList<Player> awayPlayers = new ArrayList<Player>();
	// first year of the season 1996/97 -> 1996
	private int season;
	private Date matchDate;

	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}

	public Game() {

	}

	public Game(Team ht, Team at, int scoreH, int scoreA, int posH, int posA,
			Date date, ArrayList<Player> hp, ArrayList<Player> ap) {

		this.homeTeam = ht;
		this.awwayTeam = at;
		this.scoreHome = scoreH;
		this.scoreAway = scoreA;
		this.posHome = posH;
		this.posAway = posA;
		this.matchDate = date;
		this.homePlayers = hp;
		this.awayPlayers = ap;
	}
	
	public int getScoreHomeHalf() {
		return scoreHomeHalf;
	}

	public void setScoreHomeHalf(int scoreHomeHalf) {
		this.scoreHomeHalf = scoreHomeHalf;
	}

	public int getScoreAwayHalf() {
		return scoreAwayHalf;
	}

	public void setScoreAwayHalf(int scoreAwayHalf) {
		this.scoreAwayHalf = scoreAwayHalf;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public ArrayList<Player> getHomePlayers() {
		return homePlayers;
	}

	public void setHomePlayers(ArrayList<Player> homePlayers) {
		this.homePlayers = homePlayers;
	}

	public ArrayList<Player> getAwwayPlayers() {
		return awayPlayers;
	}

	public void setAwwayPlayers(ArrayList<Player> awwayPlayers) {
		this.awayPlayers = awwayPlayers;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team getAwwayTeam() {
		return awwayTeam;
	}

	public void setAwwayTeam(Team awwayTeam) {
		this.awwayTeam = awwayTeam;
	}

	public int getScoreHome() {
		return scoreHome;
	}

	public void setScoreHome(int scoreHome) {
		this.scoreHome = scoreHome;
	}

	public int getScoreAway() {
		return scoreAway;
	}

	public void setScoreAway(int scoreAway) {
		this.scoreAway = scoreAway;
	}

	public int getPosHome() {
		return posHome;
	}

	public void setPosHome(int posHome) {
		this.posHome = posHome;
	}

	public int getPosAway() {
		return posAway;
	}

	public void setPosAway(int posAway) {
		this.posAway = posAway;
	}
}
