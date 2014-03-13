package org.elsys.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public Complaint addComplaint(Complaint c) {
    	//create message
    	/*
    	System.out.println(c.getLongitude());
    	System.out.println(c.getLatitude());
    	System.out.println(c.getAddress());
    	System.out.println(c.getMessage());
    	*/
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
			System.out.println("-----------------------");
		}
    	return list;
    }
}
