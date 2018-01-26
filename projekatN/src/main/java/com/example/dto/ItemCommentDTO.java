package com.example.dto;

import java.util.Date;

import com.example.model.ItemComment;
import com.example.model.User;

public class ItemCommentDTO {
	private Long id;

	private UserDTO writer;
	private String text;
	private Date date;

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
		User user= null;
		if (commentDTO.getWriter()!=null)
			user=UserDTO.getUser(commentDTO.getWriter());
		return new ItemComment(commentDTO.getId(), user, commentDTO.getText(),
				commentDTO.getDate());

	}
}
