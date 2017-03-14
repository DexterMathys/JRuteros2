package com.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.imp.UserDaoImp;
import com.model.User;

@ManagedBean
@RequestScoped
public class UserBean {

	private User user = new User();
	private UserDaoImp userDao = new UserDaoImp();
	private List<User> users;
	private List<User> filteredUsers;
	private String currentPass = null;
	private String newPass = null;
	private String confirmPass = null;
	private Date birthdate;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public UserBean() {
		this.users = (userDao.listarUsers());
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public User getUser() {
		return user;
	}

	public String getCurrentPass() {
		return currentPass;
	}

	public void setCurrentPass(String currentPass) {
		this.currentPass = currentPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		UserDaoImp userDao = new UserDaoImp();
		users = userDao.listarUsers();
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
					this.birthdate = this.user.getBirthdate();
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", this.user);
					return "index";
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Su usuario ha sido deshabilitado", "Por favor comunicarse con el administrador"));
					return "login";
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre de usuario o contrase�a incorrecto/a",
								"Por favor ingresar un nombre de usuario y contrase�a correctos"));
				return "login";
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login?faces-redirect=true";
	}

	public boolean validateSession() {
		boolean estado;
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user") == null) {
			estado = false;
		} else {
			estado = true;
		}
		return estado;
	}

	public String usersList() {
		this.users = (userDao.listarUsers());
		return "users";
	}

	public String toggle(User user) {
		user.setActive(!user.isActive());
		userDao.editarUser(user);
		this.users = (userDao.listarUsers());
		return "success";
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

	public String registrar() throws Exception {
		if (!this.validateUser()) {
			return "signup";
		}
		this.user.setActive(true);
		Random rand = new Random();
		int randomNum = rand.nextInt((9999 - 1000) + 1) + 1000;
		this.user.setPassword(String.valueOf(randomNum));
		this.user.setRoll("User");
		this.user.setBirthdate(this.birthdate);
		userDao.nuevoUser(this.user);
		return "signupok";
	}

	private boolean validateUser() {
		boolean ok = true;
		if (this.user.getUserName() == "") {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El campo Nombre de usuario no puede ser vacio.", ""));
			ok = false;
		} else {
			User current_us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			if ((this.validateSession() && !current_us.getUserName().equals(this.user.getUserName())
					&& this.userDao.existUsername(this.user.getUserName()))
					|| (!this.validateSession() && this.userDao.existUsername(this.user.getUserName()))) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre de usuario no disponible.", ""));
				ok = false;
			}
		}
		if (this.user.getDni() == 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo DNI no puede ser vacio ni 0.", ""));
			ok = false;
		}
		if (this.user.getName() == "") {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Nombre/s no puede ser vacio.", ""));
			ok = false;
		}
		if (this.user.getLastName() == "") {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Apellido/s no puede ser vacio.", ""));
			ok = false;
		}
		if (this.user.getAddress() == "") {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Domicilio no puede ser vacio.", ""));
			ok = false;
		}
		if (this.getBirthdate() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El campo Fecha de nacimiento no puede ser vacio.", ""));
			ok = false;
		} else {

			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setLenient(false);
				calendar.setTime(this.birthdate);
				Date date = calendar.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			} catch (IllegalArgumentException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"El campo Fecha de nacimiento es invalido.", "Una fecha valida es por ejemplo 01/01/2017"));
				ok = false;
			}
		}
		if (this.user.getSex() == "") {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una opcion en el campo Sexo.", ""));
			ok = false;
		}
		if (this.user.getEmail() == "") {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Email no puede ser vacio.", ""));
			ok = false;
		} else {
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(this.user.getEmail());
			if (!matcher.find()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Email invalido.", "Un mail valido es de la forma example@example.com"));
				ok = false;
			}
		}
		return ok;
	}

	public void eliminar(User user) throws Exception {
		userDao.eliminarUser(user);
	}

	public String modificar() {
		if (!this.validateUser()) {
			this.currentPass = null;
			this.newPass = null;
			this.confirmPass = null;
			return "editprofile";
		}
		if (this.currentPass != null && !this.currentPass.isEmpty()) {
			if (this.currentPass.equals(this.user.getPassword())) {
				if (this.newPass.equals(this.confirmPass)) {
					this.user.setPassword(this.newPass);
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Las claves no concuerdan.",
							"Por favor verifique que el campo Clave nueva cohincida con el campo Repetir clave nueva."));
					this.currentPass = null;
					this.newPass = null;
					this.confirmPass = null;
					return "editprofile";
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Clave incorrecta.", "Por favor ingresar la clave correcta"));
				this.currentPass = null;
				this.newPass = null;
				this.confirmPass = null;
				return "editprofile";
			}
		}
		userDao.editarUser(this.user);
		this.currentPass = null;
		this.newPass = null;
		this.confirmPass = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito!.", "Su usuario fue editado correctamente."));
		return "index?faces-redirect-true";
	}

	public String getMenu() {
		if (this.user.isAdmin()) {
			return "/WEB-INF/facelets/menus/adminMenu.xhtml";
		} else {
			return "/WEB-INF/facelets/menus/userMenu.xhtml";
		}

	}

}
