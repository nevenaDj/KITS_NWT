package com.example.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	@Id
	@GeneratedValue
	private Long id;

	private String text;

	@ManyToOne(fetch = FetchType.EAGER)
	private Glitch glitch;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public Comment() {

	}

	public Comment(Long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Glitch getGlitch() {
		return glitch;
	}

	public void setGlitch(Glitch glitch) {
		this.glitch = glitch;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
