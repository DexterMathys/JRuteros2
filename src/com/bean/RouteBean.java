package com.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.imp.ActivityDaoImp;
import com.imp.RouteDaoImp;
import com.imp.TravelDaoImp;
import com.model.Activity;
import com.model.Difficulty;
import com.model.Route;
import com.model.Travel;


@ManagedBean
@RequestScoped
public class RouteBean {
	
	private Route route = new Route();
	private List<Route> routes = (ArrayList<Route>) (new RouteDaoImp().listar()) ;
	private List<Activity> activities = new ArrayList<Activity>() ;

	public RouteBean() {
		// TODO Auto-generated constructor stub
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	
	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void listActivities(){
		this.activities = (ArrayList<Activity>) new ActivityDaoImp().listarHabilitadas();
	}
	
	public String newRoute(){
		/*try {
		       FacesContext.getCurrentInstance().getExternalContext().redirect("newRoute.xhtml");
		} catch (IOException ex) {
		       // do something here
		}*/
		this.listActivities();
		return "/newRoute.xhtml";
	}
	
	public String add(){
		RouteDaoImp routeDAO = new RouteDaoImp();
		if (routeDAO.existe(this.route)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Esta ruta ya existe",  ""));
			
		}else {
			TravelDaoImp travelDAO = new TravelDaoImp();
			Travel travel = travelDAO.obtener(new Long(3));
			this.route.setTravel(travel);
			routeDAO.nuevo(this.route);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
				"Ruta agregada", this.route.getName()));
			RequestContext.getCurrentInstance().closeDialog(this.activities);
			
		}
		
		return "success";
	}
	
	public Difficulty[] difficulties() {
        return Difficulty.values();
    }
	


}
