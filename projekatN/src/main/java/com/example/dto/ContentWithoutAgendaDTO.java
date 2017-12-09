package com.example.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContentWithoutAgendaDTO {

	List<GlitchDTO> glitches = new ArrayList<GlitchDTO>();
	List<NotificationDTO> notifications = new ArrayList<NotificationDTO>();
	List<CommunalProblemDTO> problems = new ArrayList<CommunalProblemDTO>();
	
	public ContentWithoutAgendaDTO(List<GlitchDTO> glitches, List<NotificationDTO> notifications,
			List<CommunalProblemDTO> problems) {
		super();
		this.glitches = glitches;
		this.notifications = notifications;
		this.problems = problems;
	}
	
	public ContentWithoutAgendaDTO() {
		super();
	}

	public List<GlitchDTO> getGlitches() {
		return glitches;
	}

	public void setGlitches(List<GlitchDTO> glitches) {
		this.glitches = glitches;
	}

	public List<NotificationDTO> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<NotificationDTO> notifications) {
		this.notifications = notifications;
	}

	public List<CommunalProblemDTO> getProblems() {
		return problems;
	}

	public void setProblems(List<CommunalProblemDTO> problems) {
		this.problems = problems;
	}
	
	
	
	
	
}
