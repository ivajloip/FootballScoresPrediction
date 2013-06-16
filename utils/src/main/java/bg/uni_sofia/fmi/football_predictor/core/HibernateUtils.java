package bg.uni_sofia.fmi.football_predictor.core;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtils {

	private static SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistryBuilder
						.buildServiceRegistry());

		return sessionFactory;
	}

	private static SessionFactory sessionFactory = buildSessionFactory();

	// Save object into database
	public static DataBaseObject save(DataBaseObject object, Session session) {
		DataBaseObject result = get(object, session);
		if (result != null)
			return result;
		// session.beginTransaction();

		Integer id = (Integer) session.save(object);
		if (object instanceof Player)
			((Player) object).setPlayerId(id);
		if (object instanceof Game)
			((Game) object).setGameId(id);
		if (object instanceof Team)
			((Team) object).setTeamId(id);
		// session.getTransaction().commit();
		return object;
	}

	public static DataBaseObject get(DataBaseObject object, Session session) {

		// session.beginTransaction();
		// Query query = session.createQuery(object.getSelectStatement());
		Criteria criteria = session.createCriteria(object.getClass());
		object.createCriteria(criteria);
		List results = criteria.list();
		if (results.isEmpty())
			return null;
		return (DataBaseObject) results.get(0);// if there are more than
												// one...destiny
	}

	public static List<DataBaseObject> getAllEntities(Class c) {
		SessionFactory sf = buildSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(c);
		List<DataBaseObject> results = criteria.list();
		session.close();
		return results;
	}

	private static int count = 0;

	public static void save(Match match) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			match.away = (Team) save(match.away, session);

			match.home = (Team) save(match.home, session);

			match.game.setAwayTeam(match.away);
			match.game.setHomeTeam(match.home);

			for (Player player : match.awayPlayers) {
				player = (Player) save(player, session);
				GamePlayer gp = new GamePlayer();
				gp.setGame(match.game);
				gp.setPlayer(player);
				match.game.getAwayPlayers().add(gp);
			}
			for (Player player : match.homePlayers) {
				player = (Player) save(player, session);
				GamePlayer gp = new GamePlayer();
				gp.setGame(match.game);
				gp.setPlayer(player);
				match.game.getHomePlayers().add(gp);
			}
			match.game = (Game) save(match.game, session);
			session.getTransaction().commit();
		} catch (Exception e) {
			
			count++;
			System.out.println(count);
		} finally {
			session.close();
		}
	}

	// Test...
	public static void main(String[] args) {
		//
		// // SessionFactory sessionFactory = new AnnotationConfiguration()
		// // .configure().buildSessionFactory();
		// // Session session = sessionFactory.openSession();
		// // Transaction transaction = null;
		// //
		// // transaction = session.beginTransaction();
		//
		// Team inter = new Team("Inter", "Italy");
		// Team parma = new Team("Parma", "Italy");
		// Player sanetti = new Player("Sanetti");
		// Player maradona = new Player("Maradona");
		// inter = (Team) save(inter);
		// parma = (Team) save(parma);
		// sanetti = (Player) save(sanetti);
		// maradona = (Player) save(maradona);
		// GamePlayer gp1 = new GamePlayer();
		// GamePlayer gp5 = new GamePlayer();
		// Game g = new Game(inter, parma, 3, 2, 50, 50, new Date(1),
		// "Premier League", 20003);
		//
		// gp1.setPlayer(sanetti);
		// gp1.setGame(g);
		// gp1.setGoals(4);
		//
		// gp5.setPlayer(maradona);
		// gp5.setGame(g);
		// gp5.setGoals(4);
		//
		// g.getAwayPlayers().add(gp1);
		// g.getAwayPlayers().add(gp5);
		//
		// gp1.setPlayer(sanetti);
		// gp1.setGame(g);
		// gp1.setGoals(4);
		//
		// g.getHomePlayers().add(gp1);
		// g.getHomePlayers().add(gp5);
		//
		// g = (Game) save(g);
		//
		// Team levski = new Team("Levski", "Bulgaria");
		// Team milan = new Team("Milan", "Italy");
		// Player messi = new Player("Leonel Messi", "Argentina", 1986);
		// Player gattuso = new Player("Gennaro Gattuso", "Italy", 1978);
		// Player shevchenko = new Player("Andriy Shevchenko", "Ukraine", 1976);
		// // Player yovov = new Player("yovov",);
		// Player kaka = new Player("Kaka", "Brazil", 1982);
		//
		// kaka = (Player) save(kaka);
		// shevchenko = (Player) save(shevchenko);
		// gattuso = (Player) save(gattuso);
		// messi = (Player) save(messi);
		// levski = (Team) save(levski);
		// milan = (Team) save(milan);
		// GamePlayer gp = new GamePlayer();
		// GamePlayer gp2 = new GamePlayer();
		// GamePlayer gp3 = new GamePlayer();
		// GamePlayer gp4 = new GamePlayer();
		// Game game1 = new Game(milan, levski, 0, 5, 40, 60, new Date(1),
		// "Premier League", 1999);
		// Game game3 = new Game(milan, milan, 0, 5, 40, 60, new Date(123),
		// "Premier League", 1999);
		//
		// gp.setGame(game3);
		// gp.setPlayer(messi);
		// gp.setGoals(7);
		// gp2.setGame(game3);
		// gp2.setPlayer(kaka);
		// gp2.setGoals(7);
		// gp3.setGame(game3);
		// gp3.setPlayer(shevchenko);
		// gp3.setGoals(7);
		// gp4.setGame(game3);
		// gp4.setPlayer(gattuso);
		// gp4.setGoals(7);
		//
		// // game1.getAwayPlayers().add(gp);
		// game1.getHomePlayers().add(gp2);
		// game3.getAwayPlayers().add(gp3);
		// game3.getHomePlayers().add(gp4);
		// game3.getAwayPlayers().add(gp);
		// game3.getHomePlayers().add(gp);
		//
		// game1 = (Game) save(game1);
		// game3 = (Game) save(game3);
		// System.out.println(game1.getGameId());
		// System.out.println(game1.getGameId());

		// transaction.commit();
		//
		// List<DataBaseObject> players = getAllEntities(Player.class);
		// List<DataBaseObject> teams = getAllEntities(Team.class);
		// for (DataBaseObject player : players)
		// System.out.println(((Player) player).getCountry());
		// for (DataBaseObject team : teams)
		// System.out.println(((Team) team).getCountry());
		// session.close();
	}
}