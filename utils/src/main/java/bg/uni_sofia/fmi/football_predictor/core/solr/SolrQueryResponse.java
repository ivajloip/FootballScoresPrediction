package bg.uni_sofia.fmi.football_predictor.core.solr;

import java.io.Serializable;

/** Wrapper of solr response. */
public class SolrQueryResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String title;
	
	private String body;
	
	private String url;
	
	public SolrQueryResponse(String title, String body, String url) {
		this.title = title;
		this.body = body;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFormattedTitle() {
		return format(getTitle());
	}
	
	public String getFormattedBody() {
		return format(getBody());
	}
	
	public String format(String value) {
		int maxLength = 50;
		if (value.length() < maxLength) {
			return value;
		}
		
		return value.substring(0, maxLength);
	}
	
}
