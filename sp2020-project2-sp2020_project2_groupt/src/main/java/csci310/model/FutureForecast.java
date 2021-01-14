package csci310.model;

import java.util.Date;

public class FutureForecast {
	private Date date;
	private float maxTemp;
	private float minTemp;
	private String desc;
	private String icon;
	
	public FutureForecast(Date date, float maxT, float minT, String desc, String icon) {
		this.date = date;
		maxTemp = maxT;
		minTemp = minT;
		this.desc = desc;
		this.icon = icon;
	}
	
	public Date getDate() {
		return date;
	}
	public float getMaxTemp() {
		return maxTemp;
	}
	public float getMinTemp() {
		return minTemp;
	}
	public String getDesc() {
		return desc;
	}
	public String getIcon() {
		return icon;
	}
}