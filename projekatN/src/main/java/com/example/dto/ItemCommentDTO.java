package com.example.dto;

import java.util.Date;

import com.example.model.ItemComment;

public class ItemCommentDTO {
	Long id;

	UserDTO writer;
	String text;
	Date date;

	public ItemCommentDTO() {

	}

	public ItemCommentDTO(ItemComment comment) {
		this(comment.getId(), new UserDTO(comment.getWriter()), comment.getText(), comment.getDate());
	}

	public ItemCommentDTO(Long id, UserDTO writer, String text, Date date) {
		super();
		this.id = id;
		this.writer = writer;
		this.text = text;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getWriter() {
		return writer;
	}

	public void setWriter(UserDTO writer) {
		this.writer = writer;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static ItemComment getComment(ItemCommentDTO commentDTO) {
		return new ItemComment(commentDTO.getId(), UserDTO.getUser(commentDTO.getWriter()), commentDTO.getText(),
				commentDTO.getDate());

	}
}
