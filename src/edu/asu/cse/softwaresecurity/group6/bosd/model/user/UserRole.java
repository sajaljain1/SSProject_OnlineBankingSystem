package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_roles", uniqueConstraints = @UniqueConstraint(columnNames = { "role", "username" }))
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "user_role_id", unique = true, nullable = false)
	private int userRoleId;

	@Column(name = "username")
	private String username;

	@Column(name = "role")
	private String role;

	public int getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserRole [userRoleId=" + userRoleId + ", username=" + username + ", role=" + role + "]";
	}

/*	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", nullable = false)
	public User getUsername() {
		return this.username;
	}

	public void setUser(User user) {
		this.user = user;
	}*/

/*	@Column(name = "role", nullable = false, length = 45)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}*/

}