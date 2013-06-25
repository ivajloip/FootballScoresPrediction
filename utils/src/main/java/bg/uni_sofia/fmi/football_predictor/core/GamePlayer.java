package bg.uni_sofia.fmi.football_predictor.core;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.Criteria;

@Entity
@Table(name = "game_player")
@AssociationOverrides({
		@AssociationOverride(name = "pk.game", joinColumns = @JoinColumn(name = "GAME_ID")),
		@AssociationOverride(name = "pk.player", joinColumns = @JoinColumn(name = "PLAYER_ID")) })
public class GamePlayer extends DataBaseObject implements Serializable {

	public GamePlayer() {
	}
	
	private GamePlayerId pk = new GamePlayerId();
	private int goals;

	@EmbeddedId
	public GamePlayerId getPk() {
		return pk;
	}

	public void setPk(GamePlayerId pk) {
		this.pk = pk;
	}

	@javax.persistence.Transient
	public Game getGame() {
		return getPk().getGame();
	}
	
	public void setGame(Game stock) {
		getPk().setGame(stock);
	}

	@javax.persistence.Transient
	public Player getPlayer() {
		return getPk().getPlayer();
	}

	public void setPlayer(Player category) {
		getPk().setPlayer(category);
	}
	
	@Column(name = "goals", nullable = true, length = 10)
	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}
	
	public void addGoals(){
		this.goals++;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		GamePlayer that = (GamePlayer) o;

		if (getPk() != null ? !getPk().equals(that.getPk())
				: that.getPk() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}

	@Override
	public String makeSelectStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria createCriteria(Criteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
