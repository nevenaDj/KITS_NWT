package com.example.dto;

import java.util.Date;
import com.example.model.Notification;
import com.example.model.NotificationStatus;

public class NotificationDTO {

	private Long id;

	private Date date;
	private String text;
	private NotificationStatus status;
	private UserDTO user;

	public NotificationDTO() {
	}

	public NotificationDTO(Notification notification) {
		this(notification.getId(), notification.getDate(), notification.getText());
		if (notification.getWriter() != null) {
			this.user = new UserDTO(notification.getWriter());
		}
	}

	public NotificationDTO(Long id, Date date, String text) {
		super();
		this.id = id;
		this.date = date;
		this.text = text;
		this.status = NotificationStatus.WAITING;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NotificationStatus getStatus() {
		return status;
	}

	public void setStatus(NotificationStatus status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public static Notification getNotification(NotificationDTO notificationDTO) {
		return new Notification(notificationDTO.getId(), notificationDTO.date, notificationDTO.getText());
	}

}
