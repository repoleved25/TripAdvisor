package androidlistview;

import java.io.Serializable;
import java.util.HashMap;

import android.annotation.SuppressLint;

@SuppressLint("UseSparseArrays")
@SuppressWarnings("serial")
public class GraphCity implements Serializable{
	private HashMap<Integer,CityMapper> places;
	private HashMap<Integer,Integer> indextopid;
	private double[][] graph=new double[20][20];
	GraphCity()
	{
		places=new HashMap<Integer,CityMapper>();
		indextopid=new HashMap<Integer, Integer>();
	}
	void addIndexToPid(int index,int pid)
	{
		indextopid.put(index,pid);
	}
	int getIndexToPid(int index)
	{
		return indextopid.get(index);
	}
	void addPlace(int pid,CityMapper p)
	{
		places.put(pid, p);
	}
	CityMapper getPlace(int pid)
	{
		return places.get(pid);
	}
	void setGraphDist(int x,int y,Double dist)
	{
		graph[x][y]=dist;
	}
	double getGraphDist(int x,int y)
	{
		return graph[x][y];
	}
	double[][] getdarr()
	{
		return graph;
	}
	public  double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		  double theta = lon1 - lon2;
		  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515;
		  if (unit == "K") {
		    dist = dist * 1.609344;
		  } else if (unit == "N") {
		  	dist = dist * 0.8684;
		    }
		  return (dist);
		}

		private double deg2rad(double deg) {
		  return (deg * Math.PI / 180.0);
		}
		
		private double rad2deg(double rad) {
		  return (rad * 180 / Math.PI);
		}
		
}
