package bg.uni_sofia.fmi.football_predictor.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Entity
@Table(name = "player")
public class Player extends DataBaseObject {

	private Integer playerId;
	private String name;
	private String country;
	private int yearBorn;
	private Set<GamePlayer> games = new HashSet<GamePlayer>();

	public Player() {
	}

	public Player(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.player")
	public Set<GamePlayer> getGames() {
		return games;
	}

	public void setGames(Set<GamePlayer> games) {
		this.games = games;
	}

	public Player(String name, String country, int yearBorn) {
		this.name = name;
		this.country = country;
		this.yearBorn = yearBorn;
	}

	@Column(name = "country", nullable = true)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "year_born", nullable = true)
	public int getYearBorn() {
		return yearBorn;
	}

	public void setYearBorn(int yearBorn) {
		this.yearBorn = yearBorn;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String makeSelectStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria createCriteria(Criteria a) {
		a.add(Restrictions.eq("name", this.name));
		if (this.country != null)
			a.add(Restrictions.eqOrIsNull("country", this.country));
		if (this.yearBorn != 0)
			a.add(Restrictions.eqOrIsNull("yearBorn", this.yearBorn));
		return a;
	}

	@Override
	public boolean equals(Object obj) {
		Player player = (Player) obj;
		return this.getName().equals(player.getName());
	}

	@Id
	@GeneratedValue
	@Column(name = "plaYER_ID", unique = true, nullable = false)
	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

}
