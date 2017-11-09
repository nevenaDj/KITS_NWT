package com.example.dto;

import com.example.model.Option;
import com.example.model.Question;

public class OptionDTO {

	private Long id;
	private String text;

	public OptionDTO() {

	}

	public OptionDTO(Option option) {
		this(option.getId(), option.getText());
	}

	public OptionDTO(Long id, String text) {
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

	public static Option getOption(OptionDTO optionDTO, Question question) {
		return new Option(optionDTO.getId(), optionDTO.getText(), question);
	}

}
