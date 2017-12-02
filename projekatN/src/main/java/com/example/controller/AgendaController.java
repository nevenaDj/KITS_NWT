package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AgendaPointDTO;
import com.example.model.AgendaPoint;
import com.example.model.Meeting;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.AgendaPointService;
import com.example.service.MeetingService;
import com.example.service.UserService;

@RestController
public class AgendaController {

	@Autowired
	AgendaPointService agendaPointService;
	@Autowired
	MeetingService meetingService;
	@Autowired
	TokenUtils tokenUtils;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/agendas/{id}/points", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<AgendaPointDTO> addAgendaPoint(@PathVariable Long id,
			@RequestBody AgendaPointDTO agendaPointDTO, HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		Meeting meeting = meetingService.findOne(id);

		if (meeting == null) {
			return new ResponseEntity<AgendaPointDTO>(HttpStatus.BAD_REQUEST);
		}

		User user = userService.findByUsername(username);

		AgendaPoint agendaPoint = AgendaPointDTO.getAgendaPoint(agendaPointDTO);
		agendaPoint.setUser(user);
		agendaPoint.setMeeting(meeting);

		agendaPoint = agendaPointService.save(agendaPoint);

		return new ResponseEntity<AgendaPointDTO>(new AgendaPointDTO(agendaPoint), HttpStatus.CREATED);

	}

	@RequestMapping(value = "/points/{id}", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<AgendaPointDTO> getAgendaPoint(@PathVariable Long id) {
		AgendaPoint agendaPoint = agendaPointService.findOne(id);

		if (agendaPoint == null) {
			return new ResponseEntity<AgendaPointDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<AgendaPointDTO>(new AgendaPointDTO(agendaPoint), HttpStatus.OK);
	}
}
