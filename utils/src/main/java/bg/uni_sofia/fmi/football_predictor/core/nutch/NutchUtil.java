package bg.uni_sofia.fmi.football_predictor.core.nutch;

import java.util.StringTokenizer;

import org.apache.hadoop.util.ToolRunner;
import org.apache.nutch.crawl.Crawl;
import org.apache.nutch.indexer.solr.SolrIndexer;
import org.apache.nutch.util.NutchConfiguration;

public class NutchUtil {
	public static void crawlSites() throws Exception {
		String crawlArg = "/media/data/workspace/programs/apache-nutch-1.6/urls" +
				" -dir /media/data/workspace/programs/apache-nutch-1.6/crawl_goal_com -threads 20 -depth 5 -topN 30";
		
		ToolRunner.run(NutchConfiguration.create(), new Crawl(),
                tokenize(crawlArg));
	}
	
	public static void pushToSolr() throws Exception {
		String crawlArg = "http://127.0.0.1:8080/solr/ /media/data/workspace/programs/apache-nutch-1.6/crawl_goal_com/crawldb" +
				" -linkdb /media/data/workspace/programs/apache-nutch-1.6/crawl_goal_com/linkdb " +
				"/media/data/workspace/programs/apache-nutch-1.6/crawl_goal_com/segments/*";
		
		ToolRunner.run(NutchConfiguration.create(), new SolrIndexer(),
				tokenize(crawlArg));
	}
	
	private static String[] tokenize(String str) {
        StringTokenizer tok = new StringTokenizer(str);
        String tokens[] = new String[tok.countTokens()];
        int i = 0;
        while (tok.hasMoreTokens()) {
                tokens[i] = tok.nextToken();
                i++;
        }

        return tokens;
	}
}
