package org.elsys.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.elsys.entities.Complaint;

public class ComplaintsService {

	private static ComplaintsService INSTANCE;
	private EntityManagerFactory emf;
	
	public EntityManagerFactory getEMF() {
		return emf;
	}
	
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
	
	public void addImageToComplaint(long complaintid, InputStream content) throws FileNotFoundException {
		final EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			Complaint complaint = em.find(Complaint.class, complaintid);
			String filename = "/home/martopc/EEworkspace/InternetTechnologiesProject/Images/"+complaint.getId()+".jpg";
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
	}
	
	private void saveToFile(InputStream content, String filename) throws FileNotFoundException {
		FileOutputStream outputStream = new FileOutputStream(new File(filename));
		InputStream inputStream = content;
		// TODO Auto-generated method stub
		try {
			// read this file into InputStream
			
	 
			// write the inputStream to a FileOutputStream
			
	 
			int read = 0;
			byte[] bytes = new byte[1024];
	 
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
	 
			System.out.println("Done!");
	 
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
