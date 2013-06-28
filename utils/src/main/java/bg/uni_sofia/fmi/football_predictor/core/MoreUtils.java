package bg.uni_sofia.fmi.football_predictor.core;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class MoreUtils {

	public static void main(String[] args) {
		Session session = HibernateUtils.sessionFactory.openSession();
		session.beginTransaction();
		Team t = (Team) HibernateUtils.getAllEntities(Team.class).get(0);
		Criteria criteria = session.createCriteria(Game.class);
		criteria.add(Restrictions.eq("season", 2009));
		criteria.add(Restrictions.eq("homeTeam", t));
		List<DataBaseObject> results = criteria.list();
		Game game = (Game) results.get(8);
		kyr(game);
		// System.out.println(results.size());
		session.close();

	}

	public static List<Game> getTeamGamesPerSeason(int season, Team team) {
		List<Game> results = new ArrayList<Game>();
		Session session = HibernateUtils.sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Game.class);
		criteria.add(Restrictions.eq("season", season));
		criteria.add(Restrictions.eq("homeTeam", team));
		List tmp = criteria.list();
		for (int i = 0; i < tmp.size(); i++)
			results.add((Game) tmp.get(i));
		Criteria criteria2 = session.createCriteria(Game.class);
		criteria2.add(Restrictions.eq("season", season));
		criteria2.add(Restrictions.eq("awayTeam", team));
		List tmp2 = criteria2.list();
		for (int i = 0; i < tmp2.size(); i++)
			results.add((Game) tmp2.get(i));
		session.close();
		return results;
	}

	public static List<Game> getGamesPerSeason(int season) {
		List<Game> results = new ArrayList<Game>();
		Session session = HibernateUtils.sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Game.class);
		criteria.add(Restrictions.eq("season", season));
		List tmp = criteria.list();
		for (int i = 0; i < tmp.size(); i++)
			results.add((Game) tmp.get(i));
		session.close();
		return results;
	}

	public static List getHomeGames(Team team, int season, Date date) {
		List results = new ArrayList<Game>();
		Session session = HibernateUtils.sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Game.class);
		criteria.add(Restrictions.eq("season", season));
		criteria.add(Restrictions.eq("homeTeam", team));
		criteria.add(Restrictions.le("matchDate", date));
		List<DataBaseObject> tmp = criteria.list();
		for (int i = 0; i < tmp.size(); i++)
			results.add((Game) tmp.get(i));
		session.close();
		Collections.sort(results);
		return results;
	}

	public static List getAwayGames(Team team, int season, Date date) {
		List results = new ArrayList<Game>();
		Session session = HibernateUtils.sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Game.class);
		criteria.add(Restrictions.eq("season", season));
		criteria.add(Restrictions.eq("awayTeam", team));
		criteria.add(Restrictions.le("matchDate", date));
		List<DataBaseObject> tmp = criteria.list();
		for (int i = 0; i < tmp.size(); i++)
			results.add((Game) tmp.get(i));
		session.close();
		Collections.sort(results);
		return results;
	}

	public static List getAllGamesThisSeason(Team team, int season, Date date) {
		List result = getAwayGames(team, season, date);
		result.addAll(getHomeGames(team, season, date));
		return result;
	}

	public static List getInBetweenGames(Team t1, Team t2, Date date) {
		List results = new ArrayList<Game>();
		Session session = HibernateUtils.sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Game.class);
		criteria.add(Restrictions.eq("homeTeam", t1));
		criteria.add(Restrictions.eq("awayTeam", t2));
		criteria.add(Restrictions.le("matchDate", date));
		List<DataBaseObject> tmp = criteria.list();
		for (int i = 0; i < tmp.size(); i++)
			results.add((Game) tmp.get(i));
		session.close();
		Collections.sort(results);
		return results;
	}

	public static List getInBetweenReverseGames(Team t1, Team t2, Date date) {
		List results = new ArrayList<Game>();
		Session session = HibernateUtils.sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Game.class);
		criteria.add(Restrictions.eq("homeTeam", t2));
		criteria.add(Restrictions.eq("awayTeam", t1));
		criteria.add(Restrictions.le("matchDate", date));
		List<DataBaseObject> tmp = criteria.list();
		for (int i = 0; i < tmp.size(); i++)
			results.add((Game) tmp.get(i));
		session.close();
		Collections.sort(results);
		return results;
	}

	public static void kyr(Game game) {
		System.out.println(game.getHomeTeam().getName());
		System.out.println(game.getAwayTeam().getName());
		System.out.println(game.getSeason());
		System.out.println(game.getMatchDate());
		System.out.println("========================================");
		getThisSeasonPreviousGames(game.getAwayTeam(), game.getSeason(),
				game.getMatchDate());
	}

	public static void getThisSeasonPreviousGames(Team team, int season,
			Date date) {
		Session session = HibernateUtils.sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Game.class);
		criteria.add(Restrictions.eq("season", season));
		criteria.add(Restrictions.eq("homeTeam", team));
		criteria.add(Restrictions.ge("matchDate", date));
		List<DataBaseObject> results = criteria.list();
		System.out.println(results.size());
		for (DataBaseObject game : results) {
			System.out.println(((Game) game).getHomeTeam().getName());
			System.out.println(((Game) game).getAwayTeam().getName());
			System.out.println(((Game) game).getSeason());
			System.out.println(((Game) game).getMatchDate());
			System.out.println();
		}
		session.close();
	}

}
