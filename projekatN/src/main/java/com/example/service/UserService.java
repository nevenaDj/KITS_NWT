package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Authority;
import com.example.model.User;
import com.example.model.UserAuthority;
import com.example.repository.AddressRepository;
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
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public User save(User user, String role) {
		user.setAddress(addressRepository.save(user.getAddress()));
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

	public User findByUsernameAndAuthority(String username, String role) {
		return userRepository.findByUsernameAndAuthority(username, role);
	}

	public boolean changePassword(Long id, String currentPassword, String newPassword, String newPassword2) {
		User user = userRepository.findOne(id);

		if (user.getPassword().equals(passwordEncoder.encode(currentPassword))) {
			if (newPassword.equals(newPassword2)) {
				user.setPassword(passwordEncoder.encode(newPassword));
				return true;
			}
		}

		return false;
	}
}
