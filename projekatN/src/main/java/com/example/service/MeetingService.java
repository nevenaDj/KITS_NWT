package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Meeting;
import com.example.repository.MeetingRepository;

@Service
public class MeetingService {
	@Autowired
	MeetingRepository meetingRepsoitory;

	public Meeting save(Meeting meeting) {
		return meetingRepsoitory.save(meeting);
	}

	public Meeting findOne(Long id) {
		return meetingRepsoitory.findOne(id);
	}

}
