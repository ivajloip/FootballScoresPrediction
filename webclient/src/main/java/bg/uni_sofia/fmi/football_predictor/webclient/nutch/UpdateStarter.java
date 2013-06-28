package bg.uni_sofia.fmi.football_predictor.webclient.nutch;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager = true)
@ApplicationScoped
public class UpdateStarter {

	private ScheduledExecutorService scheduler;

	@PostConstruct
	public void init() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
//		scheduler.scheduleAtFixedRate(new NutchRunner(), 0, 4, TimeUnit.HOURS);
	}

	@PreDestroy
	public void destroy() {
		scheduler.shutdownNow();
	}

}
