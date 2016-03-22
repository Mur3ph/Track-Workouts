package com.murph.objects;

public class Route66 {

	int routeId;
	String name;
	String time;
	String distance;
	
	public Route66() 
	{
		super();
	}
	public Route66(int routeId, String name, String time, String distance) 
	{
		this.routeId = routeId;
		this.name = name;
		this.time = time;
		this.distance = distance;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	@Override
	public String toString() 
	{
		return 	routeId 
				+ ". Name: " + name + "\n" 
				+ "   Duration: " + time + "\n" 
				+ "   Distance Covered: " + distance;
	}
	
	
	
}
