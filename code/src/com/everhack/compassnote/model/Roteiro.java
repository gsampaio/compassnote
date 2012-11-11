package com.everhack.compassnote.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.everhack.compassnote.foursquare.FoursquareVenue;

public class Roteiro {

	private List<FoursquareVenue> places;
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

	public List<FoursquareVenue> getPlaces() {
		return places;
	}
	public void setPlaces(List<FoursquareVenue> places) {
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
		setMetroMap("http://www.amadeus.net/home/new/subwaymaps/en/maps_gifs/"+(getCity().replace(" ", "-").toLowerCase().replace("á", "a").replace("ã", "a"))+"-map.gif");
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
	    String noteStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
                "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">"+
                "<en-note>"+toHTML()+"</en-note>";

		return noteStr;
	}

	public String toHTML(){
	    String noteStr = "<h1>"+getCity()+"</h1>";

        if(getChecklist() != null && getChecklist().size() > 0){
            noteStr += "<h2 style=\"margin: 20px 0 12px;\">Roteiro</h2>"+
            "<ul style=\"list-style: none; padding: 0 10px; margin: 5px 0 20px;\">";
            for (FoursquareVenue v : getPlaces()) {
                noteStr += "<li style=\"margin-bottom: 10px;\">"+v.getName()+"<span style=\"display: block; clear: both; font-size: 12px; color: #666;\">"+v.getCategory()+"</span></li>";
            }

            noteStr += "</ul>";
        }

        if(getChecklist() != null && getChecklist().size() > 0){
            noteStr += "<h2 style=\"margin: 20px 0 12px;\">Checklist</h2>"+
                    "<ul style=\"list-style: none; padding: 0 10px; margin: 5px 0 20px;\">";

            for (Checklist chk : getChecklist()) {
                noteStr += "<li> - "+chk.getValue()+"</li>";
            }
            noteStr += "</ul>";
        }

        if(getCityPhoto() != null && !getCityPhoto().equals("")){
            noteStr += "<h2 style=\"margin: 20px 0 12px;\">Foto</h2>"+
                    "<div style=\"text-align: center;\">"+
                    "<img src="+getCityPhoto()+" style=\"margin: 0 auto; width: 90%;\" />"+
                    "</div>";
        }

        if(getMetroMap() != null && !getMetroMap().equals("")){
            noteStr += "<h2 style=\"margin: 20px 0 12px;\">Metro</h2>"+
                    "<div style=\"text-align: center;\">"+
                    "<img src="+getMetroMap()+" style=\"margin: 0 auto; width: 90%;\" />"+
                    "</div>";
        }
		return noteStr;
	}

	public String getDataString(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		if(startDate != null && returnDate != null)
			return sdf.format(startDate)+" - "+sdf.format(returnDate);
		else
			return "";
	}

}