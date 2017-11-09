package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
