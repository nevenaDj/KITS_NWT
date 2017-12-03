package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Notification;
import com.example.model.Pricelist;
import com.example.repository.NotificationRepository;
import com.example.repository.PricelistRepository;

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
	
	public Page<Notification> findAllByBuilding(Pageable page, Long building_id) {
		return notificationRepository.findByBuilding(building_id, page);
	}

	public Notification save(Notification notification) {
		return notificationRepository.save(notification);
	}

	public void remove(Long id) {
		notificationRepository.delete(id);
	}
}
