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
	private BuildingDTO building= new BuildingDTO();

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
		if (meeting.getBuilding()!=null)
			this.building= new BuildingDTO(meeting.getBuilding());
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
	
	

	public BuildingDTO getBuilding() {
		return building;
	}

	public void setBuilding(BuildingDTO building) {
		this.building = building;
	}

	public AgendaDTO getAgenda() {
		return agenda;
	}

	public void setAgenda(AgendaDTO agenda) {
		this.agenda = agenda;
	}

	public static Meeting getMeeting(MeetingDTO meetingDTO) {
		Meeting m= new Meeting(meetingDTO.getId(), meetingDTO.getDateAndTime(), meetingDTO.isActive());
		Set<AgendaItem> items = new HashSet<AgendaItem>();
		if (meetingDTO.getAgenda()!=null) {
			if (meetingDTO.getAgenda().getAgendaPoints()!=null) {
				for (AgendaItemDTO itemDTO: meetingDTO.getAgenda().getAgendaPoints()){
					items.add(AgendaItemDTO.getAgendaPoint(itemDTO));
				}
				m.setPoints(items);
			}
			else {
				m.setPoints(new HashSet<AgendaItem>());
			}
			if (meetingDTO.getAgenda().getSurveys()!=null) {
				Set<Survey> surveys = new HashSet<Survey>();
				for (SurveyDTO surveyDTO: meetingDTO.getAgenda().getSurveys()){
					surveys.add(SurveyDTO.getSurvey(surveyDTO));
				}
				m.setSurveys(surveys);
			}
			else {
				m.setSurveys(new HashSet<Survey>());
			}
		}else {
			m.setPoints(new HashSet<AgendaItem>());
			m.setSurveys(new HashSet<Survey>());
		}
		
		

		if (meetingDTO.getBuilding()!=null)
			m.setBuilding(BuildingDTO.getBuilding(meetingDTO.getBuilding()));
		return m;

	}

}