package com.example.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MeetingDTO;
import com.example.model.Building;
import com.example.model.Meeting;
import com.example.service.BuildingService;
import com.example.service.MeetingService;

@RestController
public class MeetingController {

	@Autowired
	MeetingService meetingService;
	@Autowired
	BuildingService buildingService;

	@RequestMapping(value = "/buildings/{id}/meetings", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<MeetingDTO> addMeeting(@PathVariable Long id, @RequestBody MeetingDTO meetingDTO) {
		Meeting meeting = MeetingDTO.getMeeting(meetingDTO);
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<MeetingDTO>(HttpStatus.BAD_REQUEST);
		}
		meeting.setBuilding(building);
		meeting = meetingService.save(meeting);
		return new ResponseEntity<MeetingDTO>(new MeetingDTO(meeting), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/buildings/{id}/meetings/", method = RequestMethod.POST, produces = "application/json")
	//@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<MeetingDTO> getMeetingByDate(@PathVariable Long id, @RequestBody Date date) {
		

		Meeting meeting = meetingService.findMeetingByDate(id, date);
		
		if (meeting == null) {
			return new ResponseEntity<MeetingDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<MeetingDTO>(new MeetingDTO(meeting), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/buildings/{id}/meetings/date", method = RequestMethod.GET, produces = "application/json")
	//@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<ArrayList<Date>> getDateOfMeetings(@PathVariable Long id) {
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<ArrayList<Date>>(HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<Date> dates= meetingService.getDates(id);
		return new ResponseEntity<ArrayList<Date>>(dates, HttpStatus.CREATED);
	}

}
