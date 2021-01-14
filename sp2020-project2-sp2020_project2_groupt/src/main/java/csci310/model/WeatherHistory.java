package csci310.model;

public class WeatherHistory {
	private float low;
	private float high;
	
	public WeatherHistory(float low, float high) {
		this.low = low;
		this.high = high;
	}
	
	public float getLow() {
		return low;
	}
	public float getHigh() {
		return high;
	}
}
