package com.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dao.PhotoDao;
import com.model.Photo;
import com.util.HibernateUtil;

public class PhotoDaoImp implements PhotoDao {

	private Session s;

	@Override
	public List<Photo> listar() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Photo> photos = null;

		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Photo";
		try {
			photos = s.createQuery(hql).list();
			t.commit();
			// s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return photos;
	}

	@Override
	public List<Photo> listar(long route_id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Photo> photos = null;

		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Photo WHERE route_id = :route_id";
		try {
			Query q = s.createQuery(hql);
			q.setParameter("route_id", route_id);
			photos = q.list();
			t.commit();
			// s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return photos;
	}

	@Override
	public void nuevo(Photo photo) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.save(photo);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (s != null) {
				s.close();
			}
		}

	}

	@Override
	public void editar(Photo photo) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.update(photo);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public void eliminar(Photo photo) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.delete(photo);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public boolean existe(Photo photo) {
		// TODO Auto-generated method stub
		boolean ex = false;
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Photo where id = :id ";
		try {
			ex = (s.createQuery(hql).setString("id", photo.getId().toString()).uniqueResult() != null);
			t.commit();
			// s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return ex;
	}

	@Override
	public Photo obtener(Long id) {
		// TODO Auto-generated method stub
		Photo photo;
		s = HibernateUtil.sessionFactory.openSession();
		photo = (Photo) s.get(Photo.class, id);
		s.close();
		return photo;
	}

}
