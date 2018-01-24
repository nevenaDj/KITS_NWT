package com.example.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.model.AgendaItem;
import com.example.model.Meeting;
import com.example.model.Survey;

public class MeetingDTO {
	private Long id;
	private Date dateAndTime;
	private boolean active;
	private AgendaDTO agenda = new AgendaDTO();

	public MeetingDTO() {

	}

	public MeetingDTO(Meeting meeting) {
		this(meeting.getId(), meeting.getDateAndTime(), meeting.isActive());
		Set<AgendaItemDTO> itemsDTO = new HashSet<AgendaItemDTO>();
		for (AgendaItem item: meeting.getPoints()){
			itemsDTO.add(new AgendaItemDTO(item));
		}
		agenda.setAgendaPoints(itemsDTO);
		Set<SurveyDTO> surveyDTO = new HashSet<SurveyDTO>();
		for (Survey survey: meeting.getSurveys()){
			surveyDTO.add(new SurveyDTO(survey));
		}
		agenda.setSurveys(surveyDTO);
	}

	public MeetingDTO(Date dateAndTime) {
		super();
		this.dateAndTime = dateAndTime;
	}

	public MeetingDTO(Long id, Date dateAndTime, boolean active) {
		super();
		this.id = id;
		this.dateAndTime = dateAndTime;
		this.active=active;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	

	public AgendaDTO getAgenda() {
		return agenda;
	}

	public void setAgenda(AgendaDTO agenda) {
		this.agenda = agenda;
	}

	public static Meeting getMeeting(MeetingDTO meetingDTO) {
		Set<AgendaItem> items = new HashSet<AgendaItem>();
		for (AgendaItemDTO itemDTO: meetingDTO.getAgenda().getAgendaPoints()){
			items.add(AgendaItemDTO.getAgendaPoint(itemDTO));
		}
		Set<Survey> surveys = new HashSet<Survey>();
		for (SurveyDTO surveyDTO: meetingDTO.getAgenda().getSurveys()){
			surveys.add(SurveyDTO.getSurvey(surveyDTO));
		}
		Meeting m= new Meeting(meetingDTO.getId(), meetingDTO.getDateAndTime(), meetingDTO.isActive());
		m.setPoints(items);
		m.setSurveys(surveys);
		return m;

	}

}