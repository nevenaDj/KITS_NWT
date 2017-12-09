package com.example.dto;

import com.example.model.Comment;

public class CommentDTO {
	private Long id;
	private String text;

	public CommentDTO() {

	}

	public CommentDTO(Long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public CommentDTO(Comment comment) {
		this(comment.getId(), comment.getText());
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static Comment getComment(CommentDTO commnetDTO) {
		return new Comment(commnetDTO.getId(), commnetDTO.getText());
	}

}
