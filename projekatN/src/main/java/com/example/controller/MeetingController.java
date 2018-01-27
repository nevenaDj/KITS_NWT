package com.example.controller;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MeetingDTO;
import com.example.model.Building;
import com.example.model.Meeting;
import com.example.model.User;
import com.example.service.BuildingService;
import com.example.service.MeetingService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value = "meetings")
public class MeetingController {

	@Autowired
	MeetingService meetingService;
	
	@Autowired
	BuildingService buildingService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/buildings/{id}/meetings", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Create a meeting.", notes = "Returns the meeting being saved.", httpMethod = "POST", 
				produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = MeetingDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	/*** add a meeting ***/
	public ResponseEntity<MeetingDTO> addMeeting(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id,
			@ApiParam(value = "The meetingDTO object", required = true) @RequestBody MeetingDTO meetingDTO) {
		Meeting meeting = MeetingDTO.getMeeting(meetingDTO);
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		System.out.println("date: "+meetingDTO.getDateAndTime());
		meeting.setBuilding(building);
		meeting = meetingService.save(meeting);
		return new ResponseEntity<>(new MeetingDTO(meeting), HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/meetings/{id_meeting}", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get meetings.", notes = "Returns the meeting", httpMethod = "GET", 
	produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = MeetingDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** get meetings ***/
	public ResponseEntity<MeetingDTO> getMeeting(
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable Long id_meeting){
		Meeting meeting = meetingService.findOne(id_meeting);	
		if (meeting==null)
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		System.out.println("MEETING: "+meeting.getDateAndTime()+", agenda: "+meeting.getPoints().size());
		return new ResponseEntity<>(new MeetingDTO(meeting), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buildings/{id}/meetings", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get meetings.", notes = "Returns the meeting", httpMethod = "GET", 
	produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = MeetingDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** get meetings ***/
	public ResponseEntity<List<MeetingDTO>> getMeetings(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id,
			org.springframework.data.domain.Pageable page){
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Page<Meeting> meetings = meetingService.findByBuilding(id, page);
		List<MeetingDTO> meetingDTO = new ArrayList<>();
		for (Meeting meeting:meetings){
			meetingDTO.add(new MeetingDTO(meeting));
		}
	
		return new ResponseEntity<>(meetingDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buildings/{id}/meetings/count", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get meetings.", notes = "Returns the meeting", httpMethod = "GET", 
	produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = MeetingDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** get meetings ***/
	public ResponseEntity<Integer> getMeetingsCount(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id ){
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Integer count = meetingService.findByBuildingCount(id);
		return new ResponseEntity<>(count, HttpStatus.OK);
	}

/*
	@RequestMapping(value = "/buildings/{id}/meetings", method = RequestMethod.GET, produces = "application/json", params = {
			"date" }) 
	@ApiOperation(value = "Get meeting by a date.", notes = "Returns the meeting", httpMethod = "POST", 
	produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = MeetingDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** get meeting by date ***/
	/*
	public ResponseEntity<MeetingDTO> findMeetingByDate(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id,
			@ApiParam(name = "date", value = "Date of the meeting",required=true) @RequestParam("date") Date date) {

		Meeting meeting = meetingService.findMeetingByDate(id, date);

		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new MeetingDTO(meeting), HttpStatus.CREATED);
	}
*/
	@RequestMapping(value = "/buildings/{id}/meetings/dates", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get the dates where are meetings.", notes = "Returns the list of date.", httpMethod = "POST", 
	produces = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok", response = Date.class, responseContainer="List"),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** get the dates where are meetings ***/
	public ResponseEntity<List<Date>> findDatesOfMeetings(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id) {
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		List<Date> dates = meetingService.getDates(id);
		return new ResponseEntity<>(dates, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buildings/{b_id}/meetings/{m_id}/active", method = RequestMethod.PUT, produces = "application/json")
	@ApiOperation(value = "Set the agenda active.", notes = "Returns the meeting being saved.", httpMethod = "PUT", 
	produces = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Created", response = MeetingDTO.class),
		@ApiResponse(code = 400, message = "Bad request") })
	/*** set the agenda active ***/
	public ResponseEntity<MeetingDTO> setMeetingActive(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable("b_id") Long buildingId,
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable("m_id") Long meetingId) {
	
		Building building = buildingService.findOne(buildingId);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		Meeting meeting = meetingService.findOne(meetingId);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	
		meeting.setActive(true);
		meeting= meetingService.save(meeting);
		return new ResponseEntity<>(new MeetingDTO(meeting), HttpStatus.OK);
	}

	@RequestMapping(value = "/buildings/{b_id}/meeting/{m_id}/deactive", method = RequestMethod.PUT, produces = "application/json")
	@ApiOperation(value = "Set the agenda deactive.", notes = "Returns the meeting being saved.", httpMethod = "POST", 
	produces = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Ok", response = MeetingDTO.class),
		@ApiResponse(code = 400, message = "Bad request") })
	/*** set the agenda deactive ***/
	public ResponseEntity<MeetingDTO> deactivateMeeting(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable("b_id") Long buildingId,
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable("m_id") Long meetingId) {
	
		Building building = buildingService.findOne(buildingId);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		Meeting meeting = meetingService.findOne(meetingId);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		meeting.setActive(false);
		meeting= meetingService.save(meeting);
		return new ResponseEntity<>(new MeetingDTO(meeting), HttpStatus.OK);
	}

	@RequestMapping(value = "/owner/{id}/meetings", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get active meetings.", notes = "Returns the meeting being saved.", httpMethod = "POST", 
	produces = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Ok", response = MeetingDTO.class),
		@ApiResponse(code = 400, message = "Bad request") })
	/*** get active meeting ***/
	public ResponseEntity<List<MeetingDTO>> getActiveMeeting(
			@ApiParam(value = "The ID of the owner.", required = true) @PathVariable("id") Long id) {
	
		User owner = userService.findOne(id);
		if (owner == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Meeting> meetings= meetingService.findMeetingByOwner(id);
		List<MeetingDTO> meetingsDTO = new ArrayList<MeetingDTO>();
		Date today = new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		

		for (Meeting m:meetings) {		
			System.out.println(" meeting date: "+m.getDateAndTime());
			if (m.getDateAndTime().getTime()-today.getTime()<=0 ) {
				Date one_plus = new Date(m.getDateAndTime().getTime() + (1000 * 60 * 60 * 24));
				System.out.println(" if1 ");
				if (one_plus.getTime()- today.getTime()>=0) {
					System.out.println(" if2 ");
					meetingsDTO.add(new MeetingDTO(m));
				}
			}
		}
		System.out.println("meetingDTO size: "+meetingsDTO.size());
		return new ResponseEntity<>(meetingsDTO, HttpStatus.OK);
	}

}
