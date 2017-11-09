package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Option;
import com.example.repository.OptionRepository;

@Service
public class OptionService {
	@Autowired
	OptionRepository optionRepository;

	public Option save(Option option) {
		return optionRepository.save(option);
	}

}
