package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SurveyType {
	@Id
	@GeneratedValue
	private Long id;

	private String type;

	public SurveyType() {

	}

	public SurveyType(Long id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
