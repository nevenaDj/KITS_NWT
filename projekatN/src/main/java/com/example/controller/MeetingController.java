package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
