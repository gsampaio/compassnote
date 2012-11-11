package com.everhack.compassnote.foursquare;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;



public class FoursquareVenueService extends FoursquareService {

	public static void getVenuesInCity(String city, final FoursquareServiceDelegate<List<FoursquareVenue>> delegate) {
		RequestParams params = new RequestParams();
    	params.put("near", city);
    	params.put("categoryId", "4bf58dd8d48988d12d941735");
    	params.put("oauth_token", "4CMG2JAPIUAGEGN1T3433QYRM3Z5UYKXNCFSEAINNC440402");
    	params.put("v", "20121110");
    	AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
    		@Override
    		public void onSuccess(String arg0) {
    			// TODO Auto-generated method stub
    			super.onSuccess(arg0);
    			List<FoursquareVenue> venuesList = new ArrayList<FoursquareVenue>();
    			try {
					JSONObject json = new JSONObject(arg0);
					JSONObject response = json.getJSONObject("response");
					JSONArray venues = response.getJSONArray("venues");
					for (int i = 0; i < venues.length(); i++) {
						JSONObject venueJSON = (JSONObject) venues.get(i);
						FoursquareVenue venue = new FoursquareVenue(venueJSON);
						if (venue.getLocation() != null) {
							venuesList.add(venue);
						}
						
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
    			delegate.receivedResponse(venuesList);
    		}
    		
    		@Override
    		public void onFailure(Throwable arg0) {
    			delegate.requestFailed();
    		}
    	};
        
    	client.get(BASE_URL + "/venues/search", params, handler);
    }

// 
//	
//	public void getVenuesInCity(String city) {
//    	RequestParams params = new RequestParams();
//    	params.put("near", city);
//    	params.put("categoryId", "4bf58dd8d48988d12d941735");
//    	params.put("oauth_token", "4CMG2JAPIUAGEGN1T3433QYRM3Z5UYKXNCFSEAINNC440402");
//    	params.put("v", "20121110");
//    	AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
//    		@Override
//    		public void onSuccess(String arg0) {
//    			// TODO Auto-generated method stub
//    			super.onSuccess(arg0);
//    			List<FoursquareVenue> venuesList = new ArrayList<FoursquareVenue>();
//    			try {
//					JSONObject json = new JSONObject(arg0);
//					JSONObject response = json.getJSONObject("response");
//					JSONArray venues = response.getJSONArray("venues");
//					for (int i = 0; i < venues.length(); i++) {
//						JSONObject venueJSON = (JSONObject) venues.get(i);
//						FoursquareVenue venue = new FoursquareVenue(venueJSON);
//						if (venue.getLocation() != null) {
//							venuesList.add(venue);
//						}
//						
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//    			delegate.receivedResponse(venuesList);
//    		}
//    		
//    		@Override
//    		public void onFailure(Throwable arg0) {
//    			delegate.requestFailed();
//    		}
//    	};
//        
//    	client.get(BASE_URL + "/venues/search", params, handler);
//    }
	
}