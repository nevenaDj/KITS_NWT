package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Building;
import com.example.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>  {
	
	@Query(value = "SELECT n.id, n.date, n.status, n.text FROM Notification n WHERE n.meeting.id = ?1",
		  countQuery = "SELECT count(*) FROM Notification n WHERE n.meeting.id = ?1",
		    nativeQuery = false)
	public Page<Notification> findByMeeting(Long meetingID, Pageable pageable);
	

}