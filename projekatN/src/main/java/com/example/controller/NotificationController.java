package com.example.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.example.dto.NotificationDTO;
import com.example.model.Building;
import com.example.model.Notification;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.BuildingService;
import com.example.service.NotificationService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value = "notifications")
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
	@ApiOperation(value = "Create a notification in a building.", notes = "Returns the notification being saved.", httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = NotificationDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<NotificationDTO> addNotification(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id,
			@ApiParam(value = "The notificationDTO object", required = true) @RequestBody NotificationDTO notificationDTO,
			HttpServletRequest request) {

		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Notification notification = NotificationDTO.getNotification(notificationDTO);

		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User writer = userService.findByUsername(username);
		notification.setWriter(writer);
		notification.setDate(new Date());

		notification.setBuilding(building);
		notification = notificationService.save(notification);
		notificationDTO.setId(notification.getId());

		return new ResponseEntity<>(notificationDTO, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/buildings/{id}/notifications", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get a list of notifications in a building.", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported."),
			@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = NotificationDTO.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<List<NotificationDTO>> getNotifications(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id, Pageable page) {

		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Page<Notification> notifications = notificationService.findAllByBuilding(page, id);

		List<NotificationDTO> notificationsDTO = new ArrayList<>();
		for (Notification notification : notifications) {
			notificationsDTO.add(new NotificationDTO(notification));
		}
		return new ResponseEntity<>(notificationsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/notifications/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a notification.", httpMethod = "DELETE")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 404, message = "Not found") })
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<Void> deleteNotificaton(
			@ApiParam(value = "The ID of the notification.", required = true) @PathVariable("id") Long notificationId) {

		Notification notification = notificationService.findOne(notificationId);
		if (notification == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			notificationService.remove(notificationId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/notifications/{id}", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get a notification.", httpMethod = "GET")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = NotificationDTO.class),
			@ApiResponse(code = 404, message = "Not found") })
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<NotificationDTO> getNotificaton(
			@ApiParam(value = "The ID of the notification.", required = true) @PathVariable("id") Long notificationId) {

		Notification notification = notificationService.findOne(notificationId);
		if (notification == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			NotificationDTO notificationDTO = new NotificationDTO(notification);
			return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/notifications", method = RequestMethod.GET, produces = "application/json") // user's
																											// own
																											// notifications
	@ApiOperation(value = "Get a list of notifications for user.", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported."),
			@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token") })
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER', 'ROLE_PRESIDENT')")
	public ResponseEntity<List<NotificationDTO>> getNotifications(Pageable page, HttpServletRequest request) {

		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User writer = userService.findByUsername(username);

		Page<Notification> notifications = notificationService.findAllByWriter(page, writer.getId());

		List<NotificationDTO> notificationsDTO = new ArrayList<>();
		for (Notification notification : notifications) {
			notificationsDTO.add(new NotificationDTO(notification));
		}
		return new ResponseEntity<>(notificationsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/buildings/{id}/notifications/count", method = RequestMethod.GET)
	@ApiOperation(value = "Get a count of notifications.", httpMethod = "GET")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponse(code = 200, message = "Success")
	/*** get a count of notifications ***/
	public ResponseEntity<Long> getCountOfBuilding(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id) {
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Long count = notificationService.getCountOfNotifications(id);
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
}
