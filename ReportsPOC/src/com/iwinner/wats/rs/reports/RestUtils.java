package com.iwinner.wats.rs.reports;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;

public class RestUtils {
	public static String HTTP_HEADER_USERNAME="anji";
	public static String HTTP_HEADER_PASSWORD="anji";
	public static String listOfUsers(){
		String responseOfUsers=null;
		
		try{
		
		ClientConfig clientConfig = new DefaultClientConfig();
		
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		Client client = Client.create(clientConfig);
		
		final HTTPBasicAuthFilter httpBasicAuthFilter=new HTTPBasicAuthFilter(HTTP_HEADER_USERNAME,HTTP_HEADER_PASSWORD);
		
		  client.addFilter(httpBasicAuthFilter);
		
		  //WebResource webResource = client.resource("http://localhost:9090/JerseyJSONExample/rest/jsonServices/send");
		
		  WebResource webResource = client.resource("http://localhost:2929/aspService/rest/iwinnerService/spotifyJSONList/60125366358?key=fd3ebf59-3ed8-424f-a99d-5cfad4337d94");
		
		  ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
		
		  responseOfUsers = response.getEntity(String.class);

		}catch(Exception e){
		
			e.printStackTrace();
			
	   }
		return responseOfUsers;
	}

}
