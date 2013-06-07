package bg.uni_sofia.fmi.football_predictor.core;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.chainsaw.Main;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

/**
 * A simple main to illustrate how to execute a request using SolrJ
 * 
 */

public class SolrTest {

	public static void addDocs() throws IOException,
			SolrServerException {
		CommonsHttpSolrServer server = new CommonsHttpSolrServer(
				"http://localhost:8090/solr");
		for (int i = 0; i < 1000; ++i) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("cat", "book");
			doc.addField("id", "book-" + i);
			doc.addField("name", "The Legend of Po part " + i);
			server.add(doc);
			if (i % 100 == 0)
				server.commit(); // periodically flush
		}
		server.commit();
	}

	public static void search() throws MalformedURLException,
			SolrServerException {
		CommonsHttpSolrServer solr = new CommonsHttpSolrServer(
				"http://localhost:8090/solr");

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "cat:electronics");
		params.set("defType", "edismax");
		params.set("start", "0");

		QueryResponse response = solr.query(params);
		SolrDocumentList results = response.getResults();
		for (int i = 0; i < results.size(); ++i) {
			System.out.println(results.get(i));
		}
	}
	public static void main(String[] args) {
		try {
			search();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}