package org.elsys.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.elsys.entities.Complaint;

public class ComplaintsService {

	private static ComplaintsService INSTANCE;
	private EntityManagerFactory emf;
	
	protected ComplaintsService() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
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
	
	public void createComplaint(Complaint complaint) {
		final EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			em.persist(complaint);
			
			tx.commit();
		} finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			em.close();
		}
	}
	
	
	public List<Complaint> getAllComplaints() {
		EntityManager em = emf.createEntityManager();
		try {
			return em.createNamedQuery("allComplaints", Complaint.class).getResultList();
		} finally {
			em.close();
		}
	}

}







