
package com.example.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.example.dto.AgendaItemDTO;
import com.example.dto.CommunalProblemDTO;
import com.example.dto.ContentWithoutAgendaDTO;
import com.example.dto.GlitchDTO;
import com.example.dto.ItemCommentDTO;
import com.example.dto.NotificationDTO;
import com.example.dto.SurveyDTO;
import com.example.model.AgendaItem;
import com.example.model.CommunalProblem;
import com.example.model.Glitch;
import com.example.model.ItemComment;
import com.example.model.Meeting;
import com.example.model.Notification;
import com.example.model.Survey;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.AgendaItemService;
import com.example.service.BuildingService;
import com.example.service.CommunalProblemService;
import com.example.service.GlitchService;
import com.example.service.ItemCommentService;
import com.example.service.MeetingService;
import com.example.service.NotificationService;
import com.example.service.SurveyService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value = "agenda")
public class AgendaController {
	@Autowired
	AgendaItemService agendaPointService;
	@Autowired
	MeetingService meetingService;
	@Autowired
	BuildingService buildingService;
	@Autowired
	TokenUtils tokenUtils;
	@Autowired
	UserService userService;
	@Autowired
	SurveyService surveyService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	GlitchService glitchService;
	@Autowired
	CommunalProblemService comProblemsService;
	@Autowired
	ItemCommentService commentService;

	@RequestMapping(value = "/meetings/{id}/items", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Create agenda item.", notes = "Returns the item being saved.",
		httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = AgendaItemDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })	
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<AgendaItemDTO> addAgendaItem(
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable("id") Long id,
			@ApiParam(value = "The AgendaItemDTO Object.", required = true) @RequestBody AgendaItemDTO agendaPointDTO) {

		Meeting meeting = meetingService.findOne(id);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		AgendaItem agendaPoint = AgendaItemDTO.getAgendaPoint(agendaPointDTO);
		agendaPoint.setMeeting(meeting);

		agendaPoint = agendaPointService.save(agendaPoint);

		return new ResponseEntity<>(new AgendaItemDTO(agendaPoint), HttpStatus.CREATED);

	}

	@RequestMapping(value = "/meetings/{m_id}/items/{id}", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get an agenda item by id.", notes = "Returns the item which is found.",
		httpMethod = "GET", produces = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok", response = AgendaItemDTO.class),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found")})	
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<AgendaItemDTO> getAgendaPoint(
			@ApiParam(value = "The ID of the item.", required = true) @PathVariable("id") Long id,
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable("m_id") Long meetingId ) {
		Meeting meeting = meetingService.findOne(meetingId);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		AgendaItem agendaPoint = agendaPointService.findOne(id);

		if (agendaPoint == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new AgendaItemDTO(agendaPoint), HttpStatus.OK);
	}

	@RequestMapping(value = "/agendas/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get the whole agenda.", notes = "Returns the agenda.",
		httpMethod = "GET", produces = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok", response = AgendaDTO.class)})
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<AgendaDTO> getAgenda(
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable Long id) {
		Collection<AgendaItem> agendaPoints = agendaPointService.findAllAgendaPoints(id);

		HashSet<AgendaItemDTO> agendaPointsDTO = new HashSet<>();
		for (AgendaItem agendaPoint : agendaPoints) {
			agendaPointsDTO.add(new AgendaItemDTO(agendaPoint));

		}
		Collection<Survey> surveys = surveyService.findAllSurveys(id);
		HashSet<SurveyDTO> surveysDTO = new HashSet<>();
		for (Survey survey : surveys) {
			surveysDTO.add(new SurveyDTO(survey));
		}

		AgendaDTO agendaDTO = new AgendaDTO(id, agendaPointsDTO, surveysDTO);

		return new ResponseEntity<>(agendaDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/agendas/no_meeting", method = RequestMethod.GET)	
	@ApiOperation(value = "Get contents(glitches, notifications, commaunal problems) whiach are not in any agenda.",
	notes = "Returns the contnts.",
		httpMethod = "GET", produces = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok", response = ContentWithoutAgendaDTO.class)})
	@PreAuthorize("hasAnyRole( 'ROLE_PRESIDENT')")
	public ResponseEntity<ContentWithoutAgendaDTO> getAgendaWithoutMeeting() {

		ContentWithoutAgendaDTO noMeeting = new ContentWithoutAgendaDTO();
		List<Notification> notifications = notificationService.findWithoutMeeting();
		List<Glitch> glitches = glitchService.findWithoutMeeting();
		List<CommunalProblem> problems = comProblemsService.findWithoutMeeting();
		List<NotificationDTO> notificationsDTO = new ArrayList<>();
		List<GlitchDTO> glitchesDTO = new ArrayList<>();
		List<CommunalProblemDTO> problemsDTO = new ArrayList<>();
		for (Notification n : notifications) {
			notificationsDTO.add(new NotificationDTO(n));
		}
		for (Glitch g : glitches) {
			glitchesDTO.add(new GlitchDTO(g));
		}
		for (CommunalProblem cp : problems) {
			problemsDTO.add(new CommunalProblemDTO(cp));
		}

		noMeeting.setGlitches(glitchesDTO);
		noMeeting.setNotifications(notificationsDTO);
		noMeeting.setProblems(problemsDTO);
		return new ResponseEntity<>(noMeeting, HttpStatus.OK);

	}

	@RequestMapping(value = "/meetings/{m_id}/items/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete an item from the agenda", httpMethod = "DELETE")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),})
	@PreAuthorize("hasAnyRole('ROLE_PRESIDENT')")
	public ResponseEntity<Void> deleteAgenda(
			@ApiParam(value = "The ID of the item.", required = true) @PathVariable("id") Long id,
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable("m_id") Long meetingId) {
		AgendaItem agendaItem = agendaPointService.findOne(id);

		Meeting meeting = meetingService.findOne(meetingId);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (agendaItem == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		agendaPointService.delete(id);
		// glich.item????

		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@RequestMapping(value = "/meetings/{m_id}/items/{id}", method = RequestMethod.PUT, consumes="application/json" , produces="application/json")
	@ApiOperation(value = "Update an item from the agenda", notes="Returns the updated item.",
		httpMethod = "PUT", consumes="application/json" , produces="application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok", response = AgendaItemDTO.class),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found")})
	@PreAuthorize("hasAnyRole('ROLE_PRESIDENT')")
	public ResponseEntity<AgendaItemDTO> updateAgenda(
			@ApiParam(value = "The ID of the item.", required = true) @PathVariable("id") Long id, 
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable("m_id") Long meetingId,
			@RequestBody AgendaItemDTO itemDTO) {
		Meeting meeting = meetingService.findOne(meetingId);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		AgendaItem agendaItem = agendaPointService.findOne(id);

		if (agendaItem==null){
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
		if (itemDTO.getConclusion()!=null & agendaItem.getConclusion()!=null )
			if (!itemDTO.getConclusion().equals(agendaItem.getConclusion()))
				agendaItem.setConclusion(itemDTO.getConclusion());
		if (itemDTO.getTitle()!=null & agendaItem.getTitle()!=null)
			if (!itemDTO.getTitle().equals(agendaItem.getTitle()))
				agendaItem.setTitle(itemDTO.getTitle());
		if (itemDTO.getNumber()!=agendaItem.getNumber())
			agendaItem.setNumber(itemDTO.getNumber());
		agendaPointService.save(agendaItem);
	
		
		return new ResponseEntity<>(new AgendaItemDTO(agendaItem), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/meetings/{m_id}/items/{id}/conclusion", method = RequestMethod.PUT, consumes="application/json" , produces="application/json")
	@ApiOperation(value = "Update an item's conclucion in the agenda", notes="Returns the updated item.",
		httpMethod = "PUT", consumes="application/json" , produces="application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok", response = AgendaItemDTO.class),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found")})
	@PreAuthorize("hasAnyRole('ROLE_PRESIDENT')")
	public ResponseEntity<AgendaItemDTO> updateConclusionAgenda(
			@ApiParam(value = "The ID of the item.", required = true) @PathVariable("id") Long id, 
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable("m_id") Long meetingId,
			@ApiParam(value = "The AgendaItemDTO object.", required = true) @RequestBody AgendaItemDTO itemDTO) {
		Meeting meeting = meetingService.findOne(meetingId);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		AgendaItem agendaItem = agendaPointService.findOne(id);

		if (agendaItem==null){
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
		
		agendaItem.setConclusion(itemDTO.getConclusion());
		agendaPointService.save(agendaItem);

		return new ResponseEntity<AgendaItemDTO>(new AgendaItemDTO(agendaItem), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/agendas/", method = RequestMethod.PUT, consumes="application/json" , produces="application/json")
	@ApiOperation(value = "Update an item's number in the agenda", notes="Returns the updated items.",
		httpMethod = "PUT", consumes="application/json" , produces="application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok", response = AgendaDTO.class),
			@ApiResponse(code = 404, message = "Not found")})
	@PreAuthorize("hasAnyRole('ROLE_PRESIDENT')")
	public ResponseEntity<AgendaDTO> updateAgendaItemNumber(
			@ApiParam(value = "The AgendaDTO obejct.", required = true) @RequestBody AgendaDTO agendaDTO) {
		
		for (AgendaItemDTO itemDTO : agendaDTO.getAgendaPoints()) {
			AgendaItem agendaItem = agendaPointService.findOne(itemDTO.getId());
			if (agendaItem==null){
				return new ResponseEntity<>( HttpStatus.NOT_FOUND);
			}
			if (itemDTO.getNumber()!=agendaItem.getNumber())
				agendaItem.setNumber(itemDTO.getNumber());
			agendaPointService.save(agendaItem);
		}
	
		return new ResponseEntity<>(agendaDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/meetings/{m_id}/items/{id}/comments", method = RequestMethod.POST, 
			consumes="application/json" , produces="application/json")
	@ApiOperation(value = "Add a new comment to the agenda item", notes="Returns the updated item.",
		httpMethod = "POST", consumes="application/json" , produces="application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = AgendaItemDTO.class),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found")})
	@PreAuthorize("hasAnyRole('ROLE_OWNER')")
	public ResponseEntity<AgendaItemDTO> addComment(
			@ApiParam(value = "The ID of the item.", required = true)@PathVariable("id") Long id,
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable("m_id") Long meetingId,
			@ApiParam(value = "The ItemCommentDTO object.", required = true) @RequestBody ItemCommentDTO commentDTO,
			HttpServletRequest request) {
		AgendaItem agendaItem = agendaPointService.findOne(id);
		Meeting meeting = meetingService.findOne(meetingId);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (agendaItem==null){
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
	
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User writer = userService.findByUsername(username);		
		ItemComment comment= ItemCommentDTO.getComment(commentDTO);
		comment.setWriter(writer);
		Set<ItemComment> comments= agendaItem.getComments();
		if (comments==null)
			comments= new HashSet<>();
		comments.add(comment);
		agendaItem.setComments(comments);
		
		agendaPointService.save(agendaItem);
	
		
		return new ResponseEntity<>(new AgendaItemDTO(agendaItem), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/meetings/{m_id}/items/{id}/comments", method = RequestMethod.GET,
			produces="application/json")
	@ApiOperation(value = "Gett the comments from the agenda item", notes="Returns the the list of commment.",
		httpMethod = "GET" , produces="application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok", response = ItemCommentDTO.class, responseContainer="List"),
			@ApiResponse(code = 400, message = "Bad request")})
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_PRESIDENT', 'ROLE_TENANT')")
	public ResponseEntity<List<ItemCommentDTO>> getComments(
			@ApiParam(value = "The ID of the item.", required = true) @PathVariable("id") Long id,
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable("m_id") Long meetingId) {
		Meeting meeting = meetingService.findOne(meetingId);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		AgendaItem agendaItem = agendaPointService.findOne(id);

		if (agendaItem==null){
			return new ResponseEntity<List<ItemCommentDTO>>( HttpStatus.BAD_REQUEST);

		}
		Set<ItemComment> comments= agendaItem.getComments();
		if (comments==null)
			comments= new HashSet<>();
		List<ItemCommentDTO> commentsDTO= new ArrayList<>();
		for (ItemComment itemComment : comments) {
			commentsDTO.add(new ItemCommentDTO(itemComment));
		}
		
		return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/meetings/{m_id}/items/{id}/comments/{comment_id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a comment from the agenda item", httpMethod = "DELETE" )
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found")})
	
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<Void>deleteComment(
			@ApiParam(value = "The ID of the item.", required = true) @PathVariable("id") Long id, 
			@ApiParam(value = "The ID of the comment.", required = true) @PathVariable("comment_id") Long commentId,
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable("m_id") Long meetingId) {
		AgendaItem agendaItem = agendaPointService.findOne(id);

		Meeting meeting = meetingService.findOne(meetingId);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (agendaItem==null){
			return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST);
		}

		Set<ItemComment> comments= agendaItem.getComments();
		for (ItemComment com : comments) {
			if (com.getId()==commentId){
				comments.remove(com);
			}
		}
		
		agendaItem.setComments(comments);
		agendaPointService.save(agendaItem);
	
		commentService.delete(commentId);	
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
