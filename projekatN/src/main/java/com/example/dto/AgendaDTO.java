package com.example.dto;

import java.util.HashSet;
import java.util.Set;

public class AgendaDTO {
	private Long id;
	private Set<AgendaItemDTO> agendaPoints = new HashSet<>();
	private Set<SurveyDTO> surveys = new HashSet<>();

	public AgendaDTO() {

	}

	public AgendaDTO(Long id, Set<AgendaItemDTO> agendaPoints, Set<SurveyDTO> surveys) {
		super();
		this.id = id;
		this.agendaPoints = agendaPoints;
		this.surveys = surveys;
	}

	public AgendaDTO(Set<AgendaItemDTO> agendaPoints, Set<SurveyDTO> surveys) {
		super();
		this.agendaPoints = agendaPoints;
		this.surveys = surveys;
	}

	public Set<AgendaItemDTO> getAgendaPoints() {
		return agendaPoints;
	}

	public void setAgendaPoints(Set<AgendaItemDTO> agendaPoints) {
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
