package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.example.dto.GlitchDTO;
import com.example.dto.NotificationDTO;
import com.example.dto.OptionDTO;
import com.example.dto.QuestionDTO;
import com.example.dto.SurveyDTO;
import com.example.dto.UserDTO;
import com.example.model.Building;
import com.example.model.Glitch;
import com.example.model.Meeting;
import com.example.model.Notification;
import com.example.model.Option;
import com.example.model.Question;
import com.example.model.Survey;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.BuildingService;
import com.example.service.MeetingService;
import com.example.service.NotificationService;
import com.example.service.SurveyService;
import com.example.service.UserService;

@RestController
public class NotificationController {
	@Autowired
	BuildingService buildingService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	UserService userService;
	
	
	@RequestMapping(value = "/buildings/{id}/notifications", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<NotificationDTO> addNotification(@PathVariable Long id, @RequestBody NotificationDTO notificationDTO, 
			HttpServletRequest request) {
		Notification notification = NotificationDTO.getNotification(notificationDTO);

		
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<NotificationDTO>(HttpStatus.BAD_REQUEST);
		}
		
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User writer = userService.findByUsername(username);		
		notification.setWriter(writer);
		
		notification.setBuilding(building);
		notification = notificationService.save(notification);
		notificationDTO.setId(notification.getId());

		return new ResponseEntity<NotificationDTO>(notificationDTO, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/buildings/{id}/notifications", method = RequestMethod.GET, produces= "application/json")
	public ResponseEntity<List<NotificationDTO>> getNotifications(@PathVariable Long id,Pageable page) {

		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<List<NotificationDTO>>(HttpStatus.BAD_REQUEST);
		}
		
		Page<Notification> notifications = notificationService.findAllByBuilding(page, id);
		
		
		List<NotificationDTO> notificationsDTO = new ArrayList<NotificationDTO>();
		for (Notification notification : notifications) {
			notificationsDTO.add(new NotificationDTO(notification));
		}
		return new ResponseEntity<List<NotificationDTO>>(notificationsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buildings/{id}/notifications/{not_id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteNotificaton(@PathVariable("id") Long id, @PathVariable("not_id")  Long notifications_id ) { // da li je dobro tako????
		Building building = buildingService.findOne(id);
		if (building == null) {
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
	
	@RequestMapping(value = "/buildings/{id}/notifications/{not_id}", method = RequestMethod.GET, produces= "application/json")
	public ResponseEntity<NotificationDTO> getNotificaton(@PathVariable("id") Long id, @PathVariable("not_id") Long notifications_id) { // da li je dobro tako????
		Building building = buildingService.findOne(id);
		if (building == null) {
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
	
	@RequestMapping(value = "/notifications", method = RequestMethod.GET, produces= "application/json") // user's own notifications
	public ResponseEntity<List<NotificationDTO>> getNotifications(Pageable page, HttpServletRequest request) {

		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User writer = userService.findByUsername(username);		

		Page<Notification> notifications = notificationService.findAllByWriter(page, writer.getId());
		
		
		List<NotificationDTO> notificationsDTO = new ArrayList<NotificationDTO>();
		for (Notification notification : notifications) {
			notificationsDTO.add(new NotificationDTO(notification));
		}
		return new ResponseEntity<List<NotificationDTO>>(notificationsDTO, HttpStatus.OK);
	}
}
