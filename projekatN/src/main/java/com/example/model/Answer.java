package com.example.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Answer {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	private Survey survey;
	@ManyToOne(fetch = FetchType.EAGER)
	private Question question;
	@ManyToOne(fetch = FetchType.EAGER)
	private Option option;
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public Answer() {

	}

	public Answer(Long id, Question question, Option option, User user) {
		super();
		this.id = id;
		this.question = question;
		this.option = option;
		this.user = user;
	}

	public Answer(Survey survey, Question question, Option option, User user) {
		super();
		this.survey = survey;
		this.question = question;
		this.option = option;
		this.user = user;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
