package bg.uni_sofia.fmi.football_predictor.core;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Entity
@Table(name = "game")
public class Game extends DataBaseObject implements Comparable<Game> {

	private Integer gameId;

	private Team homeTeam;
	private Team awayTeam;

	// final result
	private int scoreHome;
	private int scoreAway;
	// half time result
	private int scoreHomeHalf;
	private int scoreAwayHalf;
	// ball possession
	private int posHome;
	private int posAway;

	private Set<GamePlayer> homePlayers = new HashSet<GamePlayer>(0);
	private Set<GamePlayerAway> awayPlayers = new HashSet<GamePlayerAway>(0);
	// first year of the season 1996/97 -> 1996
	private int season;
	private Date matchDate;
	private String league;

	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}

	public Game() {

	}

	public Game(Team ht, Team at, int scoreH, int scoreA, int posH, int posA,
			Date date, String league, int season) {

		this.homeTeam = ht;
		this.awayTeam = at;
		this.scoreHome = scoreH;
		this.scoreAway = scoreA;
		this.posHome = posH;
		this.posAway = posA;
		this.matchDate = date;
		this.league = league;
		this.season = season;
	}

	@Column(name = "score_home_half", nullable = true)
	public int getScoreHomeHalf() {
		return scoreHomeHalf;
	}

	public void setScoreHomeHalf(int scoreHomeHalf) {
		this.scoreHomeHalf = scoreHomeHalf;
	}

	@Column(name = "score_away_half", nullable = true)
	public int getScoreAwayHalf() {
		return scoreAwayHalf;
	}

	public void setScoreAwayHalf(int scoreAwayHalf) {
		this.scoreAwayHalf = scoreAwayHalf;
	}

	@Column(name = "season", nullable = true)
	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.game", cascade = CascadeType.ALL)
	public Set<GamePlayer> getHomePlayers() {
		return homePlayers;
	}

	public void setHomePlayers(Set<GamePlayer> homePlayers) {
		this.homePlayers = homePlayers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.game", cascade = CascadeType.ALL)
	public Set<GamePlayerAway> getAwayPlayers() {
		return awayPlayers;
	}

	public void setAwayPlayers(Set<GamePlayerAway> awwayPlayers) {
		this.awayPlayers = awwayPlayers;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awwayTeam) {
		this.awayTeam = awwayTeam;
	}

	@Column(name = "score_home", nullable = true)
	public int getScoreHome() {
		return scoreHome;
	}

	public void setScoreHome(int scoreHome) {
		this.scoreHome = scoreHome;
	}

	@Column(name = "score_away", nullable = true)
	public int getScoreAway() {
		return scoreAway;
	}

	public void setScoreAway(int scoreAway) {
		this.scoreAway = scoreAway;
	}

	@Column(name = "position_home", nullable = true)
	public int getPosHome() {
		return posHome;
	}

	public void setPosHome(int posHome) {
		this.posHome = posHome;
	}

	@Column(name = "position_away", nullable = true)
	public int getPosAway() {
		return posAway;
	}

	public void setPosAway(int posAway) {
		this.posAway = posAway;
	}

	@Override
	public String makeSelectStatement() {
		return "from Game g where g.homeTeam = '" + this.homeTeam.getTeamId()
				+ "' and g.matchDate = '" + this.matchDate + "'";
	}

	@Override
	public boolean equals(Object obj) {
		Game g = (Game) obj;
		return this.getHomeTeam().equals(g.getHomeTeam())
				&& this.getMatchDate().equals(g.getMatchDate());
	}

	@Override
	public Criteria createCriteria(Criteria criteria) {
		criteria.add(Restrictions.eq("homeTeam", this.homeTeam));
		criteria.add(Restrictions.eq("awayTeam", this.awayTeam));
		criteria.add(Restrictions.eq("matchDate", this.matchDate));
		return criteria;
	}

	@Id
	@GeneratedValue
	@Column(name = "GAME_ID", unique = true, nullable = false)
	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	@Column(name = "league", nullable = true)
	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	@Override
	public int compareTo(Game o) {
		// TODO Auto-generated method stub
		return this.getMatchDate().compareTo(o.getMatchDate());
	}

	public int sign() {
		if (getScoreHome() > getScoreAway())
			return 0;
		if (getScoreHome() == getScoreAway())
			return 1;
		else
			return 2;
	}
}
