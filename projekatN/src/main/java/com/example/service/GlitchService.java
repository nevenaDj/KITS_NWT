package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Glitch;
import com.example.model.GlitchType;
import com.example.model.User;
import com.example.repository.GlitchRepository;
import com.example.repository.GlitchTypeRepository;
import com.example.repository.UserRepository;

@Service
public class GlitchService {

	@Autowired
	GlitchRepository glitchRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	GlitchTypeRepository glitchTypeRepository;

	public Glitch save(Glitch glitch) {
		return glitchRepository.save(glitch);
	}

	public Page<Glitch> findGlitches(Pageable page, User user) {
		String authority = userRepository.getUserAuthority(user.getId());
		if (authority.equals("ROLE_USER") || authority.equals("ROLE_OWNER")) {
			return glitchRepository.findGlitchesOfTenant(user.getId(), page);
		} else {
			return glitchRepository.findGlitchesOfCompany(user.getId(), page);
		}
	}

	public Glitch findOne(Long id) {
		return glitchRepository.findOne(id);
	}

	public GlitchType saveGlitchType(GlitchType glitchType) {
		return glitchTypeRepository.save(glitchType);
	}
}
