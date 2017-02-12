package com.dao;

import java.util.List;

import com.model.User;

public interface UserDao {

	public void nuevoUser(User user);
	public void editarUser(User user);
	public void eliminarUser(User user);
	public List<User> listarUsers();
	public User verificarDatos(User user);
}
