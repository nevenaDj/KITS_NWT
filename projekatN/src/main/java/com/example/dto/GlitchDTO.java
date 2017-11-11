package com.example.dto;

import java.util.Date;

import com.example.model.Glitch;
import com.example.model.GlitchState;
import com.example.model.GlitchType;

public class GlitchDTO {
	private Long id;
	private UserDTO responsiblePerson;
	private UserDTO company;
	private String description;
	private Date dateOfReport;
	private ApartmentDTO apartment;
	private GlitchType type;
	private GlitchState state;
	private Date dateOfRepair;

	public GlitchDTO() {

	}

	public GlitchDTO(Glitch glitch) {
		this(glitch.getId(), glitch.getDescription(), glitch.getDateOfReport(), glitch.getType(), glitch.getState(),
				glitch.getDateOfRepair());
		if (glitch.getCompany() != null) {
			this.company = new UserDTO(glitch.getCompany());
		}

		if (glitch.getResponsiblePerson() != null) {
			this.responsiblePerson = new UserDTO(glitch.getResponsiblePerson());
		}

		if (glitch.getApartment() != null) {
			this.apartment = new ApartmentDTO(glitch.getApartment());
		}
	}

	public GlitchDTO(Long id, String description, Date dateOfReport, GlitchType type, GlitchState state,
			Date dateOfRepair) {
		super();
		this.id = id;
		this.description = description;
		this.dateOfReport = dateOfReport;
		this.type = type;
		this.state = state;
		this.dateOfRepair = dateOfRepair;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(UserDTO responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public UserDTO getCompany() {
		return company;
	}

	public void setCompany(UserDTO company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateOfReport() {
		return dateOfReport;
	}

	public void setDateOfReport(Date dateOfReport) {
		this.dateOfReport = dateOfReport;
	}

	public ApartmentDTO getApartment() {
		return apartment;
	}

	public void setApartment(ApartmentDTO apartment) {
		this.apartment = apartment;
	}

	public GlitchType getType() {
		return type;
	}

	public void setType(GlitchType type) {
		this.type = type;
	}

	public GlitchState getState() {
		return state;
	}

	public void setState(GlitchState state) {
		this.state = state;
	}

	public Date getDateOfRepair() {
		return dateOfRepair;
	}

	public void setDateOfRepair(Date dateOfRepair) {
		this.dateOfRepair = dateOfRepair;
	}

	public static Glitch getGlitch(GlitchDTO glitchDTO) {
		Glitch glitch = new Glitch(glitchDTO.getId(), glitchDTO.getDescription(), glitchDTO.getDateOfReport(),
				glitchDTO.getType(), glitchDTO.getState(), glitchDTO.getDateOfRepair());

		if (glitchDTO.getCompany() != null) {
			glitch.setCompany(UserDTO.getUser(glitchDTO.getCompany()));
		}

		if (glitchDTO.getResponsiblePerson() != null) {
			glitch.setResponsiblePerson(UserDTO.getUser(glitchDTO.getResponsiblePerson()));
		}

		if (glitchDTO.getApartment() != null) {
			glitch.setApartment(ApartmentDTO.getApartment(glitchDTO.getApartment()));
		}

		return glitch;
	}

}
