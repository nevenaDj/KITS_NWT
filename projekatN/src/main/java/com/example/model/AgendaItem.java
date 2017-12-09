package com.example.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class AgendaItem {
	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Meeting meeting;
	
	private String content;
	private String conclusion;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<ItemComment> comments;
	
	private int number;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Notification notification;

	@OneToOne(fetch = FetchType.EAGER)
	private Glitch glitch;
	
	@OneToOne(fetch = FetchType.EAGER)
	private CommunalProblem communalProblem;
	
	private ItemType type;
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public Set<ItemComment> getComments() {
		return comments;
	}

	public void setComments(Set<ItemComment> comments) {
		this.comments = comments;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public Glitch getGlitch() {
		return glitch;
	}

	public void setGlitch(Glitch glitch) {
		this.glitch = glitch;
	}

	public CommunalProblem getCommunalProblem() {
		return communalProblem;
	}

	public void setCommunalProblem(CommunalProblem communalProblem) {
		this.communalProblem = communalProblem;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public AgendaItem(Long id, int number, ItemType type,  String title) {
		super();
		this.id = id;
		this.number = number;
		this.type = type;
		this.title=title;
	}

	public AgendaItem(Long id, Meeting meeting, String conclusion, Set<ItemComment> comments, int number,
			ItemType type,  String title) {
		super();
		this.id = id;
		this.meeting = meeting;
		this.conclusion = conclusion;
		this.comments = comments;
		this.number = number;
		this.type = type;
		this.title=title;
	}

	public AgendaItem(Long id, Meeting meeting, String conclusion, int number, ItemType type,  String title) {
		super();
		this.id = id;
		this.meeting = meeting;
		this.conclusion = conclusion;
		this.number = number;
		this.type = type;
		this.title=title;
	}

	public AgendaItem(Long id, Meeting meeting, Set<ItemComment> comments, int number, ItemType type, String title) {
		super();
		this.id = id;
		this.meeting = meeting;
		this.comments = comments;
		this.number = number;
		this.type = type;
		this.title=title;
	}

	public AgendaItem() {
		super();
	}
	

}
