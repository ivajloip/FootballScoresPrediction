package bg.uni_sofia.fmi.football_predictor.core;

import java.util.HashSet;
import java.util.Set;

public class Match {

	Match() {

		home = new Team();
		away = new Team();
		game = new Game();
		homePlayers = new HashSet<Player>(0);
		awayPlayers = new HashSet<Player>(0);
	}

	public Set<Player> getAwayPlayers() {
		return awayPlayers;
	}

	public Set<Player> getHomePlayers() {
		return homePlayers;
	}

	public void printHome() {
		for (Player player : homePlayers)
			System.out.println(player.getName());
	}

	public void printAway() {
		for (Player player : awayPlayers)
			System.out.println(player.getName());
	}

	Game game;
	Team home;
	Team away;
	Set<Player> homePlayers;
	Set<Player> awayPlayers;
}
