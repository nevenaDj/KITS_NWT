package com.example.dto;

import java.util.HashSet;
import java.util.Set;

public class AgendaDTO {
	private Long id;
	private Set<AgendaPointDTO> agendaPoints = new HashSet<>();
	private Set<SurveyDTO> surveys = new HashSet<>();

	public AgendaDTO() {

	}

	public AgendaDTO(Long id, Set<AgendaPointDTO> agendaPoints, Set<SurveyDTO> surveys) {
		super();
		this.id = id;
		this.agendaPoints = agendaPoints;
		this.surveys = surveys;
	}

	public AgendaDTO(Set<AgendaPointDTO> agendaPoints, Set<SurveyDTO> surveys) {
		super();
		this.agendaPoints = agendaPoints;
		this.surveys = surveys;
	}

	public Set<AgendaPointDTO> getAgendaPoints() {
		return agendaPoints;
	}

	public void setAgendaPoints(Set<AgendaPointDTO> agendaPoints) {
		this.agendaPoints = agendaPoints;
	}

	public Set<SurveyDTO> getSurveys() {
		return surveys;
	}

	public void setSurveys(Set<SurveyDTO> surveys) {
		this.surveys = surveys;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
