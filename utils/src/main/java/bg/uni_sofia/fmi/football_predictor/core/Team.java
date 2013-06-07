package bg.uni_sofia.fmi.football_predictor.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Entity
@Table(name = "team")
public class Team extends DataBaseObject {

	private Integer teamId;
	private String name;
	private String country;

	public Team() {

	}

	public Team(String name, String country) {
		this.name = name;
		this.country = country;
	}

	@Column(name = "country", nullable = false)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
		return "from Team t where t.name = '" + this.name
				+ "' and t.country = '" + this.country + "'";
	}

	@Override
	public boolean equals(Object obj) {
		Team t = (Team) obj;
		return t.getCountry().equals(this.getCountry())
				&& t.getName().equalsIgnoreCase(this.getName());
	}

	@Override
	public Criteria createCriteria(Criteria criteria) {
		criteria.add(Restrictions.eq("name", this.name));
		criteria.add(Restrictions.eq("country", this.country));
		return criteria;
	}

	@Id
	@GeneratedValue
	@Column(name = "TEAM_ID", unique = true, nullable = false)
	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
}
