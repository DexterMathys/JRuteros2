package com.model;

public class Activity implements java.io.Serializable {

	private Long id;
	private String name;
	private boolean active;
	
	public Activity() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getEstado(){
		if (active) {
			return "Habilitado";
		}else{
			return "Deshabilitado";
		}
	}
	
	public boolean hasRoutes(){
		return false;
		
	}

}
