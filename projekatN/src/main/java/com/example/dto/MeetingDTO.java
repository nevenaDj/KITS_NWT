package com.example.dto;

import java.util.Date;

import com.example.model.Meeting;

public class MeetingDTO {
	private Long id;
	private Date dateAndTime;

	public MeetingDTO() {

	}

	public MeetingDTO(Meeting meeting) {
		this(meeting.getId(), meeting.getDateAndTime());
	}

	public MeetingDTO(Long id, Date dateAndTime) {
		super();
		this.id = id;
		this.dateAndTime = dateAndTime;
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

	public static Meeting getMeeting(MeetingDTO meetingDTO) {
		return new Meeting(meetingDTO.getId(), meetingDTO.getDateAndTime());

	}

}