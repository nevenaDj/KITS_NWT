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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Survey {
	@Id
	@GeneratedValue
	private Long id;

	private String title;
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;

	@OneToOne(fetch = FetchType.EAGER)
	private SurveyType type;

	@ManyToOne(fetch = FetchType.EAGER)
	private Meeting meeting;

	@OneToMany(mappedBy = "survey", fetch = FetchType.EAGER)
	private Set<Question> questions = new HashSet<Question>();

	public Survey() {

	}

	public Survey(Long id, String title, Date end, SurveyType type) {
		super();
		this.id = id;
		this.title = title;
		this.end = end;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public SurveyType getType() {
		return type;
	}

	public void setType(SurveyType type) {
		this.type = type;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

}
