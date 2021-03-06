package com.model;
// Generated 11-feb-2017 21:28:21 by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Travel generated by hbm2java
 */
public class Travel implements java.io.Serializable {

	private Long id;
	private Set<Route> routes = new HashSet<Route>(0);
	private List<Apoint> apoints ;

	public Travel() {
	}

	public Travel(Set<Route> routes, List<Apoint> apoints) {
		this.routes = routes;
		this.apoints = apoints;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Route> getRoutes() {
		return this.routes;
	}

	public void setRoutes(Set<Route> routes) {
		this.routes = routes;
	}

	public List<Apoint> getApoints() {
		return this.apoints;
	}

	public void setApoints(List<Apoint> apoints) {
		this.apoints = apoints;
	}

}
