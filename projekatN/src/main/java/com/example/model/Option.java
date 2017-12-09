package com.example.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "QuestionOption")
public class Option {
	@Id
	@GeneratedValue
	private Long id;

	private String text;
	@ManyToOne(fetch = FetchType.LAZY)
	private Question question;

	public Option() {

	}

	public Option(Long id, String text, Question question) {
		super();
		this.id = id;
		this.text = text;
		this.question = question;
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

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
