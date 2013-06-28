package bg.uni_sofia.fmi.football_predictor.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bg.uni_sofia.fmi.football_predictor.core.Game;
import bg.uni_sofia.fmi.football_predictor.core.HibernateUtils;
import bg.uni_sofia.fmi.football_predictor.core.MoreUtils;
import bg.uni_sofia.fmi.football_predictor.core.Player;
import bg.uni_sofia.fmi.football_predictor.core.Team;

public class Main {

	private static NeuralNetwork network = new NeuralNetwork(8, 3, 10, 0.1);
	private static List<Game> games = new ArrayList<Game>();

	public static double getPercantageWin(List<Game> games, Team team) {
		int numGames = games.size();
		if (games.isEmpty())
			return (1.33 / 3) ;
		else {
			int points = 0;
			for (int i = 0; i < numGames; i++)
				points += getPointsFromGame(games.get(i), team);
			return ((double) points / (numGames * 3)) ;
		}
	}

	public static int getPointsFromGame(Game game, Team team) {
		int home = game.getScoreHome();
		int away = game.getScoreAway();
		if (game.getHomeTeam().equals(team)) {
			if (home > away)
				return 3;
			if (home == away)
				return 1;
			else
				return 0;
		} else {
			if (home < away)
				return 3;
			if (home == away)
				return 1;
			else
				return 0;
		}
	}

	private static double[] prepareGame(Game game) {
		double[] res = new double[8];
		Team home = game.getHomeTeam();
		Team away = game.getAwayTeam();
		List curHomeHames = MoreUtils.getAllGamesThisSeason(home,
				game.getSeason(), game.getMatchDate());
		List curAwayHames = MoreUtils.getAllGamesThisSeason(away,
				game.getSeason(), game.getMatchDate());
		List prevHomeSeasonGames = MoreUtils.getTeamGamesPerSeason(
				game.getSeason() - 1, home);
		List prevAwaySeasonGames = MoreUtils.getTeamGamesPerSeason(
				game.getSeason() - 1, away);
		List pprevHomeSeasonGames = MoreUtils.getTeamGamesPerSeason(
				game.getSeason() - 2, home);
		List pprevAwaySeasonGames = MoreUtils.getTeamGamesPerSeason(
				game.getSeason() - 2, away);
		Collections.sort(curHomeHames);
		Collections.sort(curAwayHames);
		// Game LastHome = (Game) curHomeHames.get(curHomeHames.size() - 2);
		// Game LastAway = (Game) curAwayHames.get(curAwayHames.size() - 2);
		int homepoinetr = Math.max(curHomeHames.size() - 6, 0);
		int awaypoinetr = Math.max(curAwayHames.size() - 6, 0);
		List last5home;
		List last5away;
		if (curHomeHames.size() == 1)
			last5home = new ArrayList();
		else
			last5home = curHomeHames.subList(homepoinetr,
					curHomeHames.size() - 2);
		if (curAwayHames.size() == 1)
			last5away = new ArrayList();
		else
			last5away = curAwayHames.subList(awaypoinetr,
					curAwayHames.size() - 2);
		// List last3home = curHomeHames.subList(curHomeHames.size() - 4,
		// curHomeHames.size() - 2);
		// List last3away = curAwayHames.subList(curAwayHames.size() - 4,
		// curAwayHames.size() - 2);
		// System.out.println(prevHomeSeasonGames.size());
		// System.out.println(game.getHomeTeam().getName() + "  vs "
		// + game.getAwayTeam().getName());

		res[0] = getPercantageWin(prevHomeSeasonGames, home);
		res[1] = getPercantageWin(pprevHomeSeasonGames, home);
		res[2] = getPercantageWin(prevAwaySeasonGames, away);
		res[3] = getPercantageWin(pprevAwaySeasonGames, away);
		res[4] = getPercantageWin(curHomeHames, home);
		res[5] = getPercantageWin(curAwayHames, away);
		// res[6] = getPointsFromGame(LastAway, away);
		// res[7] = getPointsFromGame(LastHome, home);
		res[6] = getPercantageWin(last5away, away);
		res[7] = getPercantageWin(last5home, home);
		// res[8] = getPercantageWin(last3away, away);
		// res[9] = getPercantageWin(last3home, home);
		// System.out.println("prev percantage home win  :" + res[0]);
		// System.out.println("prev percantage away win  :" + res[2]);
		// System.out.println(res[0] +" persenates " + res[1]);
		// res[2] = getPercantageWin(inBetgames, home);
		// res[3] = getPercantageWin(inBetRevGames, home);
		return res;
	}

	public static int guess(Team t1, Team t2, List<Player> home,
			List<Player> away) {

		int res = 0;
		t1 = (Team) HibernateUtils.getDBO(t1);
		t2 = (Team) HibernateUtils.getDBO(t2);
		Game game = new Game();
		game.setSeason(2012);
		game.setAwayTeam(t1);
		game.setHomeTeam(t2);
		game = (Game) HibernateUtils.getDBO(game);
		double[] a = prepareGame(game);

		FileInputStream fis;
		try {
			fis = new FileInputStream("C:\\Users\\stanev\\Desktop\\network.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			NeuralNetwork network = (NeuralNetwork) ois.readObject();
			res = network.guess(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;

	}

	public static void main(String[] args) throws IOException {
//		Team t1 = new Team("Liverpool", "England");
//		Team t2 = new Team("Manchester United", "England");
//		List l1 = null;
//		List l2 = null;
//		guess(t1, t2, l1, l2);
		 for (int s = 2003; s < 2012; s++) {
		 List games = MoreUtils.getGamesPerSeason(s);
		 Collections.sort(games);
		 for (int i = 0; i < games.size(); i++) {
		 Game game = (Game) games.get(i);
		 double[] values = prepareGame(game);
		 if (game.sign() != 0)
		 for (int j = 0; j < 100; j++)
		 network.teach(values, game.sign());
		 else
		 for (int j = 0; j < 50; j++)
		 network.teach(values, game.sign());
		 }
		 }
		
		 List testGames = MoreUtils.getGamesPerSeason(2012);
		 int correct = 0;
		 Collections.sort(testGames);
		 for (int i = 0; i < testGames.size(); i++) {
		 System.out.println(((Game) testGames.get(i)).getMatchDate());
		 Game game = (Game) testGames.get(i);
		 double[] values = prepareGame(game);
		 int guess = network.guess(values);
		 System.out.println(game.getHomeTeam().getName() + " : "
		 + game.getAwayTeam().getName() + " : " + guess);
		 if (guess == game.sign())
		 correct++;
		 }
		 System.out.println("dafuq");
		 System.out.println((double) correct / (testGames.size() - 0));
		
		 FileOutputStream fos = new
		 FileOutputStream("C:\\Users\\stanev\\Desktop\\network.ser");
		 ObjectOutputStream ous = new ObjectOutputStream(fos);
		 ous.writeObject(network);
	}
}
