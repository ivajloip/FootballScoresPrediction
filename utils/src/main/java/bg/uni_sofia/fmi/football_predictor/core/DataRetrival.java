package bg.uni_sofia.fmi.football_predictor.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataRetrival {

	private static final String urlStart = "http://www.statbunker.com/football/btb/index.php?";
	private static final String urlStart2 = "http://www.statbunker.com/";
	private static final String urlStart3 = "/football/btb/btb_matchdetails.php?";
	private static final String urlStart4 = "http://www.statbunker.com/football/btb/btb_matchdetails.php?";
	private static String url = "PL=team&TeamID=FOOTBALL_TEAM&statType=seasons_results&CompID=YEARS_BEFORE";
	private static final int[] PL = new int[] { 415, 373, 323, 279, 243, 200,
			186, 179, 172, 163, 136, 76, 1 };
	private static final int[] SA = new int[] { 414, 377, 337, 292, 258, 211 };
	private static final int FOOTBALL_TEAMS = 5000;
	private static final String urlMatches = "	<td align=\"center\"><a href=\"";

	public static void main(String[] args) throws IOException {

		/*
		 * for (int i = 0; i < PL.length; i++) { for (int j = 0; j <
		 * FOOTBALL_TEAMS; j++) { getAllMatches(i, j); } }
		 */
		/*
		 * for (int i = 0; i < SA.length; i++) { for (int j = 0; j <
		 * FOOTBALL_TEAMS; j++) { getAllMatches(i, j); } }
		 */
		// getInfoForAllMatches();
		getInfoForMatch();

	}

	public static void getInfoForMatch() throws IOException {

		File allInfo = new File("D:\\matches");
		File[] matches = allInfo.listFiles();
		for (int i = 1; i < matches.length; i++) {
			Match match = new Match();
			File temp = matches[i];
			try {
				Document doc = Jsoup.parse(temp, null);
				Elements tableElements = doc.select("table");

				Elements tableHeaderEles = tableElements.select("thead tr th");

				if (tableHeaderEles.size() < 4) {
					continue;
				}

				// Home team, Goals for Home team, Goals for Away Team, Away
				// Team

				match.home.setName(tableHeaderEles.get(0).text());
				match.away.setName(tableHeaderEles.get(3).text());
				match.home.setCountry("England");
				match.away.setCountry("England");
				match.game.setScoreHome(Integer.parseInt(tableHeaderEles.get(1).text()));
				match.game.setScoreAway(Integer.parseInt(tableHeaderEles.get(2).text()));
				
				Elements tableRowElements = tableElements
						.select(":not(thead) tr");

				// First table-Tournament, Date(dd/mm/yyyy), Time, Stadium,
				// Referee, Attendance
				Element row = tableRowElements.get(1);
				Elements rowItems = row.select("td");

				match.game.setMatchDate(new java.sql.Date(DateUtils.parseDate(
						rowItems.get(1).text(), "dd/mm/yyyy").getTime()));
				int start = rowItems.get(0).text().length() - 5;
				String league = rowItems.get(0).text().substring(0, start - 1);
				int year = 2000 + Integer.parseInt(rowItems.get(0).text().substring(start, start + 2));
				
				match.game.setSeason(year);
				match.game.setLeague(league);
				/*
				 * for (int j = 0; j < rowItems.size(); j++) {
				 * System.out.print(rowItems.get(j).text() + ","); }
				 */

				// Home team scorers..., minutes..., Away team minutes..., Away
				// team scorers...
				row = tableRowElements.get(2);
				rowItems = row.select("td");
				StringBuilder scorers = new StringBuilder();

				for (int j = 0; j < rowItems.size(); j++) {
					if (!rowItems.get(j).text().equals("")) {
						String toBeTrimmed = rowItems.get(j).html()
								.replaceAll("<(.)+?>", ",");
						scorers.append(toBeTrimmed);
					}
				}
				//System.out.println(scorers);
				System.out.println("bhfghhdtgfhf");
				String tmpScorers = "";
				if (scorers.length() > 0) {
					tmpScorers = scorers.toString();
					if(tmpScorers.startsWith(",")){
						tmpScorers = tmpScorers.substring(1);
					}
					tmpScorers = tmpScorers.replaceAll(
							"(, )+", ",");
					//tmpScorers = tmpScorers.replaceAll("P", ",");
					tmpScorers = tmpScorers.replaceAll(",+", ",");
					tmpScorers = tmpScorers.replaceAll(",\\. ", ",");
					tmpScorers = tmpScorers.replaceAll("\\([A-Z]+\\)", "");
					tmpScorers = tmpScorers.replaceAll("[A-Z]+\\)", "");
					tmpScorers = tmpScorers.replaceAll("\\. ", "");
					tmpScorers = tmpScorers.replaceAll(",+", ",");
					if(tmpScorers.startsWith(",")){
						tmpScorers = tmpScorers.substring(1);
					}
				}
				System.out.println(tmpScorers);
				String goals[] = tmpScorers.split(",");
				Map<String, Integer> homeScorers = new Hashtable<String, Integer>();
				Map<String, Integer> awayScorers = new Hashtable<String, Integer>();
				
				for(int k = 0; k < match.game.getScoreHome(); k++){
					if(!homeScorers.containsKey(goals[k])){
						homeScorers.put(goals[k], new Integer(1));
					}
					else{
						homeScorers.put(goals[k], homeScorers.get(goals[k]) + 1);
					}
				}
				
				Iterator<String> iter = homeScorers.keySet().iterator();
				while(iter.hasNext()){
					Player player = new Player(iter.next());
					match.homePlayers.add(player);
//					GamePlayer gp = new GamePlayer();
//					gp.setGame(match.game);
//					gp.setPlayer(player);
//					gp.setGoals(homeScorers.get(player.getName()));
//					match.game.getHomePlayers().add(gp);
				}
				
				//Away team goals
				
				for(int k = 1; k < match.game.getScoreAway(); k++){
					if(!awayScorers.containsKey(goals[match.game.getScoreHome() * 2 + match.game.getScoreAway() + k])){
						awayScorers.put(goals[k], new Integer(1));
					}
					else{
						awayScorers.put(goals[k], awayScorers.get(goals[k]) + 1);
					}
				}
				
				iter = awayScorers.keySet().iterator();
				while(iter.hasNext()){
					Player player = new Player(iter.next());
					match.awayPlayers.add(player);
//					GamePlayer gp = new GamePlayer();
//					gp.setGame(match.game);
//					gp.setPlayer(player);
//					gp.setGoals(awayScorers.get(player.getName()));
//					match.game.getAwayPlayers().add(gp);
				}
				
				// {Home Player, Number, Away player, 11}
				for (int k = 3; k < 14; k++) {
					row = tableRowElements.get(k);
					rowItems = row.select("td");
					for (int j = 0; j + 2 < rowItems.size(); j += 3) {
						Player playerHome = new Player(rowItems.get(j).text());
						Player playerAway = new Player(rowItems.get(j + 2).text());
						match.homePlayers.add(playerHome);
						match.awayPlayers.add(playerAway);
//						GamePlayer gph = new GamePlayer();
//						GamePlayer gpa = new GamePlayer();
//						
//						gph.setGame(match.game);
//						gph.setPlayer(playerHome);
//						gph.setGoals(0);
//						match.game.getHomePlayers().add(gph);
//						
//						gpa.setGame(match.game);
//						gpa.setPlayer(playerAway);
//						gpa.setGoals(0);
//						match.game.getAwayPlayers().add(gpa);
						
					}
				}

				// {Minute, ON player, OFF player; n} {HomeBenchWarmer, Number,
				// AwayBenchwarmer; 5}
				StringBuilder benchWarmers = new StringBuilder();
				for (int k = 14; k < tableRowElements.size(); k++) {
					row = tableRowElements.get(k);
					rowItems = row.select("td");
					for (int j = 0; j < rowItems.size(); j++) {
						if (!rowItems.get(j).text().equals("")) {
							String toBeTrimmed = rowItems.get(j).html()
									.replaceAll("<(.)+?>", ",");
							benchWarmers.append(toBeTrimmed);
						}
					}
				}
				int substitutions, remarks = 0;
				if (benchWarmers.length() > 0) {
					String tmpWarmers = benchWarmers.substring(1).replaceAll(
							"(, )+", ",");

					substitutions = StringUtils.countMatches(benchWarmers,
							"(Mins)");
					remarks = StringUtils
							.countMatches(benchWarmers, "Yellow (");
					remarks += StringUtils.countMatches(benchWarmers,
							"Yellow/Red (");
					remarks += StringUtils.countMatches(benchWarmers, ",Red (");

					tmpWarmers = tmpWarmers.replaceAll("\\([A-Z]+\\)", ",");
					tmpWarmers = tmpWarmers.replaceAll(",+", ",");
					tmpWarmers = tmpWarmers.replaceAll(" \\(Mins\\) - ", "");
					tmpWarmers = tmpWarmers.replaceAll("ON \\(,", "");
					tmpWarmers = tmpWarmers.replaceAll("\\) OFF ,", "");
					tmpWarmers = tmpWarmers.replaceAll(",&nbsp;", "");
					tmpWarmers = tmpWarmers.replaceAll(",\\. ", ",");
					//System.out.print(tmpWarmers);
					// System.out.println();
					// System.out.println(substitutions);
					// System.out.println(remarks);
				}
				System.out.println();
			HibernateUtils.save(match);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void getInfoForAllMatches() throws IOException {
		File currentDirectory = new File(".");
		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("PL=");
			}
		};

		File[] allFiles = currentDirectory.listFiles(textFilter);
		for (int i = 0; i < allFiles.length; i++) {
			FileInputStream fis = null;
			BufferedReader br = null;
			try {
				String line;
				fis = new FileInputStream(allFiles[i]);
				br = new BufferedReader(new InputStreamReader(fis,
						Charset.forName("UTF-8")));
				while ((line = br.readLine()) != null) {
					if (line.startsWith(urlMatches)) {
						String[] strings = line.split("\"");
						String fileContent = getHTML(urlStart2 + strings[3]);
						write(fileContent,
								strings[3].substring(urlStart3.length()));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				br.close();
				fis.close();
			}
		}
	}

	public static void getAllMatches(int i, int j) {

		String temp = url.replace("FOOTBALL_TEAM", Integer.toString(j));
		temp = temp.replace("YEARS_BEFORE", Integer.toString(SA[i]));
		String fileContent = getHTML(urlStart + temp);
		if (fileContent.length() >= 30000) {
			write(fileContent, temp);
		}

	}

	public static void write(String fileContent, String temp) {

		try {
			PrintWriter writer = new PrintWriter(temp, "UTF-8");
			writer.println(fileContent);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getHTML(String urlToRead) {

		URL url; // The URL to read
		HttpURLConnection conn; // The actual connection to the web page
		BufferedReader rd; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		StringBuilder result = new StringBuilder(); // A long string containing
													// all the HTML
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result.append(line);
				result.append("\r\n");
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();

	}

}
