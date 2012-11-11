package com.everhack.compassnote.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Roteiro {

	private List<Object> places;
	private List<Checklist> checklist;
	private String hotel;
	private Date startDate;
	private Date returnDate;
	private String location;
	private String city;
	private String state;
	private String country;
	private String metroMap;
	private String cityPhoto;

	public List<Object> getPlaces() {
		return places;
	}
	public void setPlaces(List<Object> places) {
		this.places = places;
	}
	public List<Checklist> getChecklist() {
		return checklist;
	}
	public void setChecklist(List<Checklist> checklist) {
		this.checklist = checklist;
	}
	public String getHotel() {
		return hotel;
	}
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMetroMap() {
		return metroMap;
	}
	public void setMetroMap(String metroMap) {
		this.metroMap = metroMap;
	}
	public String getCityPhoto() {
		return cityPhoto;
	}
	public void setCityPhoto(String cityPhoto) {
		this.cityPhoto = cityPhoto;
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

	public String toEvernote(){
		return "";
	}

	public String toHTML(){
		return "";
	}

	public String getDataString(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		if(startDate != null && returnDate != null)
			return sdf.format(startDate)+" - "+sdf.format(returnDate);
		else
			return "";
	}

}