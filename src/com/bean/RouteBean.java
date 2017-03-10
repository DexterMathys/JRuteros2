package com.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.imp.ActivityDaoImp;
import com.imp.RouteDaoImp;
import com.imp.TravelDaoImp;
import com.model.Activity;
import com.model.Difficulty;
import com.model.Route;
import com.model.Travel;
import com.model.User;

@ManagedBean
@RequestScoped
public class RouteBean {

	private Route route = new Route();
	private List<Route> routes = (new RouteDaoImp().listar());
	private List<SelectItem> activities;
	private Date tiempo;
	private String isPublic;
	private String isCircular;
	private Date date;

	public RouteBean() {
		// TODO Auto-generated constructor stub
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public String getIsCircular() {
		return isCircular;
	}

	public void setIsCircular(String isCircular) {
		this.isCircular = isCircular;
	}

	public Date getTiempo() {
		return tiempo;
	}

	public void setTiempo(Date tiempo) {
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
		if (!this.validateRoute()) {
			return "newRoute";
		}
		if (this.isCircular.equals("1")) {
			this.route.setIsCircular(true);
		} else {
			this.route.setIsCircular(false);
		}
		if (this.isPublic.equals("1")) {
			this.route.setIsPublic(true);
		} else {
			this.route.setIsPublic(false);
		}
		User u = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		this.route.setUser(u);

		this.route.setTime(this.tiempo);

		this.route.setDate(this.date);

		RouteDaoImp routeDAO = new RouteDaoImp();
		if (routeDAO.existe(this.route)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Esta ruta ya existe", ""));

		} else {
			TravelDaoImp travelDAO = new TravelDaoImp();
			Travel travel = new Travel();
			travelDAO.nuevo(travel);
			this.route.setTravel(travel);
			routeDAO.nuevo(this.route);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Ruta agregada: ", this.route.getName()));
			this.route = null;
			this.route = new Route();
			this.date = null;
			this.isCircular = null;
			this.isPublic = null;
			this.tiempo = null;

		}

		return "index";
	}

	public Difficulty[] difficulties() {
		return Difficulty.values();
	}

	private boolean validateRoute() {
		boolean ok = true;
		if (this.route.getName() == "") {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El campo Nombre de la ruta no puede ser vacio.", ""));
			ok = false;
		}
		if (this.route.getDescription() == "") {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Descripcion no puede ser vacio.", ""));
			ok = false;
		}
		if (this.isPublic == "") {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una opcion de Privacidad.", ""));
			ok = false;
		}
		if (this.isCircular == "") {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una opcion de Formato.", ""));
			ok = false;
		}
		if (this.route.getDistance() <= 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Distancia debe ser mayor a 0.", ""));
			ok = false;
		}
		if (this.route.getDate() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El campo Fecha de realizacion no puede ser vacio.", ""));
			ok = false;
		}
		return ok;
	}

}
