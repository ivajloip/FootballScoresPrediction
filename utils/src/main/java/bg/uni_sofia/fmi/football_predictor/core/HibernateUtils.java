package bg.uni_sofia.fmi.football_predictor.core;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

	// Save object into database
	public static DataBaseObject save(DataBaseObject object) {
		SessionFactory sf = buildSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		Long id = (Long) session.save(object);
		object.setId(id);
		session.getTransaction().commit();
		session.close();

		return object;
	}

	// Test...
	public static void main(String[] args) {
		Game game = new Game();
		Team team = new Team();
		team.setCountry("Bulgaria");
		team.setName("Levski");
		game.setScoreAway(3);
		game.setScoreHome(2);
		game = (Game) save(game);
		team = (Team) save(team);
	}
}