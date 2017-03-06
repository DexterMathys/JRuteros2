package com.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import com.imp.ActivityDaoImp;
import com.imp.RouteDaoImp;
import com.imp.TravelDaoImp;
import com.model.Activity;
import com.model.Difficulty;
import com.model.Route;
import com.model.Travel;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@ManagedBean
@RequestScoped
public class RouteBean {

	private Route route = new Route();
	private List<Route> routes = (new RouteDaoImp().listar());
	private List<SelectItem> activities;
	private String tiempo;
	private String fecha;

	public RouteBean() {
		// TODO Auto-generated constructor stub
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
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

	public List<SelectItem> getActivities() {
		this.activities = new ArrayList<SelectItem>();
		RouteDaoImp routeDao = new RouteDaoImp();
		ActivityDaoImp actDao = new ActivityDaoImp();
		List<Activity> actividades = actDao.listar();
		activities.clear();

		for (Activity actividad : actividades) {
			SelectItem actItem = new SelectItem(actividad.getId(), actividad.getName());
			this.activities.add(actItem);
		}
		return this.activities;
	}

	public void setActivities(List<SelectItem> activities) {
		this.activities = activities;
	}

	public void listActivities() {
		// this.activities = (List<SelectItem>) new
		// ActivityDaoImp().listarHabilitadas();
	}

	public String newRoute() {
		/*
		 * try {
		 * FacesContext.getCurrentInstance().getExternalContext().redirect(
		 * "newRoute.xhtml"); } catch (IOException ex) { // do something here }
		 */
		this.listActivities();
		return "/newRoute.xhtml";
	}

	public String add() {
		SimpleDateFormat formatter = new SimpleDateFormat("hh");
		Date time = null;
		try {
			try {
				time = formatter.parse(this.tiempo);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.route.setTime(time);

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			try {
				date = formatter.parse(this.fecha);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.route.setDate(date);

		RouteDaoImp routeDAO = new RouteDaoImp();
		if (routeDAO.existe(this.route)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Esta ruta ya existe", ""));

		} else {
			TravelDaoImp travelDAO = new TravelDaoImp();
			Travel travel = travelDAO.obtener(new Long(3));
			this.route.setTravel(travel);
			routeDAO.nuevo(this.route);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ruta agregada", this.route.getName()));
			RequestContext.getCurrentInstance().closeDialog(this.activities);

		}

		return "success";
	}

	public Difficulty[] difficulties() {
		return Difficulty.values();
	}

}
