package com.example.dto;

import java.util.HashSet;
import java.util.Set;

public class AnswerDTO {
	private Long questionID;
	private Set<Long> options = new HashSet<Long>();

	public AnswerDTO() {

	}

	public AnswerDTO(Long questionID, Set<Long> options) {
		super();
		this.questionID = questionID;
		this.options = options;
	}

	public Long getQuestionID() {
		return questionID;
	}

	public void setQuestionID(Long questionID) {
		this.questionID = questionID;
	}

	public Set<Long> getOptions() {
		return options;
	}

	public void setOptions(Set<Long> options) {
		this.options = options;
	}

}
