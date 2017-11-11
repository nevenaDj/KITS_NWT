package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.NotificationDTO;
import com.example.dto.OptionDTO;
import com.example.dto.QuestionDTO;
import com.example.dto.SurveyDTO;
import com.example.dto.UserDTO;
import com.example.model.Meeting;
import com.example.model.Notification;
import com.example.model.Option;
import com.example.model.Question;
import com.example.model.Survey;
import com.example.model.User;
import com.example.service.MeetingService;
import com.example.service.NotificationService;
import com.example.service.SurveyService;

@RequestMapping(value = "/meetings/{id}/notifications")
@RestController
public class NotificationController {
	@Autowired
	MeetingService meetingService;
	@Autowired
	NotificationService notificationService;
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<NotificationDTO> addNotification(@PathVariable Long id, @RequestBody NotificationDTO notificationDTO) {
		Notification notification = NotificationDTO.getNotification(notificationDTO);

		Meeting meeting = meetingService.findOne(id);
		if (meeting == null) {
			return new ResponseEntity<NotificationDTO>(HttpStatus.BAD_REQUEST);
		}
		notification.setMeeting(meeting);
		notification = notificationService.save(notification);
		notificationDTO.setId(notification.getId());

		return new ResponseEntity<NotificationDTO>(notificationDTO, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, produces= "application/json")
	public ResponseEntity<List<NotificationDTO>> getNotifications(@PathVariable Long id,Pageable page) {

		Meeting meeting = meetingService.findOne(id);
		if (meeting == null) {
			return new ResponseEntity<List<NotificationDTO>>(HttpStatus.BAD_REQUEST);
		}
		
		Page<Notification> notifications = notificationService.findAllByMeeting(page, id);
		
		
		List<NotificationDTO> notificationsDTO = new ArrayList<NotificationDTO>();
		for (Notification notification : notifications) {
			notificationsDTO.add(new NotificationDTO(notification));
		}
		return new ResponseEntity<List<NotificationDTO>>(notificationsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{not_id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteNotificaton(@PathVariable("id") Long id, @PathVariable("not_id")  Long notifications_id ) { // da li je dobro tako????
		Meeting meeting = meetingService.findOne(id);
		if (meeting == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		Notification notification = notificationService.findOne(notifications_id);
		if (notification == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			notificationService.remove(notifications_id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/{not_id}", method = RequestMethod.GET, produces= "application/json")
	public ResponseEntity<NotificationDTO> getNotificaton(@PathVariable("id") Long id, @PathVariable("not_id") Long notifications_id) { // da li je dobro tako????
		Meeting meeting = meetingService.findOne(id);
		if (meeting == null) {
			return new ResponseEntity<NotificationDTO>(HttpStatus.BAD_REQUEST);
		}
		
		Notification notification = notificationService.findOne(notifications_id);
		if (notification == null) {
			return new ResponseEntity<NotificationDTO>(HttpStatus.NOT_FOUND);
		} else {
			NotificationDTO notificationDTO= new NotificationDTO(notification);
			return new ResponseEntity<NotificationDTO>(notificationDTO, HttpStatus.OK);
		}
	}
}
