package com.example.controller;

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

import com.example.dto.CommentDTO;
import com.example.model.Comment;
import com.example.model.Glitch;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.CommentService;
import com.example.service.GlitchService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value = "comments")
public class CommnetController {

	@Autowired
	CommentService commentService;

	@Autowired
	GlitchService glitchService;

	@Autowired
	UserService userService;

	@Autowired
	TokenUtils tokenUtils;

	@RequestMapping(value = "glitches/{id}/comments", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Create a new comment to the glitch.", notes = "Returns the comment being saved.", httpMethod = "POST", 
				produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = CommentDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasAnyRole('ROLE_COMPANY','ROLE_USER')")
	public ResponseEntity<CommentDTO> addComment(
			@ApiParam(value = "The ID of the glitch.", required = true) @PathVariable Long id, 
			@ApiParam(value = "The commentDTO object", required = true) @RequestBody CommentDTO commentDTO,
			HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User user = userService.findByUsername(username);

		Glitch glitch = glitchService.findOne(id);

		if (glitch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Comment comment = CommentDTO.getComment(commentDTO);
		comment.setGlitch(glitch);
		comment.setUser(user);

		comment = commentService.save(comment);

		return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.CREATED);

	}

}
