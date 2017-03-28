package com.bean;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.imp.ActivityDaoImp;
import com.imp.ApointDaoImp;
import com.imp.RouteDaoImp;
import com.imp.RoutescoreDaoImp;
import com.imp.TravelDaoImp;
import com.model.Activity;
import com.model.Apoint;
import com.model.Difficulty;
import com.model.Route;
import com.model.Routescore;
import com.model.Travel;
import com.model.User;
import com.model.UserRoute;
import com.model.UserRouteId;

@ManagedBean
@RequestScoped
public class RouteBean {

	private Route route = new Route();
	private RoutescoreDaoImp scoreDao = new RoutescoreDaoImp();
	private double totalScore = 0.0;
	private int score = 0;
	private String myscore;
	private String scorePromedio;
	private List<Route> routes = (new RouteDaoImp().listar());
	private List<Route> filteredRoutes;
	private List<SelectItem> activities;
	private int hours;
	private int minutes;
	private String isPublic;
	private String isCircular;
	private Date date;
	private String points;
	private RouteDaoImp routeDao = new RouteDaoImp();
	private Part file;
	private String fileContent;

	public RouteBean() {
	}

	public String getMyscore() {
		return myscore;
	}

	public void setMyscore(String myscore) {
		this.myscore = myscore;
	}

	public String getScorePromedio() {
		return scorePromedio;
	}

	public void setScorePromedio(String scorePromedio) {
		this.scorePromedio = scorePromedio;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public List<Route> getFilteredRoutes() {
		return filteredRoutes;
	}

	public void setFilteredRoutes(List<Route> filteredRoutes) {
		this.filteredRoutes = filteredRoutes;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
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
		List<Activity> actividades = actDao.listarHabilitadas();
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
		this.route = new Route();
		this.date = null;
		this.hours = 0;
		this.minutes = 0;
		this.isCircular = null;
		this.isPublic = null;
		this.listActivities();
		return "/newRoute.xhtml";
	}

	public String myRoutes() {
		User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		this.setRoutes(new RouteDaoImp().listar(us.getId()));
		return "/myRoutes.xhtml";
	}

	public String searchRoute() {
		this.setRoutes(new RouteDaoImp().listarPublicas());
		return "/publicRoutes.xhtml";
	}

	public String add() throws ParserConfigurationException, SAXException, IOException {

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
		Date time = null;
		try {
			time = new SimpleDateFormat("HH:mm").parse(String.valueOf(this.hours) + ":" + String.valueOf(this.minutes));
		} catch (ParseException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Tiempo estimado no es correcto.",
							"Un Tiempo estimado correcto es por ejemplo 02 horas y 30 minutos."));
			return "newRoute";
		}
		this.route.setTime(time);

		this.route.setDate(this.date);

		RouteDaoImp routeDAO = new RouteDaoImp();
		if (routeDAO.existe(this.route)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Esta ruta ya existe", ""));

		} else {
			TravelDaoImp travelDAO = new TravelDaoImp();
			Travel travel = new Travel();
			travelDAO.nuevo(travel);

			String action = obtenerPuntos();
			// Separo los puntos
			String[] points = action.split(",");
			crearPuntos(points, travel);

			this.route.setTravel(travel);
			routeDAO.nuevo(this.route);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Ruta agregada: ", this.route.getName()));
			this.route = null;
			this.route = new Route();
			this.date = null;
			this.isCircular = null;
			this.isPublic = null;
			this.hours = 0;
			this.minutes = 0;
		}

		return "index";
	}

	public Difficulty[] difficulties() {
		return Difficulty.values();
	}

	private boolean validateRoute() throws ParserConfigurationException, SAXException, IOException {
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
		if ((this.hours == 0 && this.minutes == 0) || (this.hours < 0 || this.minutes < 0)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Tiempo estimado no es correcto.",
							"Un Tiempo estimado correcto es por ejemplo 02 horas y 30 minutos."));
			ok = false;
		}
		if (this.date == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El campo Fecha de realizacion no puede ser vacio.", ""));
			ok = false;
		}
		String[] points = obtenerPuntos().split(",");
		if (points.length < 2) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos 2 puntos en el mapa.", ""));
			ok = false;
		}
		return ok;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public void upload() throws ParserConfigurationException, SAXException {
		try {
			this.fileContent = new Scanner(this.file.getInputStream()).useDelimiter("\\A").next();
		} catch (IOException e) {
			// Error handling
		}
	}

	private String fileToString(String filePath) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document = null;

		builder = factory.newDocumentBuilder();
		document = builder.parse(new InputSource(new StringReader(filePath)));
		document.getDocumentElement().normalize();
		// System.out.println ("Root element of the doc is " +
		// document.getDocumentElement().getNodeName());
		NodeList listOfPoints = document.getElementsByTagName("Point");
		int totalCoordinates = listOfPoints.getLength();
		// System.out.println("Total point : " + totalCoordinates);
		String points = null;
		for (int s = 0; s < listOfPoints.getLength(); s++) {

			Node firstPersonNode = listOfPoints.item(s);
			if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {
				Element firstPersonElement = (Element) firstPersonNode;

				NodeList firstNameList = firstPersonElement.getElementsByTagName("coordinates");
				Element firstNameElement = (Element) firstNameList.item(0);
				// lon, lat, alt
				NodeList textFNList = firstNameElement.getChildNodes();
				String[] point = textFNList.item(0).getNodeValue().toString().split(",");
				if (points == null) {
					// lat, lon
					points = point[1] + " " + point[0];
				} else {
					points = points + "," + point[1] + " " + point[0];
				}
				// System.out.println("Coordinate : " +
				// ((Node)textFNList.item(0)).getNodeValue().trim());
			}
		}
		return points;
	}

	public void crearPuntos(String[] points, Travel travel) {
		ApointDaoImp apointDAO = new ApointDaoImp();
		String[] partPoint;
		for (String point : points) {
			partPoint = point.split(" ");
			// travel, lat ,long
			apointDAO.nuevo(new Apoint(travel, partPoint[0], partPoint[1]));
		}
	}

	public String obtenerPuntos() throws ParserConfigurationException, SAXException, IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String action = params.get("points");
		if (action.equals("")) {
			upload();
			action = fileToString(this.fileContent);
		}
		return action;

	}

	public String view(Route route) {
		this.route = routeDao.obtener(route.getId());
		if (this.route.isIsPublic()) {
			this.isPublic = "1";
		} else {
			this.isPublic = "0";
		}
		if (this.route.isIsCircular()) {
			this.isCircular = "1";
		} else {
			this.isCircular = "0";
		}
		this.hours = this.route.getTime().getHours();
		this.minutes = this.route.getTime().getMinutes();
		this.date = this.route.getDate();
		User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		Routescore s = scoreDao.obtener(this.route.getId(), us.getId());

		if (s != null) {
			this.score = s.getScore();
		} else {
			this.score = 0;
		}
		this.myscore = String.valueOf(this.score);
		List<Routescore> scores = scoreDao.listar(this.route.getId());
		double promedio = 0.0;
		double suma = 0;
		for (Routescore routescore : scores) {
			suma += routescore.getScore();
		}
		if (suma > 0) {
			promedio = suma / scores.size();
		}
		this.scorePromedio = String.valueOf(promedio);
		return "route.xhtml";
	}

	public void edit() {

	}

	public void delete(Route route) {
		routeDao.eliminar(route);
		User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		this.setRoutes(new RouteDaoImp().listar(us.getId()));
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ruta ", route.getName() + " " + "eliminada"));
	}

	public void puntuar() {
		if (this.score == 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El puntaje no puede ser 0", ""));
		} else {
			User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			this.myscore = String.valueOf(this.score);
			Routescore routeScore = new Routescore(this.route, us, this.score);
			scoreDao.nuevo(routeScore);
			List<Routescore> scores = scoreDao.listar(this.route.getId());
			double promedio = 0.0;
			double suma = 0;
			for (Routescore routescore : scores) {
				suma += routescore.getScore();
			}
			if (suma > 0) {
				promedio = suma / scores.size();
			}
			this.scorePromedio = String.valueOf(promedio);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se guard� tu puntaje:", String.valueOf(this.score)));
		}
	}

	public boolean existePuntaje() {
		User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		if (scoreDao.existe(this.route.getId(), us.getId())) {
			return true;
		} else {
			return false;
		}
	}

	public void hacerRuta() {
		User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		UserRouteId user_route_id = new UserRouteId(us.getId(), this.route.getId());
		UserRoute user_route = new UserRoute(user_route_id, this.route, us);
		routeDao.nuevo(user_route);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Has realizado esta ruta", ""));
	}

	public boolean existeRutaHecha() {
		User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		UserRouteId user_route_id = new UserRouteId(us.getId(), this.route.getId());
		UserRoute user_route = routeDao.obtener(user_route_id);
		if (user_route != null) {
			return true;
		} else {
			return false;
		}
	}

	public long cantUsers() {
		return routeDao.countUsers(this.route.getId());
	}

}
