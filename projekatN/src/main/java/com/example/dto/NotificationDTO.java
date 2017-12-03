package com.example.dto;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.example.model.Meeting;
import com.example.model.Notification;
import com.example.model.NotificationStatus;
import com.example.model.User;

public class NotificationDTO {

	private Long id;
	
	private Date date;
	private String text;
	private NotificationStatus status;


	public NotificationDTO() {
	}

	public NotificationDTO(Notification notification) {
		this(notification.getId(), notification.getDate(), notification.getText());
	}

	public NotificationDTO(Long id, Date date, String text) {
		super();
		this.id = id;
		this.date = date;
		this.text = text;
		this.status= NotificationStatus.WAITING;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public NotificationStatus getStatus() {
		return status;
	}

	public void setStatus(NotificationStatus status) {
		this.status = status;
	}

	public static Notification getNotification(NotificationDTO notificationDTO) {
		return new Notification(notificationDTO.getId(), notificationDTO.date, notificationDTO.getText());
	}
	

}
