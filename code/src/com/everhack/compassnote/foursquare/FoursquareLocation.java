package com.everhack.compassnote.foursquare;

import org.json.JSONException;
import org.json.JSONObject;

public class FoursquareLocation {
	private String address;
	private Double lat;
	private Double lng;
	private String city;
	private String state;
	private String country;
	
	public FoursquareLocation(JSONObject json) {
		try {
			json.has("address");
			this.address = json.getString("address");
			this.lat = json.getDouble("lat");
			this.lng = json.getDouble("lng");
			this.city = json.getString("city");
			this.state = json.getString("state");
			this.country = json.getString("country");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}
