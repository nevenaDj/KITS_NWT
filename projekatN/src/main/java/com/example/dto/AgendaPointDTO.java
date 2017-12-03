package com.example.dto;

import com.example.model.AgendaPoint;

public class AgendaPointDTO {
	private Long id;

	private String content;
	private String comment;

	public AgendaPointDTO() {

	}

	public AgendaPointDTO(AgendaPoint agendaPoint) {
		this(agendaPoint.getId(), agendaPoint.getContent(), agendaPoint.getComment());
	}

	public AgendaPointDTO(Long id, String content, String comment) {
		super();
		this.id = id;
		this.content = content;
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public static AgendaPoint getAgendaPoint(AgendaPointDTO agendaPointDTO) {
		return new AgendaPoint(agendaPointDTO.getId(), agendaPointDTO.getContent(), agendaPointDTO.getComment());
	}

}
