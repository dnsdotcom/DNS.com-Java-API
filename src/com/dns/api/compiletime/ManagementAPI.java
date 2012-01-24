/*
 * DNS.com Java API - Copyright 2011, DNS, Inc. - All rights reserved.
 * This code is released under the terms of the BSD License. See LICENSE file in the root
 * of this code base for more information.
 */

package com.dns.api.compiletime;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An implementation of the Management API which validates arguments at compile time
 * @author <a href="mailto: deven@dns.com">Deven Phillips</a>
 */
public class ManagementAPI extends GenericAPI {

	/**
	 * Constructor
	 * @param apiHost The host name of the server to make API calls against.
	 * @param useSSL Should we use HTTPS connections for API calls?
	 * @param apiToken The API Token for authenticating requests.
	 */
	public ManagementAPI(String apiHost, boolean useSSL, String apiToken) {
		super(apiHost, useSSL, apiToken) ;
	}

	/**
	 * Returns the JSON results of an appendToGeoGroup API call.
	 * @param name The name of the GeoGroup to be appended to
	 * @param iso2Code The 2 character country code for this entry
	 * @param region (OPTIONAL) Either the region name or <code>null</code>
	 * @param city (OPTIONAL) Either the city name or <code>null</code>
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject appendToGeoGroup(String name, String iso2Code, String region, String city) {
		StringBuilder uriBuilder = new StringBuilder("/api/appendToGeoGroup/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;
		uriBuilder.append("&name="+name) ;
		uriBuilder.append("&iso2_code="+iso2Code) ;
		if (region!=null) {
			uriBuilder.append("region="+region) ;
		}
		if (city!=null) {
			uriBuilder.append("city="+city) ;
		}
		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Changes the mode of the specified domain and assigns domain to a group if requested.
	 * @param domain The domain to change the mode on
	 * @param mode The mode to change the domain to (either "advanced" or "group"
	 * @param group The group to assign the domain to
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject assignDomainMode(String domain, String mode, String group) {
		StringBuilder uriBuilder = new StringBuilder("/api/assignDomainMode/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;
		uriBuilder.append("&domain="+domain) ;
		uriBuilder.append("&mode="+mode) ;
		if (group!=null) {
			uriBuilder.append("&group="+group) ;
		}
		
		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Creates a new domain entry in the system using the specified mode and domain name.
	 * @param domain The domain to change the mode on
	 * @param mode The mode to change the domain to (either "advanced" or "group"
	 * @param group (OPTIONAL) The group to assign the domain to or <code>null</code>
	 * @param rname (OPTIONAL) Contact for SOA of this zone. (ex: admin@example.com. is admin.example.com) or <code>null</code>
	 * @param ns (OPTIONAL) List of name servers to use or <code>null</code>
	 * @param primary_wildcard (OPTIONAL) IP or CNAME destination for a wildcard on the primary zone or <code>null</code>
	 * @param primary_wildcard_qtype (OPTIONAL) Either A or CNAME  or <code>null</code>. IS REQUIRED IF `primary_wildcard` IS SUPPLIED
	 * @param default_mx (OPTIONAL) MX Destination for email or <code>null</code>
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createDomain(
			String mode,
			String domain,
			String group,
			String rname,
			String[] ns,
			String primary_wildcard,
			String primary_wildcard_qtype,
			String default_mx) {
		StringBuilder uriBuilder = new StringBuilder("/api/createDomain/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;
		uriBuilder.append("&domain="+domain) ;
		uriBuilder.append("&mode="+mode) ;
		if (group!=null) {
			uriBuilder.append("&group="+group) ;
		}
		if (rname!=null) {
			uriBuilder.append("&rname="+rname) ;
		}
		if (ns!=null) {
			for (int x=0; x<ns.length;x++) {
				uriBuilder.append("&ns="+ns[x]) ;
			}
		}
		if (primary_wildcard!=null) {
			uriBuilder.append("&primary_wildcard="+primary_wildcard) ;
		}
		if (primary_wildcard_qtype!=null) {
			uriBuilder.append("&primary_wildcard_qtype="+primary_wildcard_qtype) ;
		}
		if (default_mx!=null) {
			uriBuilder.append("&default_mx="+default_mx) ;
		}
		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Create a new domain group with the specified default settings
	 * @param name The name of the group to create
	 * @param rname (OPTIONAL) Contact for SOA of this zone. (ex: admin@example.com. is admin.example.com) or <code>null</code>
	 * @param ns (OPTIONAL) List of name servers to use or <code>null</code>
	 * @param primary_wildcard (OPTIONAL) IP or CNAME destination for a wildcard on the primary zone or <code>null</code>
	 * @param primary_wildcard_qtype (OPTIONAL) Either A or CNAME  or <code>null</code>. IS REQUIRED IF `primary_wildcard` IS SUPPLIED
	 * @param default_mx (OPTIONAL) MX Destination for email or <code>null</code>
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createDomainGroup(
				String name,
				String rname,
				String[] ns,
				String primary_wildcard,
				String primary_wildcard_qtype,
				String default_mx) {
		StringBuilder uriBuilder = new StringBuilder("/api/createDomainGroup/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;
		uriBuilder.append("&name="+name) ;
		if (rname!=null) {
			uriBuilder.append("&rname="+rname) ;
		}
		if (ns!=null) {
			for (int x=0; x<ns.length;x++) {
				uriBuilder.append("&ns="+ns[x]) ;
			}
		}
		if (primary_wildcard!=null) {
			uriBuilder.append("&primary_wildcard="+primary_wildcard) ;
		}
		if (primary_wildcard_qtype!=null) {
			uriBuilder.append("&primary_wildcard_qtype="+primary_wildcard_qtype) ;
		}
		if (default_mx!=null) {
			uriBuilder.append("&default_mx="+default_mx) ;
		}
		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Create multiple new domains and optionally assign them to a domain group.
	 * @param domains A list of domains to be added.
	 * @param mode The mode to set for the domains (either "advanced" or "group"
	 * @param group (OPTIONAL) The domain group to assign the domains to or <code>null</code>
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createDomains(String mode, String[] domains, String group) {
		StringBuilder uriBuilder = new StringBuilder("/api/createDomainGroup/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&mode="+mode) ;
		for (int x=0; x<domains.length; x++) {
			uriBuilder.append("&domains="+domains[x]) ;
		}
		if (group!=null) {
			uriBuilder.append("&group="+group) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Create a new hostname in the specifed domain/group
	 * @param name The name of the domain/group to have the host added to
	 * @param isGroup Is this change for a domain or a group?
	 * @param host The name of the host to be added
	 * @param isUrlForward Is this a URL forward host?
	 * @param defaultAddr (OPTIONAL) The default address to have undefined traffic sent to or <code>null</code>
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createHostname(String name, boolean isGroup, String host, boolean isUrlForward, String defaultAddr) {
		StringBuilder uriBuilder = new StringBuilder("/api/createHostname/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (isGroup) {
			uriBuilder.append("&group="+name) ;
		} else {
			uriBuilder.append("&domain="+name) ;
		}

		uriBuilder.append("&host="+host) ;

		if (isUrlForward) {
			uriBuilder.append("&is_urlforward=true") ;
		}

		if (defaultAddr!=null) {
			uriBuilder.append("&default="+defaultAddr) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Create a new A record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param type The type of record to create (This method supports A, AAAA, TXT, NS, and CNAME)
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createDefaultRecord(
				String name,
				boolean isGroup,
				String host,
				String rdata,
				String type,
				boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		StringBuilder uriBuilder = new StringBuilder("/api/createRRData/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (isGroup) {
			uriBuilder.append("&group="+name) ;
		} else {
			uriBuilder.append("&domain="+name) ;
		}

		uriBuilder.append("&host="+host) ;
		uriBuilder.append("&type="+type) ;

		if (isWildcard) {
			uriBuilder.append("&is_wildcard=true") ;
		}

		uriBuilder.append("&rdata="+rdata) ;

		if (geoGroup!=null&&(geoGroup.compareTo("null")!=0)) {
			uriBuilder.append("&geoGroup="+geoGroup) ;
		}
		if (iso2Code!=null&&(iso2Code.compareTo("null")!=0)) {
			uriBuilder.append("&country_iso2="+iso2Code) ;
		}
		if (region!=null&&(region.compareTo("null")!=0)) {
			uriBuilder.append("&region="+region) ;
		}
		if (city!=null&&(city.compareTo("null")!=0)) {
			uriBuilder.append("&city="+city) ;
		}
		if (ttl!=null) {
			uriBuilder.append("&ttl="+ttl) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Create a new A record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createARecord(
				String name,
				boolean isGroup,
				String host,
				String rdata,
				boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		return createDefaultRecord(name, isGroup, host, rdata, "A", isWildcard, geoGroup, iso2Code, region, city, ttl) ;
	}

	/**
	 * Create a new AAAA record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createAAAARecord(
				String name,
				boolean isGroup,
				String host,
				String rdata,
				boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		return createDefaultRecord(name, isGroup, host, rdata, "AAAA", isWildcard, geoGroup, iso2Code, region, city, ttl) ;
	}

	/**
	 * Create a new TXT record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createTXTRecord(
				String name,
				boolean isGroup,
				String host,
				String rdata,
				boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		String safeRData = null ;
		try {
			safeRData = URLEncoder.encode(rdata, "US-ASCII") ;
		} catch (UnsupportedEncodingException uee) {
			safeRData = "" ;
			uee.printStackTrace() ;
		}
		return createDefaultRecord(name, isGroup, host, safeRData, "TXT", isWildcard, geoGroup, iso2Code, region, city, ttl) ;
	}

	/**
	 * Create a new CNAME record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createCNAMERecord(
				String name,
				boolean isGroup,
				String host,
				String rdata,
				boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		return createDefaultRecord(name, isGroup, host, rdata, "CNAME", isWildcard, geoGroup, iso2Code, region, city, ttl) ;
	}

	/**
	 * Create a new NS record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createNSRecord(
				String name,
				boolean isGroup,
				String host,
				String rdata,
				boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		return createDefaultRecord(name, isGroup, host, rdata, "NS", isWildcard, geoGroup, iso2Code, region, city, ttl) ;
	}

	/**
	 * Create a new SOA record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param retry (OPTIONAL) The integer value for retry or <code>null</code> for the default value.
	 * @param minimum (OPTIONAL) The integer value for minimum or <code>null</code> for the default value.
	 * @param expire (OPTIONAL) The integer value for expire or <code>null</code> for the default value.
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createSOARecord(
				String name,
				Boolean isGroup,
				String host,
				String rdata,
				Integer retry,
				Integer expire,
				Integer minimum,
				Boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		StringBuilder uriBuilder = new StringBuilder("/api/createRRData/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (isGroup) {
			uriBuilder.append("&group="+name) ;
		} else {
			uriBuilder.append("&domain="+name) ;
		}

		uriBuilder.append("&host="+host) ;
		uriBuilder.append("&type=SOA") ;

		if (isWildcard!=null) {
			if (isWildcard.booleanValue()) {
				uriBuilder.append("&is_wildcard=true") ;
			}
		}

		uriBuilder.append("&rdata="+rdata) ;

		if (geoGroup!=null && (!geoGroup.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&geoGroup="+geoGroup) ;
		}
		if (iso2Code!=null && (!iso2Code.toLowerCase().contains("null"))) {
			uriBuilder.append("&country_iso2="+geoGroup) ;
		}
		if (region!=null && (!region.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&region="+region) ;
		}
		if (city!=null && (!city.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&city="+city) ;
		}
		if (ttl!=null) {
			uriBuilder.append("&ttl="+ttl) ;
		}
		if (minimum!=null) {
			uriBuilder.append("&minimum="+minimum) ;
		}
		if (retry!=null) {
			uriBuilder.append("&retry="+retry) ;
		}
		if (expire!=null) {
			uriBuilder.append("&expire="+expire) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Create a new SRV record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param weight The preference weight for this record
	 * @param priority The priority preference for this record
	 * @param port The TCP/UDP port for this service
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createSRVRecord(
				String name,
				boolean isGroup,
				String host,
				String rdata,
				int weight,
				int priority,
				int port,
				boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		StringBuilder uriBuilder = new StringBuilder("/api/createRRData/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (isGroup) {
			uriBuilder.append("&group="+name) ;
		} else {
			uriBuilder.append("&domain="+name) ;
		}

		uriBuilder.append("&host="+host) ;

		if (isWildcard) {
			uriBuilder.append("&is_wildcard=true") ;
		}

		uriBuilder.append("&rdata="+rdata) ;
		uriBuilder.append("&weight="+weight) ;
		uriBuilder.append("&port="+port) ;
		uriBuilder.append("&priority="+priority) ;
		uriBuilder.append("&type=SRV") ;

		if (geoGroup!=null && (!geoGroup.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&geoGroup="+geoGroup) ;
		}
		if (iso2Code!=null && (!iso2Code.toLowerCase().contains("null"))) {
			uriBuilder.append("&country_iso2="+geoGroup) ;
		}
		if (region!=null && (!region.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&region="+region) ;
		}
		if (city!=null && (!city.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&city="+city) ;
		}
		if (ttl!=null) {
			uriBuilder.append("&ttl="+ttl) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Create a new MX record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createMXRecord(
				String name,
				boolean isGroup,
				String host,
				String rdata,
				int priority,
				boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		StringBuilder uriBuilder = new StringBuilder("/api/createRRData/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (isGroup) {
			uriBuilder.append("&group="+name) ;
		} else {
			uriBuilder.append("&domain="+name) ;
		}

		uriBuilder.append("&host="+host) ;
		uriBuilder.append("&type=MX") ;
		uriBuilder.append("&priority="+priority) ;

		if (isWildcard) {
			uriBuilder.append("&is_wildcard=true") ;
		}

		uriBuilder.append("&rdata="+rdata) ;

		if (geoGroup!=null && (!geoGroup.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&geoGroup="+geoGroup) ;
		}
		if (iso2Code!=null && (!iso2Code.toLowerCase().contains("null"))) {
			uriBuilder.append("&country_iso2="+geoGroup) ;
		}
		if (region!=null && (!region.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&region="+region) ;
		}
		if (city!=null && (!city.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&city="+city) ;
		}
		if (ttl!=null) {
			uriBuilder.append("&ttl="+ttl) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Create a new URL301 record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createURL301Record(
				String name,
				boolean isGroup,
				String host,
				String rdata,
				boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		return createDefaultRecord(name, isGroup, host, rdata, "URL301", isWildcard, geoGroup, iso2Code, region, city, ttl) ;
	}

	/**
	 * Create a new URLFrame record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param title (OPTIONAL) The page title for the frame or <code>null</code>
	 * @param description (OPTIONAL) The HTML Meta description tag content or <code>null</code>
	 * @param keywords (OPTIONAL) The HTML Meta keywords tag content or <code>null</code>
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createURLFrameRecord(
				String name,
				boolean isGroup,
				String host,
				String rdata,
				String title,
				String description,
				String keywords,
				boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		String encTitle = null ;
		String encDesc = null ;
		String encKeywords = null ;
		try {
			encTitle = URLEncoder.encode(title, "US-ASCII") ;
			encDesc = URLEncoder.encode(description, "US-ASCII") ;
			encKeywords = URLEncoder.encode(keywords, "US-ASCII") ;
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace() ;
		}

		StringBuilder uriBuilder = new StringBuilder("/api/createRRData/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (isGroup) {
			uriBuilder.append("&group="+name) ;
		} else {
			uriBuilder.append("&domain="+name) ;
		}

		uriBuilder.append("&host="+host) ;
		uriBuilder.append("&type=URLFrame") ;

		if (isWildcard) {
			uriBuilder.append("&is_wildcard=true") ;
		}

		uriBuilder.append("&rdata="+rdata) ;

		if (geoGroup!=null && (!geoGroup.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&geoGroup="+geoGroup) ;
		}
		if (iso2Code!=null && (!iso2Code.toLowerCase().contains("null"))) {
			uriBuilder.append("&country_iso2="+geoGroup) ;
		}
		if (region!=null && (!region.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&region="+region) ;
		}
		if (city!=null && (!city.toLowerCase().contentEquals("null"))) {
			uriBuilder.append("&city="+city) ;
		}
		if (ttl!=null) {
			uriBuilder.append("&ttl="+ttl) ;
		}
		if (title!=null) {
			uriBuilder.append("&title="+encTitle) ;
		}
		if (keywords!=null) {
			uriBuilder.append("&keywords="+encKeywords) ;
		}
		if (description!=null) {
			uriBuilder.append("&description="+encDesc) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Create a new URL302 record using the specified options
	 * @param name The name of the domain/group to have the record added to
	 * @param isGroup Is this a domain or a domain group?
	 * @param host The host name to have the record added to
	 * @param rdata The response data for this A record.
	 * @param isWildcard Is this record a wildcard?
	 * @param geoGroup (OPTIONAL) GeoGroup to set for this record or <code>null</code>
	 * @param iso2Code (OPTIONAL) The 2 character ISO country code to set for this record or <code>null</code>
	 * @param region (OPTIONAL) The region to set for this record or <code>null</code>
	 * @param city (OPTIONAL) The city to set for this record or <code>null</code>
	 * @param ttl (OPTIONAL) The Time-To-Live for resolvers to hold cache of rdata, between 1 and
	 * 65535 OR <code>null</code> to use the default of 1440
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createURL302Record(
				String name,
				boolean isGroup,
				String host,
				String rdata,
				boolean isWildcard,
				String geoGroup,
				String iso2Code,
				String region,
				String city,
				Integer ttl) {
		return createDefaultRecord(name, isGroup, host, rdata, "URL302", isWildcard, geoGroup, iso2Code, region, city, ttl) ;
	}

	/**
	 * Deletes the specified domain
	 * @param domain The name of the domain to delete.
	 * @param confirm Confirm that this is that action required, otherwise the request will fail.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject deleteDomain(String domain, boolean confirm) {
		StringBuilder uriBuilder = new StringBuilder("/api/deleteDomain/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&domain="+domain) ;
		if(confirm) {
			uriBuilder.append("&confirm=true") ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Disables the specified domain
	 * @param domain The name of the domain to disable.
	 * @param confirm Confirm that this is that action required, otherwise the request will fail.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject disableDomain(String domain, boolean confirm) {
		StringBuilder uriBuilder = new StringBuilder("/api/disableDomain/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&domain="+domain) ;
		if(confirm) {
			uriBuilder.append("&confirm=true") ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Enables the specified domain
	 * @param domain The name of the domain to enable.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject enableDomain(String domain, boolean confirm) {
		StringBuilder uriBuilder = new StringBuilder("/api/enableDomain/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&domain="+domain) ;
		if(confirm) {
			uriBuilder.append("&confirm=true") ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Return a list of domain groups matching the specified filter string.
	 * @param filter A case insensitive search filter or "" for all.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject getDomainGroups(String filter) {
		StringBuilder uriBuilder = new StringBuilder("/api/getDomainGroups/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&search_term="+filter) ;

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Return a list of domains matching the specified filter string.
	 * @param filter A case insensitive search filter or "" for all.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject getDomains(String filter) {
		StringBuilder uriBuilder = new StringBuilder("/api/getDomains/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&search_term="+filter) ;

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Return a list of domains which are members of the specified domain group.
	 * @param group The name of the group to get a list of member domains for.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject getDomainsInGroup(String group) {
		StringBuilder uriBuilder = new StringBuilder("/api/getDomainsInGroup/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&group="+group) ;

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Create a new domain group with the specified name
	 * @param group The name of the new domain group to be created.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject createGeoGroup(String group) {
		StringBuilder uriBuilder = new StringBuilder("/api/createDomainGroup/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&group="+group) ;

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Get all hostnames for the specified domain
	 * @param domain The name of the domain to enumerate hosts for.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject getHostnamesForDomain(String domain) {
		StringBuilder uriBuilder = new StringBuilder("/api/getHostnamesForDomain/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&domain="+domain) ;

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Get all hostnames for the specified group
	 * @param group The name of the group to enumerate hosts for.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject getHostnamesForGroup(String group) {
		StringBuilder uriBuilder = new StringBuilder("/api/getHostnamesForGroup/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&group="+group) ;

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Return the resource record data for a given domain/group and hostname.
	 * @param name The name of the domain/group to pull from.
	 * @param isGroup Is this a domain or a domainGroup?
	 * @param hostname The host name to get the resource records for.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject getRRSetForHostname(String name, boolean isGroup, String hostname) {
		StringBuilder uriBuilder = new StringBuilder("/api/getRRSetForHostname/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (isGroup) {
			uriBuilder.append("&group="+name) ;
		} else {
			uriBuilder.append("&domain="+name) ;
		}

		uriBuilder.append("&host="+hostname) ;

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Request that the domain be rebuilt and redistributed to the authoritative name servers.
	 * @param name The name of the domain/group to be rebuilt.
	 * @param isGroup Is this a domain or a domain group?
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject rebuild(String name, boolean isGroup) {
		StringBuilder uriBuilder = new StringBuilder("/api/rebuild/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (isGroup) {
			uriBuilder.append("&group="+name) ;
		} else {
			uriBuilder.append("&domain="+name) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Remove the specified domain group
	 * @param group The name of the domain group to remove.
	 * @param confirm Set to "true" in order to authorize the call, otherwise the call will fail.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject removeDomainGroup(String group, boolean confirm) {
		StringBuilder uriBuilder = new StringBuilder("/api/removeDomainGroup/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&group="+group) ;

		if(confirm) {
			uriBuilder.append("&confirm=true") ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Remove the specified host from the specified domain/group configuration.
	 * @param name The name of the domain/group in which to find this host
	 * @param isGroup Is this request for a domain or a domain group?
	 * @param host The name of the host to remove.
	 * @param confirm Set to true or this request will fail.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject removeHostname(String name, boolean isGroup, String host, boolean confirm) {
		StringBuilder uriBuilder = new StringBuilder("/api/removeHostname/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (isGroup) {
			uriBuilder.append("&group="+name) ;
		} else {
			uriBuilder.append("&domain="+name) ;
		}

		uriBuilder.append("&host="+host) ;

		if (confirm) {
			uriBuilder.append("&confirm=true") ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Remove the resource record identified by rrId
	 * @param rrId The record ID to be removed, can be found by pulling data from getRRSetForHostname()
	 * @param confirm Set to true or this request will fail.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject removeRR(int rrId, boolean confirm) {
		StringBuilder uriBuilder = new StringBuilder("/api/removeRR/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;
		uriBuilder.append("&rr_id="+rrId) ;

		if (confirm) {
			uriBuilder.append("&confirm=true") ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Update the root wildcard address for the listed domains and/or group
	 * @param address The IPv4 address to set as the rdata for the listed domains and groups
	 * @param group (OPTIONAL) The domain group to update the root wildcard for or <code>null</code>
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject updateRootWildcardForList(String address, String group) {
		StringBuilder uriBuilder = new StringBuilder("/api/removeHostname/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&IP="+address) ;

		if (group!=null) {
			uriBuilder.append("&group="+group) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Update the root wildcard address for the listed domains and/or group
	 * @param address The IPv4 address to set as the rdata for the listed domains and groups
	 * @param domains (OPTIONAL) The list of domains to update the root wildcard for or <code>null</code>
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject updateRootWildcardForList(String address, String[] domains) {
		StringBuilder uriBuilder = new StringBuilder("/api/removeHostname/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&IP="+address) ;

		for (int x=0; x<domains.length; x++) {
			uriBuilder.append("&domains="+domains[x]) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Update the resource record identified by the rrId (As found by a call to getRRSetForHostname())
	 * @param rrId The resource record ID
	 * @param rdata The new response data to be set
	 * @param ttl (OPTIONAL) The Time-to-live to be set or <code>NULL</code> for the default TTL
	 * @param priority (OPTIONAL) The priority to set for MX/SRV records or <code>NULL</code> to leave unchanged.
	 * @param isWildcard (OPTIONAL) Set TRUE if this is a wildcard record, or <code>NULL</code> or FALSE.
	 * @param geoGroup The name of a GeoLocation group to assign for this resource record. (Can be NULL, Cannot be used with country/region/city)
	 * @param country The name of a country to assign for GeoLocation based responses (Can be NULL, Cannot be used with geoGroup)
	 * @param region The name of a region to assign for GeoLocation based responses (Can be NULL, Requires country to be set as well)
	 * @param city The name of a city to assign for GeoLocation based responses (Can be NULL, Requires region to be set as well)
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject updateRRData(Integer rrId, String rdata, Integer ttl, Integer priority, Boolean isWildcard, 
			Integer retry, Integer expire, Integer minimum, Integer weight, Integer port, String title, 
			String keywords, String description, String geoGroup, String country, String region, String city) {

		StringBuilder uriBuilder = new StringBuilder("/api/updateRRData/?") ;
		log.debug("Setting API Token") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&rr_id="+rrId) ;
		log.debug("Setting RR ID") ;
		uriBuilder.append("&rdata="+rdata) ;
		log.debug("Setting RR rdata") ;
		if (ttl!=null) {
			log.debug("Setting RR TTL") ;
			uriBuilder.append("&ttl="+ttl) ;
		}
		
		if (priority!=null) {
			log.debug("Setting RR priority") ;
			uriBuilder.append("&priority="+priority) ;
		}
		
		if (isWildcard.booleanValue()) {
			log.debug("Setting RR isWildcard") ;
			uriBuilder.append("&is_wildcard="+Boolean.toString(isWildcard)) ;
		}
		
		if (retry!=null) {
			log.debug("Setting RR retry") ;
			uriBuilder.append("&retry="+retry) ;
		}
		
		if (expire!=null) {
			log.debug("Setting RR expire") ;
			uriBuilder.append("&expire="+expire) ;
		}
		
		if (minimum!=null) {
			log.debug("Setting RR minimum") ;
			uriBuilder.append("&minimum="+minimum) ;
		}
		
		if (weight!=null) {
			log.debug("Setting RR weight") ;
			uriBuilder.append("&weight="+weight) ;
		}
		
		if (port!=null) {
			log.debug("Setting RR port") ;
			uriBuilder.append("&port="+port) ;
		}

		if (geoGroup!=null) {
			uriBuilder.append("&geoGroup=").append(geoGroup) ;
		}

		if (country!=null) {
			uriBuilder.append("&country=").append(country) ;
			if (region!=null) {
				uriBuilder.append("&region=").append(region) ;
				if (city!=null) {
					uriBuilder.append("&city=").append(city) ;
				}
			}
		}
		
		try {
			if (title!=null) {
				log.debug("Setting RR title") ;
				uriBuilder.append("&title="+URLEncoder.encode(title,"UTF-8")) ;
			}
			if (keywords!=null) {
				log.debug("Setting RR keywords") ;
				uriBuilder.append("&keywords="+URLEncoder.encode(keywords,"UTF-8")) ;
			}
			if (description!=null) {
				log.debug("Setting RR description") ;
				uriBuilder.append("&description="+URLEncoder.encode(description,"UTF-8")) ;
			}
		} catch (UnsupportedEncodingException e) {
			// This catch block should never be reached on any normal Java platform.
			log.error("UnsupportedEncodingException while attempting to URL Encode text", e) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Create a new XFR configuration for the named domain/sub
	 * @param domain The name of the domain to create an XFR configuration for
	 * @param host This host indicates the "sub" domain that the XFR will work on, set as NULL to create XFR for the root of the domain (Can be null)
	 * @param master The IP address of the master server which we will poll to get updates and we will receive NOTIFY packets from
	 * @param port The UDP/TCP port on which to perform the XFR transfer with the master, set to NULL to use the default of 53 (Can be null)
	 * @param refresh_interval The initial refresh interval with which to perform zone transfers, set to NULL to use the default of 3600 seconds (Can be null)
	 * @return A {@link JSONObject} containing the JSON response from the API server
	 */
	public JSONObject createXfrZone(String domain, String host, String master, Integer port, Integer refresh_interval) {

		StringBuilder uriBuilder = new StringBuilder("/api/createXfrZone/?") ;
		log.debug("Setting API Token") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (domain==null) {
			JSONObject error = new JSONObject() ;
			try {
				JSONObject meta = new JSONObject() ;
				meta.put("success", 0) ;
				meta.put("error", "Required argument 'domain' is NULL") ;
				error.put("meta", meta) ;
			} catch (JSONException e) {
				log.error("An error was encountered while attempting to return an error message.", e) ;
			}
			return error ;
		} else {
			if (domain.length()<3) {
				try {
					JSONObject error = new JSONObject() ;
					JSONObject meta = new JSONObject() ;
					meta.put("success", 0) ;
					meta.put("error", "The 'domain' argument is too small to be a valid domain.") ;
					error.put("meta", error) ;
					return error ;
				} catch (JSONException e) {
					log.error("An error was encountered while attempting to return an error message.", e) ;
				}
			} else {
				uriBuilder.append("&domain=").append(domain) ;
			}
		}

		if (host!=null) {
			if (!host.contentEquals("") && !host.toLowerCase().contentEquals("null")) {
				uriBuilder.append("&host=").append(host) ;
			}
		}

		if (master!=null) {
			try {
				InetAddress.getByName(master) ;
				uriBuilder.append("&master=").append(master) ;
			} catch (UnknownHostException uhe) {
				try {
					JSONObject error = new JSONObject() ;
					JSONObject meta = new JSONObject() ;
					meta.put("success", 0) ;
					meta.put("error", "The value '"+master+"' for the 'master' server is not a valid IP address.") ;
					error.put("meta", meta) ;
					return error ;
				} catch (JSONException jsone) {
					log.error("An error was encountered while attempting to return an error message.", jsone) ;
				}
			}
		} else {
			try {
				JSONObject error = new JSONObject() ;
				JSONObject meta = new JSONObject() ;
				meta.put("success", 0) ;
				meta.put("error", "The required argument 'master' is NULL") ;
				error.put("meta", error) ;
				return error ;
			} catch (JSONException jsone) {
				log.error("An error was encountered while attempting to return an error message.", jsone) ;
			}
		}

		if (port!=null) {
			uriBuilder.append("&port=").append(port) ;
		}

		if (refresh_interval!=null) {
			uriBuilder.append("&refresh_interval=").append(refresh_interval) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * 
	 * @param filter A {@link String} which is used as a case insensitive filter for the city names (Can be null)
	 * @param countryCode An {@link Integer} which is the ID of a country from the countries list (Can be null)
	 * @param regionCode An {@link Integer} which is the ID of a region from the regions list (Can be null)
	 * @param limit An {@link Integer} which indicates the maximum number of results to return (Can be null)
	 * @param offset An {@link Integer} which indicates the offset at which to start a set of return values (Can be null)
	 * @param orderBy A {@link String} which indicates which field the list should be sorted by (Can be null)
	 * @param direction A {@link String} either "ASC" or "DESC" which indicates the direction to sort the results in (Can be null)
	 * @return A {@link JSONObject} which contains the result status and either error details or returned data
	 */
	public JSONObject getCityList(String filter, Integer countryCode, Integer regionCode, Integer limit, Integer offset, String orderBy, String direction) {

		StringBuilder uriBuilder = new StringBuilder("/api/getCityList?") ;
		log.debug("Setting API Token") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (filter!=null) {
			uriBuilder.append("&filter=").append(filter) ;
		}

		if (countryCode!=null) {
			uriBuilder.append("&countryCode=").append(countryCode) ;
		}

		if (regionCode!=null) {
			uriBuilder.append("&regionCode=").append(regionCode) ;
		}

		if (limit!=null) {
			uriBuilder.append("&limit=").append(limit) ;
		}

		if (offset!=null) {
			uriBuilder.append("&offset=").append(offset) ;
		}

		if (orderBy!=null) {
			uriBuilder.append("&order_by=").append(orderBy) ;
		}

		if (direction!=null) {
			if (direction.toLowerCase().contentEquals("ASC") || direction.toLowerCase().contentEquals("DESC")) {
				uriBuilder.append("&direction=").append(direction.toUpperCase()) ;
			}
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Returns a {@link JSONObject} containing a list of Countries which match the specified filter
	 * @param filter A {@link String} which is used as a case insensitive filter for the country names/codes (i.e. 'US' will return US and aUStralia and aUStria etc... - Can be null)
	 * @param limit An {@link Integer} which indicates the maximum number of results to return (Can be null and defaults to 100)
	 * @param offset An {@link Integer} which indicates the offset at which to start a set of return values (Can be null and defaults to 0)
	 * @param orderBy A {@link String} which indicates which field the list should be sorted by (Can be null and defaults to the name of the country)
	 * @param direction A {@link String} either "ASC" or "DESC" which indicates the direction to sort the results in (Can be null and defaults to DESC)
	 * @return
	 */
	public JSONObject getCountryList(String filter, Integer limit, Integer offset, String orderBy, String direction) {

		StringBuilder uriBuilder = new StringBuilder("/api/getCountryList?") ;
		log.debug("Setting API Token") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (filter!=null) {
			uriBuilder.append("&filter=").append(filter) ;
		}

		if (limit!=null) {
			uriBuilder.append("&limit=").append(limit) ;
		}

		if (offset!=null) {
			uriBuilder.append("&offset=").append(offset) ;
		}

		if (orderBy!=null) {
			uriBuilder.append("&order_by=").append(orderBy) ;
		}

		if (direction!=null) {
			if (direction.toLowerCase().contentEquals("ASC") || direction.toLowerCase().contentEquals("DESC")) {
				uriBuilder.append("&direction=").append(direction.toUpperCase()) ;
			}
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Returns a {@link JSONObject} containing a list of Regions which match the specified filter
	 * @param filter A {@link String} which is used as a case insensitive filter for the region names/codes (i.e. 'ND' will return 'North Dakota' and 'MarylaND' etc... - Can be null)
	 * @param limit An {@link Integer} which indicates the maximum number of results to return (Can be null)
	 * @param offset An {@link Integer} which indicates the offset at which to start a set of return values (Can be null)
	 * @param orderBy A {@link String} which indicates which field the list should be sorted by (Can be null)
	 * @param direction A {@link String} either "ASC" or "DESC" which indicates the direction to sort the results in (Can be null)
	 * @return A {@link JSONObject} which contains the result status and either error details or returned data
	 */
	public JSONObject getRegionList(String filter, Integer countryCode, Integer limit, Integer offset, String orderBy, String direction) {

		StringBuilder uriBuilder = new StringBuilder("/api/getCityList?") ;
		log.debug("Setting API Token") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (filter!=null) {
			uriBuilder.append("&filter=").append(filter) ;
		}

		if (countryCode!=null) {
			uriBuilder.append("&countryCode=").append(countryCode) ;
		}

		if (limit!=null) {
			uriBuilder.append("&limit=").append(limit) ;
		}

		if (offset!=null) {
			uriBuilder.append("&offset=").append(offset) ;
		}

		if (orderBy!=null) {
			uriBuilder.append("&order_by=").append(orderBy) ;
		}

		if (direction!=null) {
			if (direction.toLowerCase().contentEquals("ASC") || direction.toLowerCase().contentEquals("DESC")) {
				uriBuilder.append("&direction=").append(direction.toUpperCase()) ;
			}
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Return a list of all XFR configurations for a given zone.
	 * @param domain The name of the zone for which the list of XFR configurations is desired.
	 * @return A {@link JSONObject} containing the result status and either an error message or data as detailed below<br /><pre>
	 * {
	 * 		"meta": {
	 * 			"code": 200, 
	 * 			"success": 1
	 * 		}, 
	 * 		"data": [
	 * 			{
	 * 				"xfr_type": 1, 
	 * 				"master": "1.2.3.4", 
	 * 				"zone": "1voiceministries.net", 
	 * 				"last_transfer": "2012-01-24 10:43:00", 
	 * 				"domain_id": 694732, 
	 * 				"serial": 1, 
	 * 				"refresh_interval": 1200, 
	 * 				"id": 6, 
	 * 				"port": 53
	 * 			}
	 * 		]
	 * }
	 * </pre>
	 * or
	 * <pre>
	 * {
	 * 		"meta": {
	 * 			"code": 400, 
	 * 			"success": 0, 
	 * 			"error": "Domain name not specified"
	 * 		}
	 * }
	 * </pre>
	 */
	public JSONObject getXfrForZone(String domain) {
		StringBuilder uriBuilder = new StringBuilder("/api/getXfrForZone?") ;
		log.debug("Setting API Token") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (domain!=null) {
			uriBuilder.append("?domainname=").append(domain) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Remove the XFR settings for a given domain (and optional sub host)
	 * @param domain The name of the domain for which the XFR settings will be removed.
	 * @param host If the XFR settings are on a sub-domain, this would be the hostname of the sub-domain (Can be null for the root of the domain)
	 * @returnA {@link JSONObject} containing the result status and either an error message.
	 */
	public JSONObject removeXfrZone(String domain, String host) {
		StringBuilder uriBuilder = new StringBuilder("/api/removeXfrZone?") ;
		log.debug("Setting API Token") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (domain!=null) {
			uriBuilder.append("?domainname=").append(domain) ;
		}

		if (host!=null) {
			uriBuilder.append("?host=").append(host) ;
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	
}
