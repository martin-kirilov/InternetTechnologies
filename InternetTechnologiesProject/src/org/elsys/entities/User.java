package org.elsys.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity(name="Users")
@NamedQueries({
	@NamedQuery(name="byUsername", query="SELECT u FROM Users u WHERE username=:username")
})
public class User {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(length=50, nullable=false, unique=true)
	private String username;
	
	@Column(length=50, nullable=false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@OneToMany
	private List<Complaint> complaints;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
}
