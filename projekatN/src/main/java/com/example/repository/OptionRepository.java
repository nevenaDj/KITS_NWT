package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

}
