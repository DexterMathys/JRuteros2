package com.services;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseApoint {
	private Long id; 
	private String lat;
	private String lon;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public ResponseApoint(Long id, String lat, String lon) {
		super();
		this.id = id;
		this.lat = lat;
		this.lon = lon;
	}
	
	
	
	

}
