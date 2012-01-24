package com.dns.api.compiletime;

import org.json.JSONObject;

public class GeoAPI extends GenericAPI {

	/**
	 * Constructor
	 * @param apiHost The host name of the server to make API calls against.
	 * @param useSSL Should we use HTTPS connections for API calls?
	 * @param apiToken The API Token for authenticating requests.
	 */
	public GeoAPI(String apiHost, boolean useSSL, String apiToken) {
		super(apiHost, useSSL, apiToken) ;
	}

	/**
	 * Add a geo location to an existing GeoGroup
	 * @param name (REQUIRED) A {@link String} containing the name of the geogroup to be modified
	 * @param isoCode (REQUIRED) A {@link String} containing the 2 character ISO country code of the country to be added as part of this location 
	 * @param region (OPTIONAL) A {@link String} containing the name of the region to be added as part of this location (Without a region, a city is invalid)
	 * @param city (OPTIONAL) A {@link String} containing the name of the city to be added as part of this location (A region is required in order to specify a city)
	 * @return A {@link JSONObject} containing the result status of the action as detailed below:<br /><pre>
	 * {"meta":
	 * 		{
	 * 			"code": 200, 
	 * 			"id": 22, 
	 * 			"success": 1
	 * 		}
	 * }
	 * </pre> or <pre>
	 * {"meta": 
	 * 		{
	 * 			"code": 400, 
	 * 			"success": 0, 
	 * 			"error": "Name and ISO2 code are both required"
	 * 		}
	 * }
	 * </pre>
	 */
	public JSONObject appendToGeoGroup(String name, String isoCode, String region, String city) {
		StringBuilder uriBuilder = new StringBuilder("/api/appendToGeoGroup/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (name!=null) {
			if (!("null".contentEquals(name.toLowerCase()))) {
				uriBuilder.append("&name=") ;
				uriBuilder.append(name) ;
			}
		}

		if (isoCode!=null) {
			if (!("null".contentEquals(isoCode.toLowerCase()))) {
				uriBuilder.append("&iso2_code=") ;
				uriBuilder.append(isoCode) ;
			}
		}

		if (region!=null) {
			if (!("null".contentEquals(region.toLowerCase()))) {
				uriBuilder.append("&region=") ;
				uriBuilder.append(region) ;

				// Only try to add a city if a region was specified
				if (city!=null) {
					if (!("null".contentEquals(city.toLowerCase()))) {
						uriBuilder.append("&city=") ;
						uriBuilder.append(city) ;
					}
				}
			}
		}

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Return the details about a specific GeoGroup.
	 * @param group The name of the geogroup to get the details for.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject getGeoGroupDetails(String name) {
		StringBuilder uriBuilder = new StringBuilder("/api/getGeoGroupDetails/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&name="+name) ;

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Return a list of GeoGroups which match the specified filter string.
	 * @param filter A case insensitive search filter or "" for all.
	 * @return A {@link JSONObject} containing the JSON response or an error code.
	 */
	public JSONObject getGeoGroups(String filter) {
		StringBuilder uriBuilder = new StringBuilder("/api/getGeoGroups/?") ;
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		uriBuilder.append("&search_term="+filter) ;

		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * Return a list of countries which match the provided filter information
	 * @param filter (OPTIONAL) A filter to limit which countries are returned. The filter applies to the 2 character country code as well as the country name.
	 * @param limit (OPTIONAL) An {@link Integer} representing the maximum number of results to return 
	 * @param offset (OPTIONAL) An {@link Integer} representing the offset from the start of the list from which to return results (i.e. if the result list contains 500 items and the limit is 30 and the offset is 10, return items 11-40)
	 * @param order_by (OPTIONAL) The name of the field by which to sort. Can be one of [id,iso_code,iso_num,name,continent_code]
	 * @param direction (OPTIONAL) A {@link String} which contains either "ASC" or "DESC" to determine the direction to sort the results (Defaults to DESC)
	 * @return A {@link JSONObject} containing the result data as exemplified below: <br /><pre>
	 * 	{"meta" : {
	 * 		"success" : 1,
	 * 		"id" : 3233,
	 * 		"data": [{
	 * 				"id"				: 33,
	 * 				"iso_code"			: "US",
	 * 				"iso_num"			: 840,
	 * 				"name"				: "United States",
	 * 				"continent_code"	: "NA"
	 * 			},{
	 * 				"id"				: 54,
	 * 				"iso_code"			: "CN",
	 * 				"iso_num"			: 156,
	 * 				"name"				: "China",
	 * 				"continent_code"	: "AS"
	 * 			}]
	 * 		}
	 * 	}
	 * </pre>
	 */
	public JSONObject getCountryList(String filter, Integer limit, Integer offset, String order_by, String direction) {
		StringBuilder uriBuilder = new StringBuilder("/api/getCountryList/?") ;
		if (apiToken==null) {
			apiToken="" ;
		}
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (filter!=null) {
			if (!("null".contentEquals(filter.toLowerCase()))) {
				uriBuilder.append("&") ;
				uriBuilder.append("filter=") ;
				uriBuilder.append(filter) ;
			}
		}

		if (limit!=null) {
			uriBuilder.append("&") ;
			uriBuilder.append("limit=") ;
			uriBuilder.append(limit) ;
		}

		if (offset!=null) {
			uriBuilder.append("&") ;
			uriBuilder.append("offset=") ;
			uriBuilder.append(offset) ;
		}

		if (order_by!=null) {
			if (!("null".contentEquals(order_by.toLowerCase()))) {
				uriBuilder.append("&") ;
				uriBuilder.append("order_by=") ;
				uriBuilder.append(order_by.toLowerCase()) ;
			}
		}

		if (direction!=null) {
			if (!("null".contentEquals(direction.toLowerCase()))) {
				uriBuilder.append("&") ;
				uriBuilder.append("direction=") ;
				uriBuilder.append(direction.toLowerCase()) ;
			}
		}
		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * 
	 * @param filter (OPTIONAL) A filter to limit which countries are returned. The filter applies to the 2 character country code as well as the country name.
	 * @param limit (OPTIONAL) An {@link Integer} representing the maximum number of results to return 
	 * @param offset (OPTIONAL) An {@link Integer} representing the offset from the start of the list from which to return results (i.e. if the result list contains 500 items and the limit is 30 and the offset is 10, return items 11-40)
	 * @param order_by (OPTIONAL) The name of the field by which to sort. Can be one of [id,iso_code,iso_num,name,continent_code]
	 * @param direction (OPTIONAL) A {@link String} which contains either "ASC" or "DESC" to determine the direction to sort the results (Defaults to DESC)
	 * @param countryCode (OPTIONAL) A {@link String} representing either the 2 character ISO country code or the integer country code
	 * @return A {@link JSONObject} containing the result data as exemplified below: <br /><pre>
	 * 	{"meta" : {
	 * 		"success" : 1,
	 * 		"id" : 3233,
	 * 		"data": [{
	 * 					"id"			: 185,
	 * 					"country_id"	: 33,
	 * 					"code"			: "KY",
	 * 					"name"			: "Kentucky"
	 * 				},{
	 *					"id"			: 170,
	 * 					"country_id"	: 33,
	 * 					"code"			: "IN",
	 * 					"name"			: "Indiana"
	 * 				}]
	 * 		}
	 * 	}
	 * </pre>
	 */
	public JSONObject getRegionList(String filter, String countryCode, Integer limit, Integer offset, String order_by, String direction) {
		StringBuilder uriBuilder = new StringBuilder("/api/getRegionList/?") ;
		if (apiToken==null) {
			apiToken="" ;
		}
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (filter!=null) {
			if (!("null".contentEquals(filter.toLowerCase()))) {
				uriBuilder.append("&") ;
				uriBuilder.append("filter=") ;
				uriBuilder.append(filter) ;
			}
		}

		if (countryCode!=null) {
			if (!("null".contentEquals(countryCode.toLowerCase()))) {
				uriBuilder.append("&") ;
				uriBuilder.append("countryCode=") ;
				uriBuilder.append(countryCode) ;
			}
		}

		if (limit!=null) {
			uriBuilder.append("&") ;
			uriBuilder.append("limit=") ;
			uriBuilder.append(limit) ;
		}

		if (offset!=null) {
			uriBuilder.append("&") ;
			uriBuilder.append("offset=") ;
			uriBuilder.append(offset) ;
		}

		if (order_by!=null) {
			if (!("null".contentEquals(order_by.toLowerCase()))) {
				uriBuilder.append("&") ;
				uriBuilder.append("order_by=") ;
				uriBuilder.append(order_by.toLowerCase()) ;
			}
		}

		if (direction!=null) {
			if (!("null".contentEquals(direction.toLowerCase()))) {
				uriBuilder.append("&") ;
				uriBuilder.append("direction=") ;
				uriBuilder.append(direction.toLowerCase()) ;
			}
		}
		return makeHttpRequest(uriBuilder.toString()) ;
	}

	/**
	 * 
	 * @param filter (OPTIONAL) A filter to limit which countries are returned. The filter applies to the 2 character country code as well as the country name.
	 * @param limit (OPTIONAL) An {@link Integer} representing the maximum number of results to return 
	 * @param offset (OPTIONAL) An {@link Integer} representing the offset from the start of the list from which to return results (i.e. if the result list contains 500 items and the limit is 30 and the offset is 10, return items 11-40)
	 * @param order_by (OPTIONAL) The name of the field by which to sort. Can be one of [id,iso_code,iso_num,name,continent_code]
	 * @param direction (OPTIONAL) A {@link String} which contains either "ASC" or "DESC" to determine the direction to sort the results (Defaults to DESC)
	 * @param countryCode (OPTIONAL) A {@link String} representing either the 2 character ISO country code or the integer country code
	 * @param regionCode (OPTIONAL) An {@link Integer} representing the region ID which contains the cities to be returned
	 * @return A {@link JSONObject} containing the result data as exemplified below: <br /><pre>
		{"meta" : {
			"success" : 1,
			"id" : 3233,
				"data": [{
						"id"			: 3627350,
						"name"			: "Louisville",
						"country_id"	: 33,
						"region_id"		: 185,
						"postal_code"	: 40202,
						"latitude"		: 38.2517,
						"longitude"		: -85.7544,
						"metro_code"	: 529,
						"area_code"		: 502,
						"charset"		: 0,
					},{
						"id"			: 3724834,
						"name"			: "Jeffersonville",
						"country_id"	: 33,
						"region_id"		: 170,
						"postal_code"	: 47130,
						"latitude"		: 38.3283,
						"longitude"		: -85.6957,
						"metro_code"	: 529,
						"area_code"		: 812,
						"charset"		: 0,
					}]
			}
		}
	 * </pre>
	 */
	public JSONObject getCityList(String filter, String countryCode, Integer regionCode, Integer limit, Integer offset, String order_by, String direction) {
		StringBuilder uriBuilder = new StringBuilder("/api/getRegionList/?") ;
		if (apiToken==null) {
			apiToken="" ;
		}
		uriBuilder.append("AUTH_TOKEN="+apiToken) ;

		if (filter!=null) {
			if (!("null".contentEquals(filter.toLowerCase()))) {
				uriBuilder.append("&") ;
				uriBuilder.append("filter=") ;
				uriBuilder.append(filter) ;
			}
		}

		if (countryCode!=null) {
			if (!("null".contentEquals(countryCode.toLowerCase()))) {
				uriBuilder.append("&") ;
				uriBuilder.append("countryCode=") ;
				uriBuilder.append(countryCode) ;
			}
		}

		if (regionCode!=null) {
			uriBuilder.append("&") ;
			uriBuilder.append("regionCode=") ;
			uriBuilder.append(regionCode) ;
		}

		if (limit!=null) {
			uriBuilder.append("&") ;
			uriBuilder.append("limit=") ;
			uriBuilder.append(limit) ;
		}

		if (offset!=null) {
			uriBuilder.append("&") ;
			uriBuilder.append("offset=") ;
			uriBuilder.append(offset) ;
		}

		if (order_by!=null) {
			if (!("null".contentEquals(order_by.toLowerCase()))) {
				uriBuilder.append("&") ;
				uriBuilder.append("order_by=") ;
				uriBuilder.append(order_by.toLowerCase()) ;
			}
		}

		if (direction!=null) {
			if (!("null".contentEquals(direction.toLowerCase()))) {
				uriBuilder.append("&") ;
				uriBuilder.append("direction=") ;
				uriBuilder.append(direction.toLowerCase()) ;
			}
		}
		return makeHttpRequest(uriBuilder.toString()) ;
	}
}
