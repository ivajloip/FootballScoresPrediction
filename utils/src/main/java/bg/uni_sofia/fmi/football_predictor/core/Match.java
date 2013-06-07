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

	Game game;
	Team home;
	Team away;
	Set<Player> homePlayers;
	Set<Player> awayPlayers;
}
