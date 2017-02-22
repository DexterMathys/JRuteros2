package com.imp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.dao.UserDao;
import com.model.User;
import com.util.HibernateUtil;

public class UserDaoImp implements UserDao {

	private Session s = HibernateUtil.sessionFactory.openSession();

	@Override
	public void nuevoUser(User user) {
		try {
			if (s != null) {
				s.close();
				s = null;
			}
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.save(user);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

		}
	}

	@Override
	public void editarUser(User user) {
		try {
			if (s != null) {
				s.close();
				s = null;
			}
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.update(user);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

		}
	}

	@Override
	public void eliminarUser(User user) {
		try {
			if (s != null) {
				s.close();
				s = null;
			}
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.delete(user);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listarUsers() {
		List<User> users = null;
		if (s != null) {
			s.close();
			s = null;
		}
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM User a INNER JOIN FETCH a.pais";
		try {
			users = s.createQuery(hql).list();
			t.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		}
		return users;
	}

	@Override
	public User verificarDatos(User user) {
		User us = null;
		try {
			if (s != null) {
				s.close();
				s = null;
			}
			s = HibernateUtil.getSessionfactory().openSession();
			String hql = "FROM User WHERE userName = '" + user.getUserName() + "' AND password = '" + user.getPassword()
					+ "'";
			Query query = s.createQuery(hql);

			if (!query.list().isEmpty()) {
				us = (User) query.list().get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return us;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listarOrdenado() {
		List<User> users = null;
		try {
			if (s != null) {
				s.close();
				s = null;
			}
			s = HibernateUtil.getSessionfactory().openSession();
			Criteria cri = s.createCriteria(User.class);
			cri.addOrder(Order.asc("userName"));
			users = cri.list();
		} catch (Exception e) {
			throw e;
		}
		return users;
	}

}
