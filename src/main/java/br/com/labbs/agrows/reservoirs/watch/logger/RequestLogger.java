package br.com.labbs.agrows.reservoirs.watch.logger;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class RequestLogger implements ClientRequestFilter {

	@Override
	public void filter(final ClientRequestContext requestContext) throws IOException {
		System.out.println(requestContext.getMethod() + " " + requestContext.getUri());
		//System.out.println("String Headers: " + requestContext.getStringHeaders());
		//System.out.println("Body: " + requestContext.getEntity());
	}

}
