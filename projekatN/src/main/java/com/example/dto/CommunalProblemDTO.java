package com.example.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.model.Apartment;
import com.example.model.CommunalProblem;

public class CommunalProblemDTO {
	private Long id;
	private String description;
	private GlitchTypeDTO type;
	private Date dateOfRepair;
	private Long companyID;
	private Set<ApartmentDTO> apartments = new HashSet<>();

	public CommunalProblemDTO() {

	}

	public CommunalProblemDTO(CommunalProblem communalProblem) {
		this(communalProblem.getId(), communalProblem.getDescription(), communalProblem.getDateOfRepair());

		if (communalProblem.getType() != null) {
			this.type = new GlitchTypeDTO(communalProblem.getType());
		}
		if (communalProblem.getCompany()!=null)
			this.companyID=communalProblem.getCompany().getId();
		
		for (Apartment a: communalProblem.getApartments())
			apartments.add(new ApartmentDTO(a));
	}

	public CommunalProblemDTO(Long id, String description, Date dateOfRepair) {
		super();
		this.id = id;
		this.description = description;
		this.dateOfRepair = dateOfRepair;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GlitchTypeDTO getType() {
		return type;
	}

	public void setType(GlitchTypeDTO type) {
		this.type = type;
	}

	public Long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateOfRepair() {
		return dateOfRepair;
	}

	public void setDateOfRepair(Date dateOfRepair) {
		this.dateOfRepair = dateOfRepair;
	}

	public Set<ApartmentDTO> getApartments() {
		return apartments;
	}

	public void setApartments(Set<ApartmentDTO> apartments) {
		this.apartments = apartments;
	}

	public static CommunalProblem getCommunalProblem(CommunalProblemDTO communalProblemDTO) {
		CommunalProblem communalProblem = new CommunalProblem(communalProblemDTO.getId(),
				communalProblemDTO.getDescription(), communalProblemDTO.getDateOfRepair());

		if (communalProblemDTO.getType() != null) {
			communalProblem.setType(GlitchTypeDTO.getGlitchType(communalProblemDTO.getType()));
		}
		List<Apartment> apartment = new ArrayList<Apartment>();
		for (ApartmentDTO a: communalProblemDTO.getApartments())
			apartment.add(ApartmentDTO.getApartment(a));
		return communalProblem;
	}

}
