package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Notification;
import com.example.repository.NotificationRepository;

@Service
public class NotificationService {
	@Autowired
	NotificationRepository notificationRepository;

	public Notification findOne(Long id) {
		return notificationRepository.findOne(id);
	}

	public Page<Notification> findAll(Pageable page) {
		return notificationRepository.findAll(page);
	}

	public Page<Notification> findAllByBuilding(Pageable page, Long buildingId) {
		return notificationRepository.findByBuilding(buildingId, page);
	}

	public Page<Notification> findAllByWriter(Pageable page, Long writerId) {
		return notificationRepository.findByWriter(writerId, page);
	}

	public Notification save(Notification notification) {
		return notificationRepository.save(notification);
	}

	public void remove(Long id) {
		notificationRepository.delete(id);
	}

	public List<Notification> findWithoutMeeting() {
		return notificationRepository.findWithoutMeeting();
	}

	public Long getCountOfNotifications(Long idBuilding) {
		return notificationRepository.getCountOfNotifications(idBuilding);
	}
}
