package androidlistview;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CityMapper implements Serializable{
	int pid;
	String name;
	Double lat;
	Double lon;
	int getPid()
	{
		return pid;
	}
	void setPid(int pid)
	{
		this.pid=pid;
	}
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
}
