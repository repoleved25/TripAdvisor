package androidlistview;

import java.io.Serializable;

/**
 * @author Gundu
 *
 */
@SuppressWarnings("serial")
public class Insertclass implements Serializable{
	private int mintime,maxtime;
	private String name,type;
	private Double lat;
	private Double lon;
	
	String getName()
	{
		return name;
	}
	void setName(String name)
	{
		this.name=name;
	}
	Double getLat()
	{
		return lat;
	}
	void setLat(Double lat)
	{
		this.lat=lat;
	}
	Double getLon()
	{
		return lon;
	}
	void setLon(Double lon)
	{
		this.lon=lon;
	}
	public int getMintime() {
		return mintime;
	}
	public void setMintime(int mintime) {
		this.mintime = mintime;
	}
	public int getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(int maxtime) {
		this.maxtime = maxtime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
