package com.dns.api.compiletime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto: deven@dns.com">Deven Phillips</a>
 */
public class GenericAPI {

	protected String apiHost = null ;
	protected boolean useSSL = false ;
	protected String apiToken = null ;
	protected Logger log = null ;

	/**
	 * Constructor
	 * @param apiHost The host name of the server to make API calls against.
	 * @param useSSL Should we use HTTPS connections for API calls?
	 * @param apiToken The API Token for authenticating requests.
	 */
	public GenericAPI(String apiHost, boolean useSSL, String apiToken) {
		super() ;
		this.log = LoggerFactory.getLogger(GenericAPI.class.getSimpleName()) ;
		this.apiHost = apiHost ;
		this.useSSL = useSSL ;
		try {
			this.apiToken = URLEncoder.encode(apiToken, "US-ASCII") ;
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.getLocalizedMessage()) ;
			System.out.println(serializeStackTrace(uee.getStackTrace())) ;
		}
	}

	public void setSSL(boolean isSSL) {
		this.useSSL = isSSL ;
	}

	public boolean isSSL() {
		return useSSL ;
	}

	public void setApiHost(String host) {
		this.apiHost = host ;
	}

	public String getApiHost() {
		return this.apiHost ;
	}

	public void setApiToken(String token) {
		this.apiToken = token ;
	}

	public String getApiToken() {
		return this.apiToken ;
	}

	private String serializeStackTrace(StackTraceElement[] trace) {
		StringBuilder traceBuilder = new StringBuilder() ;
		for (int x=0; x<trace.length; x++) {
			traceBuilder.append(trace[x].toString()+"\n") ;
		}
		return traceBuilder.toString() ;
	}

	/**
	 * Takes a protocol string and a URI and makes an HTTP request. Returns a parsed
	 * <code>com.dns.mobile.json.JSONObject</code> from the results.
	 * @param req The portion of the URL after the host name
	 * @return A <code>com.dns.mobile.json.JSONObject</code> containing the results of the request.
	 */
	protected JSONObject makeHttpRequest(String req) {
		String protocol = this.useSSL?"https":"http" ;
		String breadCrumbs = "" ;
		JSONObject response ;

		if (req!=null) {
			String apiCall = protocol + "://" + apiHost + req;
			response = null;
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(apiCall);
			request.addHeader("User-Agent", "DNS-Android");
			HttpResponse answer = null;
			try {
				log.debug("Sending request to " + protocol + "://" + apiHost + req + "&isAndroid=true");
				answer = client.execute(request);
				log.debug("API HTTP Request Completed.");
			} catch (ClientProtocolException cpe) {
				log.debug("ClientProtocolException when trying to request API URL", cpe);
				response = new JSONObject();
				try {
					response.put("error", cpe.getLocalizedMessage());
					response.put("breadcrumb", breadCrumbs);
					response.put("stackTrace", serializeStackTrace(cpe.getStackTrace()));
				} catch (JSONException jsone) {
					log.error(cpe.getLocalizedMessage(), cpe);
				}
			} catch (IOException ioe) {
				log.error("IOException when trying to request API URL", ioe);
				response = new JSONObject();
				try {
					response.put("error", ioe.getLocalizedMessage());
					response.put("breadcrumb", breadCrumbs);
					response.put("stackTrace", serializeStackTrace(ioe.getStackTrace()));
				} catch (JSONException jsone) {
					log.error(jsone.getLocalizedMessage(), jsone);
				}
			}
			boolean isValidStatus = false;
			if (answer != null) {
				if ((answer.getStatusLine().getStatusCode() >= 200 && answer.getStatusLine().getStatusCode() <= 220) || answer.getStatusLine().getStatusCode() == 403) {
					isValidStatus = true;
				}
			} else {
				log.error("The HTTP answer object is null!!");
				response = new JSONObject();
				try {
					response.put("error", "HttpClient response is null.");
					response.put("breadcrumb", breadCrumbs);
				} catch (JSONException jsone) {
					log.error(jsone.getLocalizedMessage(), jsone);
				}
			}
			if (isValidStatus) {
				log.debug("HTTP Response status is 200 OK");
				try {
					BufferedReader bis = new BufferedReader(new InputStreamReader(answer.getEntity().getContent()));
					StringBuilder responseText = new StringBuilder();
					String line = null;
					log.debug("Reading in the response body.");
					line = bis.readLine();
					while (line != null) {
						responseText.append(line);
						line = bis.readLine();
					}
					log.debug("Response body read and stored\n\n" + responseText.toString() + "\n");
					response = new JSONObject(responseText.toString());
					bis.close() ;
				} catch (IOException ioe) {
					log.error("IOException when trying to read response body", ioe);
					response = new JSONObject();
					try {
						response.put("error", ioe.getLocalizedMessage());
						response.put("breadcrumb", breadCrumbs);
						response.put("stackTrace", serializeStackTrace(ioe.getStackTrace()));
					} catch (JSONException jsone) {
						log.error(jsone.getLocalizedMessage(), jsone);
					}
				} catch (JSONException jsone) {
					log.error("JSONException encountered while parsing request body.", jsone);
					response = new JSONObject();
					try {
						response.put("error", jsone.getLocalizedMessage());
						response.put("breadcrumb", breadCrumbs);
						response.put("stackTrace", serializeStackTrace(jsone.getStackTrace()));
					} catch (JSONException jsone1) {
						log.error(jsone1.getLocalizedMessage(), jsone1);
					}
				}
			} else {
				log.debug("Status '" + answer.getStatusLine().getStatusCode() + "' was not valid");
				BufferedReader bis = null;
				try {
					bis = new BufferedReader(new InputStreamReader(answer.getEntity().getContent()));
				} catch (IllegalStateException e) {
					log.error(e.getLocalizedMessage(), e);
				} catch (IOException e) {
					log.error(e.getLocalizedMessage(), e);
				}
				if (bis != null) {
					StringBuilder responseText = new StringBuilder();
					String line = null;
					log.debug("Reading in the response body.");
					try {
						line = bis.readLine();
						while (line != null) {
							responseText.append(line);
							line = bis.readLine();
						}
						bis.close() ;
					} catch (IOException e) {
						log.error(e.getLocalizedMessage(), e);
					}
					log.debug("Response body read and stored\n\n" + responseText.toString() + "\n");
					response = new JSONObject();
					try {
						response.put("error", "HttpClient response has code '" + answer.getStatusLine().getStatusCode() + "'.");
						response.put("breadcrumb", breadCrumbs);
					} catch (JSONException jsone) {
						log.error(jsone.getLocalizedMessage(), jsone);
					}
				}
			}
		} else {
			response = new JSONObject() ;
			try {
				response.put("error", "The request string is null!!!") ;
			} catch (JSONException jsone) {
				log.error(jsone.getLocalizedMessage(), jsone);
			}
		}

		return response;
	}
}
