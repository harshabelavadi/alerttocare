package com.philips.alerttocare.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * This Icu class is an entity with 
 * id , Icu label and 
 * it has list of beds contained in particular icu
 **/

@Entity
@Table(name = "ICUs")
public class Icu implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Id", updatable = false)
	private long id;
	
	@Column(name = "ICU Label")
	private String label;
	
	@Column(name = "Created At")
	private Date createdAt;
	
	public Icu() {
		super();
	}
	
	public Icu(long id, String label) {
		super();
		this.id = id;
		this.label = label;
	}

	public long getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	@PrePersist
	public void setCreatedAt() {
	    this.createdAt = new Date();
	}
	public Date getCreatedAt() {
		return this.createdAt;
	}
}
