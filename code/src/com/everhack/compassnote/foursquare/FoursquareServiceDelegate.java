package com.everhack.compassnote.foursquare;

public interface FoursquareServiceDelegate {
	void receivedResponse(Object response);
	void requestFailed();
}
