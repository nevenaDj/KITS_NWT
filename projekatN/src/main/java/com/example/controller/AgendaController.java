package com.example.controller;

import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AgendaDTO;
import com.example.dto.AgendaPointDTO;
import com.example.dto.SurveyDTO;
import com.example.model.AgendaPoint;
import com.example.model.Meeting;
import com.example.model.Survey;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.AgendaPointService;
import com.example.service.MeetingService;
import com.example.service.SurveyService;
import com.example.service.UserService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api")
@Api(value = "agenda")
public class AgendaController {

	@Autowired
	AgendaPointService agendaPointService;
	@Autowired
	MeetingService meetingService;
	@Autowired
	TokenUtils tokenUtils;
	@Autowired
	UserService userService;
	@Autowired
	SurveyService surveyService;

	@RequestMapping(value = "/agendas/{id}/points", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_OWNER')")
	public ResponseEntity<AgendaPointDTO> addAgendaPoint(@PathVariable Long id,
			@RequestBody AgendaPointDTO agendaPointDTO, HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		Meeting meeting = meetingService.findOne(id);

		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User user = userService.findByUsername(username);

		AgendaPoint agendaPoint = AgendaPointDTO.getAgendaPoint(agendaPointDTO);
		agendaPoint.setUser(user);
		agendaPoint.setMeeting(meeting);

		agendaPoint = agendaPointService.save(agendaPoint);

		return new ResponseEntity<>(new AgendaPointDTO(agendaPoint), HttpStatus.CREATED);

	}

	@RequestMapping(value = "/points/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<AgendaPointDTO> getAgendaPoint(@PathVariable Long id) {
		AgendaPoint agendaPoint = agendaPointService.findOne(id);

		if (agendaPoint == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new AgendaPointDTO(agendaPoint), HttpStatus.OK);
	}

	@RequestMapping(value = "/agendas/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<AgendaDTO> getAgenda(@PathVariable Long id) {
		Collection<AgendaPoint> agendaPoints = agendaPointService.findAllAgendaPoints(id);

		HashSet<AgendaPointDTO> agendaPointsDTO = new HashSet<>();
		for (AgendaPoint agendaPoint : agendaPoints) {
			agendaPointsDTO.add(new AgendaPointDTO(agendaPoint));

		}

		Collection<Survey> surveys = surveyService.findAllSurveys(id);

		HashSet<SurveyDTO> surveysDTO = new HashSet<>();
		for (Survey survey : surveys) {
			surveysDTO.add(new SurveyDTO(survey));
		}

		AgendaDTO agendaDTO = new AgendaDTO(id, agendaPointsDTO, surveysDTO);

		return new ResponseEntity<>(agendaDTO, HttpStatus.OK);

	}
}
