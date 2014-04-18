package org.elsys.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.elsys.entities.Complaint;
import org.elsys.entities.User;
import org.elsys.entities.UserRole;

public class ComplaintsService {

	private static ComplaintsService INSTANCE;
	private EntityManagerFactory emf;
	
	public EntityManagerFactory getEMF() {
		return emf;
	}
	
	protected ComplaintsService() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Driver not found", e);
		}
		
		emf = createEMF();
	}
	
	private EntityManagerFactory createEMF() {
		final EntityManagerFactory result = Persistence.createEntityManagerFactory("InternetTechnologiesProject");
		return result;
	}

	public static ComplaintsService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ComplaintsService();
		}
		return INSTANCE;
	}
	
	public Complaint addImageToComplaint(long complaintid, InputStream content) throws FileNotFoundException {
		final EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		Complaint complaint;
		
		try {
			tx.begin();
			complaint = em.find(Complaint.class, complaintid);
			String filename = "./" + complaint.getId() + ".jpg";
			saveToFile(content, filename);
			System.out.println("saved to file : " + filename);
			complaint.setImagePath(filename);
			
			em.merge(complaint);
			
			tx.commit();
		} finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			em.close();
		}
		
		return complaint;
	}
	
	private void saveToFile(InputStream content, String filename) throws FileNotFoundException {
		FileOutputStream outputStream = new FileOutputStream(new File(filename));
		InputStream inputStream = content;
		try {
			int read = 0;
			byte[] bytes = new byte[1024];
	 
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
	 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	 
			}
		}
	}

	public void createComplaint(String authorUsername, Complaint complaint) {
		final EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			complaint.setAuthor(getUser(em, authorUsername));
			em.persist(complaint);
			
			tx.commit();
		} finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			em.close();
		}
	}
	
	private User getUser(EntityManager em, String username) {
		final TypedQuery<User> query = em.createNamedQuery("byUsername", User.class);
		query.setParameter("username", username);
		return query.getSingleResult();
	}

	public List<Complaint> getAllComplaints(String username) {
		EntityManager em = emf.createEntityManager();
		try {
			return em.createNamedQuery("allComplaints", Complaint.class).setParameter("username", username).getResultList();
		} finally {
			em.close();
		}
	}

	public void addPlateToComplaint(String result, Complaint c) {
		final EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			c.setPlateNumber(result);
			
			em.merge(c);
			
			tx.commit();
		} finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			em.close();
		}
	}

	public void deleteComplaint(long complaintid) {
		final EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			final Complaint c = em.find(Complaint.class, complaintid);
			if(c != null) {
				em.remove(c);
			}
			
			tx.commit();
		} finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			em.close();
		}
	}

	public void updateComplaint(Complaint c, long complaintid) {
		final EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		
		final Complaint complaint = em.find(Complaint.class, complaintid);
	
		final User u = em.find(User.class, complaint.getAuthor().getId());
		
		c.setAuthor(u);
		
		try {
			tx.begin();
			
			em.merge(c);
			
			tx.commit();
		} finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			em.close();
		}
	}

	public void createInitialUser() {
		final EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			final User user1 = new User();
			user1.setUsername("admin");
			user1.setPassword("admin");
			user1.setRole(UserRole.ADMIN);
			
			em.persist(user1);
			
			tx.commit();
		} finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			em.close();
		}
	}

	public void addUser(User u) {
		final EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			em.persist(u);
			
			tx.commit();
		} finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			em.close();
		}
	}

}
