package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Authority;
import com.example.model.User;
import com.example.model.UserAuthority;
import com.example.repository.AuthorityRepository;
import com.example.repository.UserAuthorityRepository;
import com.example.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserAuthorityRepository userAuthorityRepository;
	@Autowired
	AuthorityRepository authorityRepository;

	public User save(User user, String role) {
		user = userRepository.save(user);
		Authority authority = authorityRepository.findByName(role);

		UserAuthority userAuthority = new UserAuthority(user, authority);
		userAuthority = userAuthorityRepository.save(userAuthority);

		return user;
	}

	public User update(User user) {
		return userRepository.save(user);
	}

	public Page<User> find(Pageable page, String role) {
		return userRepository.find(role, page);
	}

	public Page<User> findAll(Pageable page) {
		return userRepository.findAll(page);
	}

	public User findOne(Long id) {
		return userRepository.findOne(id);
	}

	public void remove(Long id, String role) {
		Authority authority = authorityRepository.findByName(role);
		Long userAuthorityID = userAuthorityRepository.findByUserAndAuthority(id, authority.getId());
		userAuthorityRepository.delete(userAuthorityID);
		userRepository.delete(id);

	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
