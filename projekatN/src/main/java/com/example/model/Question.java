package com.example.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question {
	@Id
	@GeneratedValue
	private Long id;

	private String text;

	@ManyToOne(fetch = FetchType.LAZY)
	private Survey survey;
	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	private Set<Option> options = new HashSet<Option>();

	public Question() {

	}

	public Question(Long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public Question(Long id, String text, Survey survey) {
		super();
		this.id = id;
		this.text = text;
		this.survey = survey;
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

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Set<Option> getOptions() {
		return options;
	}

	public void setOptions(Set<Option> options) {
		this.options = options;
	}

}
