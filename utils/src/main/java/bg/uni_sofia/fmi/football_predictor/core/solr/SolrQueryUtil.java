package bg.uni_sofia.fmi.football_predictor.core.solr;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

public class SolrQueryUtil {
	
	private static String DEFAULT_SOLR_URL = "http://localhost:8080/solr";
	
	private static CommonsHttpSolrServer solrServer;
	
	public static CommonsHttpSolrServer getSolrServer(String url)
			throws MalformedURLException {
		if (solrServer == null) {
			solrServer = new CommonsHttpSolrServer(url);
		}
		
		return solrServer;
	}
	
	public static CommonsHttpSolrServer getSolrServer()
			throws MalformedURLException {
		return getSolrServer(DEFAULT_SOLR_URL);
	}

	public static Collection<SolrQueryResponse> find(String data)
			throws MalformedURLException, SolrServerException {
		return find(data, data);
	}
	
	public static Collection<SolrQueryResponse> find(String title, String content)
			throws MalformedURLException, SolrServerException {
		CommonsHttpSolrServer server = getSolrServer();
		
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "title:" + title);
		params.set("q", "content:" + content);
		params.set("fl", "url,tstamp,title,content");
//		params.set("fq", "");
		params.set("defType", "edismax");
		params.set("start", "0");
		params.set("rows", "20");

		QueryResponse response = server.query(params);
		SolrDocumentList results = response.getResults();
		
		List<SolrQueryResponse> result = new ArrayList<SolrQueryResponse>();
		
		for (SolrDocument doc : results) {
			String docTitle = (String) doc.getFirstValue("title");
			String docBody = (String) doc.getFirstValue("body");
			String docUrl = (String) doc.getFirstValue("url");
			SolrQueryResponse item =
					new SolrQueryResponse(docTitle, docBody, docUrl);
			result.add(item);
		}
		
		return result;
	}
	
}
