package com.example.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AgendaPoint {
	@Id
	@GeneratedValue
	private Long id;

	private String content;
	private String comment;

	@ManyToOne(fetch = FetchType.EAGER)
	private Meeting meeting;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public AgendaPoint(Long id, String content, String comment, Meeting meeting, User user) {
		super();
		this.id = id;
		this.content = content;
		this.comment = comment;
		this.meeting = meeting;
		this.user = user;
	}

	public AgendaPoint(Long id, String content, String comment) {
		super();
		this.id = id;
		this.content = content;
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
