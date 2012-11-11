package com.everhack.compassnote.foursquare;


public interface FoursquareServiceDelegate<T> {
	void receivedResponse(T response);	
	void requestFailed();

}
