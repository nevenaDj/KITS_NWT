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

@RestController
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
	@PreAuthorize("hasRole('ROLE_COMPANY')")
	public ResponseEntity<CommentDTO> addComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO,
			HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User user = userService.findByUsername(username);

		Glitch glitch = glitchService.findOne(id);

		if (glitch == null) {
			return new ResponseEntity<CommentDTO>(HttpStatus.BAD_REQUEST);
		}

		Comment comment = CommentDTO.getComment(commentDTO);
		comment.setGlitch(glitch);
		comment.setUser(user);

		comment = commentService.save(comment);

		return new ResponseEntity<CommentDTO>(new CommentDTO(comment), HttpStatus.CREATED);

	}

}
