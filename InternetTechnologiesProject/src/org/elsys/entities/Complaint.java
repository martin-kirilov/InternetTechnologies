package org.elsys.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity(name = "Complaints")
@NamedQueries({
	@NamedQuery(name = "allComplaints", query = "SELECT c FROM Complaints c")
})
public class Complaint {
	
	@Id
	@GeneratedValue
	private long id;
	
	/*
	@Column(length = 128, nullable = false)
	public String imagePath;
	*/
	@Column(nullable = false)
	public double latitude;
	
	@Column(nullable = false)
	public double longitude;
	
	@Column(length = 512, nullable = false)
	public String address;
	
	@Column(length = 1024, nullable = false)
	public String message;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
/*
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
*/
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
