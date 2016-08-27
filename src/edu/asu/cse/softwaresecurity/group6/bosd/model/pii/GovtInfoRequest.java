package edu.asu.cse.softwaresecurity.group6.bosd.model.pii;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "govt_info_req")
public class GovtInfoRequest {
	private Long id; 
	private String pii;
	private boolean completed;
	
	@Column(name="complete")
	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Column(name = "pii")
	public String getPii() {
		return pii;
	}

	public void setPii(String pii) {
		this.pii = pii;
	}

	@Id
    @Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
