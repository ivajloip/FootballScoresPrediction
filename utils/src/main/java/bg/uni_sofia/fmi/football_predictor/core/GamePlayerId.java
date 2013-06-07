package bg.uni_sofia.fmi.football_predictor.core;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class GamePlayerId implements Serializable {
	public GamePlayerId() {
	}

	private Game game;
	private Player player;

	@ManyToOne
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@ManyToOne
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		GamePlayerId that = (GamePlayerId) o;

		if (game != null ? !game.equals(that.game) : that.game != null)
			return false;
		if (player != null ? !player.equals(that.player) : that.player != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (game != null ? game.hashCode() : 0);
		result = 31 * result + (player != null ? player.hashCode() : 0);
		return result;
	}
}
