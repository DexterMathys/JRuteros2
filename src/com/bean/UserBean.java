package com.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.imp.UserDaoImp;
import com.model.User;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class UserBean {

	private User user = new User();
	private UserDaoImp userDao = new UserDaoImp();
	private List<User> users;
	private String day;
	private String month;
	private String year;

	public User getUser() {
		return user;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String verificarDatos() throws Exception {
		User us = null;
		try {
			us = userDao.verificarDatos(this.user);
			if (us != null) {
				if (us.isActive() == true) {
					this.user = us;
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", us);
					return "index";
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Su usuario ha sido deshabilitado", "Por favor comunicarse con el administrador"));
					return "login";
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre de usuario o contraseña incorrecto/a",
								"Por favor ingresar un nombre de usuario y contraseña correctos"));
				return "login";
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean verificarSesion() {
		boolean estado;
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user") == null) {
			estado = false;
		} else {
			estado = true;
		}

		return estado;
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		this.user = new User();
		return "login?faces-redirect-true";
	}

	public String registrar() throws Exception {
		this.user.setActive(true);
		Random rand = new Random();
		int randomNum = rand.nextInt((9999 - 1000) + 1) + 1000;
		this.user.setPassword(String.valueOf(randomNum));
		this.user.setRoll("User");
		String str_birthdate = this.year + "-" + this.month + "-" + this.day;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		Date birthdate = null;
		try {
			birthdate = formatter.parse(str_birthdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.user.setBirthdate(birthdate);
		userDao.nuevoUser(this.user);
		return "signupok";
	}

	public void eliminar(User user) throws Exception {
		userDao.eliminarUser(user);
	}

	public String modificar() {
		userDao.editarUser(this.user);
		return "index";
	}

	public void listar() throws Exception {
		this.users = userDao.listarOrdenado();
	}

}
