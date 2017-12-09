
package com.example.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.example.model.Building;
import com.example.model.CommunalProblem;
import com.example.model.Glitch;
import com.example.model.ItemComment;
import com.example.model.Meeting;
import com.example.model.Notification;
import com.example.model.Survey;
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

	@RequestMapping(value = "/buildings/{building_id}/meetings/{id}/points", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<AgendaItemDTO> addAgendaItem(@PathVariable("id") Long id,
			@PathVariable("building_id") Long buildingId, @RequestBody AgendaItemDTO agendaPointDTO) {

		Building building = buildingService.findOne(buildingId);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Meeting meeting = meetingService.findOne(id);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		AgendaItem agendaPoint = AgendaItemDTO.getAgendaPoint(agendaPointDTO);
		agendaPoint.setMeeting(meeting);

		agendaPoint = agendaPointService.save(agendaPoint);

		return new ResponseEntity<>(new AgendaItemDTO(agendaPoint), HttpStatus.CREATED);

	}

	@RequestMapping(value = "/points/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<AgendaItemDTO> getAgendaPoint(@PathVariable Long id) {
		AgendaItem agendaPoint = agendaPointService.findOne(id);

		if (agendaPoint == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new AgendaItemDTO(agendaPoint), HttpStatus.OK);
	}

	@RequestMapping(value = "/agendas/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<AgendaDTO> getAgenda(@PathVariable Long id) {
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
	@PreAuthorize("hasAnyRole( 'ROLE_PRESIDENT')")
	public ResponseEntity<ContentWithoutAgendaDTO> getAgendaWithoutMeeting(@PathVariable Long id) {

		ContentWithoutAgendaDTO no_meeting = new ContentWithoutAgendaDTO();
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

		no_meeting.setGlitches(glitchesDTO);
		no_meeting.setNotifications(notificationsDTO);
		no_meeting.setProblems(problemsDTO);
		return new ResponseEntity<>(no_meeting, HttpStatus.OK);

	}

	@RequestMapping(value = "/agendas/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('ROLE_PRESIDENT')")
	public ResponseEntity<Void> deleteAgenda(@PathVariable Long id) {
		AgendaItem agendaItem = agendaPointService.findOne(id);

		if (agendaItem == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		agendaPointService.delete(id);
		// glich.item????

		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@RequestMapping(value = "/agendas/{id}", method = RequestMethod.PUT, consumes="application/json" , produces="application/json")
	@PreAuthorize("hasAnyRole('ROLE_PRESIDENT')")
	public ResponseEntity<AgendaItemDTO> updateAgenda(@PathVariable Long id, @RequestBody AgendaItemDTO itemDTO) {
		AgendaItem agendaItem = agendaPointService.findOne(id);

		if (agendaItem==null){
			return new ResponseEntity<AgendaItemDTO>( HttpStatus.NOT_FOUND);
		}
		
		if (!itemDTO.getConclusion().equals(agendaItem.getConclusion()))
			agendaItem.setConclusion(itemDTO.getConclusion());
		if (!itemDTO.getTitle().equals(agendaItem.getTitle()))
			agendaItem.setTitle(itemDTO.getTitle());
		if (itemDTO.getNumber()!=agendaItem.getNumber())
			agendaItem.setNumber(itemDTO.getNumber());
		agendaPointService.save(agendaItem);
	
		
		return new ResponseEntity<AgendaItemDTO>(new AgendaItemDTO(agendaItem), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/agendas/{id}/conclusion", method = RequestMethod.PUT, consumes="application/json" , produces="application/json")
	@PreAuthorize("hasAnyRole('ROLE_PRESIDENT')")
	public ResponseEntity<AgendaItemDTO> updateConclusionAgenda(@PathVariable Long id, @RequestBody AgendaItemDTO itemDTO) {
		AgendaItem agendaItem = agendaPointService.findOne(id);

		if (agendaItem==null){
			return new ResponseEntity<AgendaItemDTO>( HttpStatus.NOT_FOUND);
		}
		
		if (!itemDTO.getConclusion().equals(agendaItem.getConclusion()))
			agendaItem.setConclusion(itemDTO.getConclusion());
		agendaPointService.save(agendaItem);
	
		
		return new ResponseEntity<AgendaItemDTO>(new AgendaItemDTO(agendaItem), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/agendas/", method = RequestMethod.PUT, consumes="application/json" , produces="application/json")
	@PreAuthorize("hasAnyRole('ROLE_PRESIDENT')")
	public ResponseEntity<AgendaDTO> updateAgendaItemNumber(@PathVariable Long id, @RequestBody AgendaDTO agendaDTO) {
		
		for (AgendaItemDTO itemDTO : agendaDTO.getAgendaPoints()) {
			AgendaItem agendaItem = agendaPointService.findOne(itemDTO.getId());
			if (agendaItem==null){
				return new ResponseEntity<AgendaDTO>( HttpStatus.NOT_FOUND);
			}
			if (itemDTO.getNumber()!=agendaItem.getNumber())
				agendaItem.setNumber(itemDTO.getNumber());
			agendaPointService.save(agendaItem);
		}
		
		
		return new ResponseEntity<AgendaDTO>(agendaDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/agendas/{id}/comments", method = RequestMethod.POST, consumes="application/json" , produces="application/json")
	@PreAuthorize("hasAnyRole('ROLE_OWNER')")
	public ResponseEntity<AgendaItemDTO> addComment(@PathVariable Long id, @RequestBody ItemCommentDTO commentDTO,
			HttpServletRequest request) {
		AgendaItem agendaItem = agendaPointService.findOne(id);

		if (agendaItem==null){
			return new ResponseEntity<AgendaItemDTO>( HttpStatus.NOT_FOUND);
		}
	
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User writer = userService.findByUsername(username);		
		ItemComment comment= ItemCommentDTO.getComment(commentDTO);
		comment.setWriter(writer);
		Set<ItemComment> comments= agendaItem.getComments();
		if (comments==null)
			comments= new HashSet<ItemComment>();
		comments.add(comment);
		agendaItem.setComments(comments);
		
		agendaPointService.save(agendaItem);
	
		
		return new ResponseEntity<AgendaItemDTO>(new AgendaItemDTO(agendaItem), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/agendas/{id}/comments", method = RequestMethod.GET, consumes="application/json" , produces="application/json")
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_PRESIDENT', 'ROLE_TENANT')")
	public ResponseEntity<List<ItemCommentDTO>> getComments(@PathVariable Long id) {
		AgendaItem agendaItem = agendaPointService.findOne(id);

		if (agendaItem==null){
			return new ResponseEntity<List<ItemCommentDTO>>( HttpStatus.NOT_FOUND);
		}
		Set<ItemComment> comments= agendaItem.getComments();
		if (comments==null)
			comments= new HashSet<ItemComment>();
		List<ItemCommentDTO> commentsDTO= new ArrayList<ItemCommentDTO>();
		for (ItemComment itemComment : comments) {
			commentsDTO.add(new ItemCommentDTO(itemComment));
		}
		
		return new ResponseEntity<List<ItemCommentDTO>>(commentsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/agendas/{id}/comments/{comment_id}", method = RequestMethod.GET, consumes="application/json" , produces="application/json")
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<Void>deleteComment(@PathVariable("id") Long id, @PathVariable("comment_id") Long commentId) {
		AgendaItem agendaItem = agendaPointService.findOne(id);

		if (agendaItem==null){
			return new ResponseEntity<Void>( HttpStatus.NOT_FOUND);
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
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
