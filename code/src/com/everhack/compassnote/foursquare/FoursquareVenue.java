package com.everhack.compassnote.foursquare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FoursquareVenue {
	
	private String id;
	private String name;
	private FoursquareLocation location;
	private String category;
	private Long checkinCount;
	private Long usersCount;
	private Long tipCount;
	
	public FoursquareVenue(JSONObject json) {
		try {
			this.id = json.getString("id");
			this.name = json.getString("name");
			this.location = new FoursquareLocation(json.getJSONObject("location"));
			
			JSONArray categories = json.getJSONArray("categories");
			if (categories != null && categories.length() > 0) {
				JSONObject category = (JSONObject) categories.get(0);
				if (category != null) {
					this.category = category.getString("name");
				}
			}
			
			JSONObject stats = json.getJSONObject("stats");
			if (stats != null) {
				this.checkinCount = stats.getLong("checkinsCount");
				this.usersCount = stats.getLong("usersCount");
				this.tipCount = stats.getLong("tipCount");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FoursquareLocation getLocation() {
		return location;
	}

	public void setLocation(FoursquareLocation location) {
		this.location = location;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getCheckinCount() {
		return checkinCount;
	}

	public void setCheckinCount(Long checkinCount) {
		this.checkinCount = checkinCount;
	}

	public Long getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(Long usersCount) {
		this.usersCount = usersCount;
	}

	public Long getTipCount() {
		return tipCount;
	}

	public void setTipCount(Long tipCount) {
		this.tipCount = tipCount;
	}
}
