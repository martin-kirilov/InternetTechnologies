package org.elsys.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.elsys.entities.Complaint;
import org.elsys.services.ComplaintsService;

@Path("Rest")
public class Rest {
    
	private ComplaintsService complaintsService;
	
    public Rest() {
    	this.complaintsService = ComplaintsService.getInstance();
    }
   
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Complaint addComplaint(Complaint c) {
    	//create message
    	
    	System.out.println(c.getLongitude());
    	System.out.println(c.getLatitude());
    	System.out.println(c.getAddress());
    	System.out.println(c.getMessage());
    	
    	complaintsService.createComplaint(c);
    	return c;
    }
    
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Complaint> getAllComplaints() {
    	List<Complaint> list = new ArrayList<Complaint>();
    	list = complaintsService.getAllComplaints();
    	for (Complaint complaint : list) {
    		System.out.println(complaint.getLongitude());
			System.out.println(complaint.getLatitude());
			System.out.println(complaint.getAddress());
			System.out.println(complaint.getMessage());
			System.out.println(complaint.getImagePath());
			System.out.println(complaint.getPlateNumber());
			System.out.println("-----------------------");
		}
    	return list;
    }
    
    @POST
	@Path("{complaintid}/image")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public void setComplaintImage(@Context ServletContext ctx,
			@PathParam("complaintid") long complaintid, InputStream content) throws IOException, InterruptedException {
    	//Complaint complaint = em.find(Complaint.class, complaintid);
    	Complaint complaint = complaintsService.addImageToComplaint(complaintid, content);
    	executeOCR(complaint);
    }
    
    
    public void executeOCR(Complaint c) throws InterruptedException, IOException {
    	String command = "./LicensePlateRec ./" + c.getId() + ".jpg";
		System.out.println(command);
		Process p = Runtime.getRuntime().exec(command);
		p.waitFor();
		Scanner sc = new Scanner(p.getInputStream());
		String result = sc.nextLine();
		System.out.println(result);
		//save result into DB.
		complaintsService.addPlateToComplaint(result, c);
		sc.close();
    }
    
}
