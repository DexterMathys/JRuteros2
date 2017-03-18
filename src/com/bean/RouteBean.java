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
import com.imp.TravelDaoImp;
import com.model.Activity;
import com.model.Apoint;
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
	private int hours;
	private int minutes;
	private String isPublic;
	private String isCircular;
	private Date date;
	private String points;
	private Part file;
	private String fileContent;
	 

	public RouteBean() {
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
			String[] points =  action.split(",");
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
		if (points.length < 2 ) {
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
	 
	 private String fileToString(String filePath) throws ParserConfigurationException, SAXException, IOException
	 {  
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		 DocumentBuilder builder;  
		 Document document = null ;
		 
		 builder = factory.newDocumentBuilder();  
		 document = builder.parse( new InputSource( new StringReader( filePath ) ) );  
		 document.getDocumentElement().normalize();
		 //System.out.println ("Root element of the doc is " + document.getDocumentElement().getNodeName());
         NodeList listOfPoints = document.getElementsByTagName("Point");
         int totalCoordinates = listOfPoints.getLength();
        // System.out.println("Total point : " + totalCoordinates);
		 String points = null;
         for(int s=0; s<listOfPoints.getLength() ; s++){

        	Node firstPersonNode = listOfPoints.item(s);
        	if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){
	        	Element firstPersonElement = (Element)firstPersonNode; 
	        	
	        	NodeList firstNameList = firstPersonElement.getElementsByTagName("coordinates");
	        	Element firstNameElement = (Element)firstNameList.item(0);
	        	//lon, lat, alt
	        	NodeList textFNList = firstNameElement.getChildNodes();
	        	String[] point = ((Node)textFNList.item(0)).getNodeValue().toString().split(",");
	        	if (points == null){
	        		// lat, lon
	        		points = point[1] + " " +  point[0];
	        	}else{
	        		points = points + ","  + point[1] + " " +  point[0];
	        	}
	        	//System.out.println("Coordinate : " + ((Node)textFNList.item(0)).getNodeValue().trim());
        	}
        }
		return points;
	 }
	 
	 public void crearPuntos(String[] points, Travel travel) {
		 ApointDaoImp apointDAO = new ApointDaoImp();
		 String[] partPoint;
			for (String point : points) {
				partPoint = point.split(" ");
				//travel, lat ,long
				apointDAO.nuevo(new Apoint(travel, partPoint[0], partPoint[1]));
			}
	 }
	 
	 public String obtenerPuntos() throws ParserConfigurationException, SAXException, IOException{
		 Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
		String action = params.get("points");
		if (action.equals("")) {
			upload();
			action = fileToString(this.fileContent);
		}
		return action;
		 
	 }

}
