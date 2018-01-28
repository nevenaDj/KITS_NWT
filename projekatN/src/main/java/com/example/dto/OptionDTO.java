package com.example.dto;

import com.example.model.Option;
import com.example.model.Question;

public class OptionDTO {

	private Long id;
	private String text;
	private int count;

	public OptionDTO() {

	}

	public OptionDTO(Option option) {
		this(option.getId(), option.getText());
	}

	public OptionDTO(String text) {
		super();
		this.text = text;
	}

	public OptionDTO(Long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static Option getOption(OptionDTO optionDTO, Question question) {
		return new Option(optionDTO.getId(), optionDTO.getText(), question);
	}

}
