package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Answer;
import com.example.model.ItemComment;
import com.example.repository.AnswerRepository;
import com.example.repository.ItemCommentRepository;

@Service
public class ItemCommentService {

	@Autowired
	ItemCommentRepository commentRepository;
	
	public ItemComment save(ItemComment comment) {
		return commentRepository.save(comment);
	}
	
	public ItemComment findOne(Long id) {
		return commentRepository.findOne(id);
	}
	
	public Page<ItemComment> findAll(Long id, Pageable p) {
		return commentRepository.findAll(p);
	}
	
	public void delete(Long id) {
		commentRepository.delete(id);
	}
	
}
