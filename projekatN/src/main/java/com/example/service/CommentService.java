package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Comment;
import com.example.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;

	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

}
