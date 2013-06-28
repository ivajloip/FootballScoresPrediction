package bg.uni_sofia.fmi.football_predictor.webclient.nutch;

import bg.uni_sofia.fmi.football_predictor.core.nutch.NutchUtil;

public class NutchRunner extends Thread {
	private static final long TIMEOUT = 1000 * 60 * 60 * 4;
	
	public void run() {
		while(true) {
			try {
				NutchUtil.crawlSites();
				NutchUtil.pushToSolr();
				
				sleep(TIMEOUT);
			} catch (Exception ex) {
				
			}
		}
	}

}
