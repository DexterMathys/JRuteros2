package com.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.imp.UserDaoImp;
import com.model.User;

public class UserBean {

	private User user = new User();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String verificarDatos() throws Exception {
		UserDaoImp userDao = new UserDaoImp();
		User us = null;
		
		try {
			us = userDao.verificarDatos(this.user);
			if (us != null) {
				if (us.isActive() == true) {
					this.user = us;
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", us);
					return "index";
				}else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Su usuario ha sido deshabilitado", "Por favor comunicarse con el administrador"));
					return "login";
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Nombre de usuario o contraseņa incorrecto/a", "Por favor ingresar un nombre de usuario y contraseņa correctos"));
				return "login";
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
