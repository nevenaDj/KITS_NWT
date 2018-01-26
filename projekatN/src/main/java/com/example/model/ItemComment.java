package com.example.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemComment {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User writer;
	
	private String text;
	private Date date;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private AgendaItem item;
	
	public ItemComment() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ItemComment(Long id, User writer, String text, Date date) {
		super();
		this.id = id;
		this.writer = writer;
		this.text = text;
		this.date= date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public AgendaItem getItem() {
		return item;
	}

	public void setItem(AgendaItem item) {
		this.item = item;
	}
	
	
	
}
