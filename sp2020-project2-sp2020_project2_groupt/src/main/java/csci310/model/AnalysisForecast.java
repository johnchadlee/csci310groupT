package csci310.model;

import java.util.Date;
import java.util.List;

public class AnalysisForecast extends SearchForecast {
	private List<FutureForecast> next5Days;
	private List<WeatherHistory> historicalTemps;
	public AnalysisForecast(String location, Date date, float temp, List<FutureForecast> n5D, List<WeatherHistory> hTs, String desc, String icon) {
		super(location, date, temp, desc, icon);
		next5Days = n5D;
		historicalTemps = hTs;
	}
	
	public List<FutureForecast> getNext5Days(){
		return next5Days;
	}
	public List<WeatherHistory> getHistoricalTemps(){
		return historicalTemps;
	}
	public void prettyPrint() {
		System.out.println("Location: " + this.getLocation() + " at " + this.getDate().toString() + " | Temperature: " + Float.toString(this.getTemperatureRange()) + " and " + this.getDesc());
		System.out.println("Next 5 Days: ");
		for(int i=0;i<next5Days.size();i++) {
			FutureForecast ff = next5Days.get(i);
			System.out.println(" - Day " + i + " High: " + Float.toString(ff.getMaxTemp()));
			System.out.println(" - Day " + i + " Low: " + Float.toString(ff.getMinTemp()));
		}
		System.out.println("...");
		System.out.println("Last 12 Months: ");
		for(int i=0;i<historicalTemps.size();i++) {
			WeatherHistory wh = historicalTemps.get(i);
			System.out.println(" - Month " + i + " High: " + Float.toString(wh.getHigh()));
			System.out.println(" - Month " + i + " Low: " + Float.toString(wh.getLow()));
		}
	}
}
