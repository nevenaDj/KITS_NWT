package com.example.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Notification {
	@Id
	@GeneratedValue
	private Long id;
	
	private Date date;
	private String text;
	private NotificationStatus status;

	@ManyToOne(fetch = FetchType.EAGER)
	private Building building;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User writer;
	
	public Notification(){
		
	}
	
	public Notification(Long id, Date date, String text) {
		super();
		this.id = id;
		this.date = date;
		this.text = text;
		this.status=NotificationStatus.WAITING;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public User getWriter() {
		return writer;
	}
	public void setWriter(User writer) {
		this.writer = writer;
	}
	public NotificationStatus getStatus() {
		return status;
	}
	public void setStatus(NotificationStatus status) {
		this.status = status;
	}
	public Building getBuilding() {
		return building;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}
	
	
	
	
}
