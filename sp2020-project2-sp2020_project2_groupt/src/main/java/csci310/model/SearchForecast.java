package csci310.model;

import java.util.Date;

public class SearchForecast {
	private String location;
	private Date date;
	private float temp;
	private String desc;
	private String icon;
	
	public SearchForecast(String location, Date date, float temp, String desc, String icon) {
		this.location = location;
		this.date = date;
		this.temp = temp;
		this.desc = desc;
		this.icon = icon;
	}
	
	public float getTemperatureRange() {
		return temp;
	}
	
	public String getLocation() {
		return location;
	}
	public Date getDate() {
		return date;
	}
	public String getDesc() {
		return desc;
	}
	public String getIcon() {
		return icon;
	}
	
	public void prettyPrint() {
		System.out.println("Location: " + this.getLocation() + " at " + this.getDate().toString() + " | Temperature: " + Float.toString(this.getTemperatureRange()) + " and " + this.getDesc());
	}
	
}
