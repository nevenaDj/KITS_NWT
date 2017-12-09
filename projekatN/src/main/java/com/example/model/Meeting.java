package com.example.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Meeting {
	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAndTime;

	@ManyToOne(fetch = FetchType.EAGER)
	private Building building;

	@OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
	private Set<Survey> surveys = new HashSet<>();

	@OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
	private Set<AgendaItem> points = new HashSet<>();

	private boolean active= false;
	
	public Meeting() {

	}

	public Meeting(Long id, Date dateAndTime, boolean active) {
		super();
		this.id = id;
		this.dateAndTime = dateAndTime;
		this.active=active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Set<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(Set<Survey> surveys) {
		this.surveys = surveys;
	}

	public Set<AgendaItem> getPoints() {
		return points;
	}

	public void setPoints(Set<AgendaItem> points) {
		this.points = points;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
}
