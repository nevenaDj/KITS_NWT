package com.example.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.model.Survey;
import com.example.model.SurveyType;

public class SurveyDTO {
	private Long id;
	private String title;
	private Date end;
	private SurveyType type;

	private Set<QuestionDTO> questions = new HashSet<>();

	public SurveyDTO() {

	}

	public SurveyDTO(Survey survey) {
		this(survey.getId(), survey.getTitle(), survey.getEnd(), survey.getType());
	}

	public SurveyDTO(String title, Date end, SurveyType type, Set<QuestionDTO> questions) {
		super();
		this.title = title;
		this.end = end;
		this.type = type;
		this.questions = questions;
	}

	public SurveyDTO(Long id, String title, Date end, SurveyType type) {
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

	public SurveyType getType() {
		return type;
	}

	public void setType(SurveyType type) {
		this.type = type;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<QuestionDTO> questions) {
		this.questions = questions;
	}

	public static Survey getSurvey(SurveyDTO surveyDTO) {
		return new Survey(surveyDTO.getId(), surveyDTO.getTitle(), surveyDTO.getEnd(), surveyDTO.getType());
	}
}
