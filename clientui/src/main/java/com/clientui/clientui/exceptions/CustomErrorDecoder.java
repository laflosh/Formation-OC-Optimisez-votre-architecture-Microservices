package com.clientui.clientui.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

	private ErrorDecoder defaultErrorDecoder = new Default();
	
	@Override
	public Exception decode(String invoqueur, Response response) {
		
		if(response.status() == 400) {
			
			return new ProductBadRequestException(
				"requete incorrecte"
			);
			
		};
		
		return defaultErrorDecoder.decode(invoqueur, response);
	}

	
	
}
