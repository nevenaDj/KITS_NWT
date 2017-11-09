package com.example.dto;

import java.util.HashSet;
import java.util.Set;

import com.example.model.Question;
import com.example.model.Survey;

public class QuestionDTO {
	private Long id;
	private String text;

	private Set<OptionDTO> options = new HashSet<OptionDTO>();

	public QuestionDTO() {

	}

	public QuestionDTO(Question question) {
		this(question.getId(), question.getText());
	}

	public QuestionDTO(Question question, HashSet<OptionDTO> options) {
		this(question.getId(), question.getText());
		this.options = options;
	}

	public QuestionDTO(Long id, String text) {
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

	public Set<OptionDTO> getOptions() {
		return options;
	}

	public void setOptions(Set<OptionDTO> options) {
		this.options = options;
	}

	public static Question getQuestion(QuestionDTO questionDTO, Survey survey) {
		return new Question(questionDTO.getId(), questionDTO.getText(), survey);
	}

}
