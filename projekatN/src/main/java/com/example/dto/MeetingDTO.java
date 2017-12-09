package com.example.dto;

import java.util.Date;

import com.example.model.Meeting;

public class MeetingDTO {
	private Long id;
	private Date dateAndTime;
	private boolean active;

	public MeetingDTO() {

	}

	public MeetingDTO(Meeting meeting) {
		this(meeting.getId(), meeting.getDateAndTime(), meeting.isActive());
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static Meeting getMeeting(MeetingDTO meetingDTO) {
		return new Meeting(meetingDTO.getId(), meetingDTO.getDateAndTime(), meetingDTO.isActive());

	}

}
