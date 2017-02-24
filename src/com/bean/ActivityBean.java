package com.bean;

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
import com.imp.UserDaoImp;
import com.model.Activity;
import com.model.User;

@ManagedBean
@RequestScoped
public class ActivityBean {
	
	private Activity activity = new Activity();
	private List<Activity> activities = (ArrayList<Activity>) (new ActivityDaoImp().listar()) ;

	public ActivityBean() {
		// TODO Auto-generated constructor stub
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	public void reloadActivities(){
		this.activities = (ArrayList<Activity>) (new ActivityDaoImp().listar()) ;
	}
/*	
	public String activitiesList(){
		this.activities = (ArrayList<Activity>) (new ActivityDaoImp().listar()) ;
		return "activities";
	}*/
	
	public String toggle(Activity activity){
		ActivityDaoImp activityDAO = new ActivityDaoImp();
		activity.setActive(!activity.isActive());
		activityDAO.editar(activity);
		this.activities = (ArrayList<Activity>) (new ActivityDaoImp().listar()) ;
		return "success";
	}
	
	public void addActivity(){
		/*ActivityDaoImp activityDAO = new ActivityDaoImp();
		if (activityDAO.existe(this.activity)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Esta actividad ya existe", ""));
			//return "login";
			
		}else {
			activityDAO.nuevo(this.activity);
			this.activities.add(this.activity);
		}*/
		this.activity = new Activity();
		 Map<String,Object> options = new HashMap<String, Object>();
	        options.put("resizable", false);
	        options.put("draggable", false);
	        options.put("modal", true);
	    RequestContext.getCurrentInstance().openDialog("formActivity.xhtml", options, null);
	}
	
	public void add(){
		ActivityDaoImp activityDAO = new ActivityDaoImp();
		if (activityDAO.existe(this.activity)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Esta actividad ya existe",  ""));
			
		}else {
			activityDAO.nuevo(this.activity);
			this.activities.add(this.activity);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Actividad agregada", this.activity.getName()));
			RequestContext.getCurrentInstance().closeDialog(this.activities);
			
		}
		//
	}
	
	public void delete(){
		ActivityDaoImp activityDAO = new ActivityDaoImp();
		if (activityDAO.existe(this.activity)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Esta actividad tiene rutas asociadas",  ""));
			
		}else {
			activityDAO.eliminar(this.activity);
			this.activities.remove(this.activity);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Actividad eliminada", this.activity.getName()));
			
		}
		
	}
	
	
	public void update(Activity ac){
		ActivityDaoImp activityDAO = new ActivityDaoImp();
		if (activityDAO.existe(ac)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Esta actividad ya existe", ""));
		}else {
			activityDAO.editar(ac);
			this.activities = (ArrayList<Activity>) (new ActivityDaoImp().listar()) ;
			RequestContext.getCurrentInstance().closeDialog(ac);
		}
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update("datatable:datatable");
		
	}
	
	public void responseUpdate(SelectEvent event) {
	    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad actualizada.", this.activity.getName());
	    FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void formEdit(Activity ac){
		this.activity = ac;
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("formActivity.xhtml", options, null);
		
	}


}
