package com.dns.api.examples;

import com.dns.api.compiletime.*;
import org.json.* ;

public class BasicExamples {
    ManagementAPI api = null ;

	public void main (String[] args) {
        String apiHost = "www.dns.com" ; // Set to 'sandbox.dns.com' for development and testing.
        boolean useSSL = true ;          // Set to 'false' when using the sandbox
        String apiToken = "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX" ; // Replace this with your own API token

        api = new ManagementAPI(apiHost, useSSL, apiToken) ;

        String domainFilter = "" ; // And text in here will be used to filter the domains returned case-insensitively
        JSONObject domainsResult = api.getDomains(domainFilter) ;

/*
The JSON data should look something like:

{
	"meta": {
		"code": 200, 
		"success": 1
	}, 
	"data": [
		{
			"name": "mydomain1.com", 
			"id": 76182, 
			"mode": "advanced", 
			"date_created": "2010-08-04 18:04:20", 
			"num_hosts": 26, 
			"date_last_modified": "2011-12-29 14:26:12"
		}, {
			"name": "mydomain2.com", 
			"id": 106053, 
			"mode": "advanced", 
			"date_created": "2010-12-29 21:39:08", 
			"num_hosts": 6, 
			"date_last_modified": "2011-12-25 15:04:44"
		}
	]
}
*/

        if (domainsResult.has("meta")) {
             try {
                 if (domainsResult.getJSONObject("meta").has("error")) {
                     // An error occurred and this field contains the error message
                     System.out.println("The following error occurred: "+domainsResult.getJSONObject("meta").getString("error")) ;
                 } else {
                	 JSONArray data = domainsResult.getJSONArray("data") ;
                	 for (int x=0; x<data.length(); x++) {
                		 JSONObject domain = data.getJSONObject(x) ;
                		 String domainName = domain.getString("name") ;
                		 int domainID = domain.getInt("id") ;
                		 String dateCreated = domain.getString("date_created") ;
                		 int hostCount = domain.getInt("num_hosts") ;
                		 String dateLastModified = domain.getString("date_last_modified") ;
                		 // We now have all of the information about this domain in the list and we can manipulate it
                		 
                		 JSONObject domainHostList = api.getHostnamesForDomain(domainName) ;
                		 /*
                		 This should return a JSON result which looks like:
                		 
                		 {"meta": 
                		 	{
                		 		"domain": "zanclus.com", 
                		 		"code": 200, 
                		 		"success": 1
            		 		}, 
            		 		"data": [
            		 			{
            		 				"name": "", 
            		 				"num_rr": 7, 
            		 				"is_urlforward": false, 
            		 				"date_created": "2010-08-04 18:04:20", 
            		 				"id": 35910, 
            		 				"date_last_modified": "2010-08-04 18:04:20"
        		 				}, {
        		 					"name": "www", 
        		 					"num_rr": 1, 
        		 					"is_urlforward": false, 
        		 					"date_created": "2010-08-04 18:04:25", 
        		 					"id": 35911, 
        		 					"date_last_modified": "2010-08-04 18:04:25"
    		 					}, {
    		 						"name": "mail", 
    		 						"num_rr": 1, 
    		 						"is_urlforward": false, 
    		 						"date_created": "2010-08-04 18:04:25", 
    		 						"id": 35912, 
    		 						"date_last_modified": "2010-08-04 18:04:25"
		 						}
						    ]
						}
                		*/
                	 }
                 }
             } catch (JSONException jsone) {
                 jsone.printStackTrace() ;
             }
        }
    }
}
