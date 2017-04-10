package com.model;
// Generated 11-feb-2017 21:28:21 by Hibernate Tools 4.3.5.Final

/**
 * Apoint generated by hbm2java
 */
public class Apoint implements java.io.Serializable {

	private Long id;
	private Travel travel;
	private String latitude;
	private String longuitude;

	public Apoint() {
	}

	public Apoint(Travel travel) {
		this.travel = travel;
	}

	public Apoint(Travel travel, String latitude, String longuitude) {
		this.travel = travel;
		this.latitude = latitude;
		this.longuitude = longuitude;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Travel getTravel() {
		return this.travel;
	}

	public void setTravel(Travel travel) {
		this.travel = travel;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLonguitude() {
		return this.longuitude;
	}

	public void setLonguitude(String longuitude) {
		this.longuitude = longuitude;
	}
	
	public double rad(double x){
		 return (x * Math.PI / 180);
	}
	
	public float toFloat(String str){
		return new Float(str);
	}
	
	public double getDistance(Apoint p2){
		double lat2 = new Float(p2.getLatitude());
		double lgt2 = new Float(p2.getLonguitude());
		double lat1 = new Float(getLatitude());
		double lgt1 = new Float(getLonguitude());
		
		
		double R = 6378137; // Earth’s mean radius in meter
		double dLat = rad(lat2 - lat1);
		double dLong = rad(lgt2 - lgt1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(rad(lat1)) * Math.cos(rad(lat2)) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;
		return d; // returns the distance in meter
	}

}
