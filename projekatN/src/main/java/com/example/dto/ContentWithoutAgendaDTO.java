package com.example.dto;

import java.util.ArrayList;
import java.util.List;

public class ContentWithoutAgendaDTO {

	List<GlitchDTO> glitches = new ArrayList<>();
	List<NotificationDTO> notifications = new ArrayList<>();
	List<CommunalProblemDTO> communalProblems = new ArrayList<>();
	
	public ContentWithoutAgendaDTO(List<GlitchDTO> glitches, List<NotificationDTO> notifications,
			List<CommunalProblemDTO> communalProblems) {
		super();
		this.glitches = glitches;
		this.notifications = notifications;
		this.communalProblems = communalProblems;
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

	public List<CommunalProblemDTO> getCommunalProblems() {
		return communalProblems;
	}

	public void setCommunalProblems(List<CommunalProblemDTO> communalProblems) {
		this.communalProblems = communalProblems;
	}


	
	
	
	
}
