package com.example.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.model.Glitch;
import com.example.model.GlitchState;
import com.example.model.Picture;

public class GlitchDTO {
	private Long id;
	private UserDTO responsiblePerson;
	private Long companyID;
	private String description;
	private Date dateOfReport;
	private ApartmentDTO apartment;
	private GlitchTypeDTO type;
	private GlitchState state;
	private Date dateOfRepair;
	private BillDTO bill;
	private boolean dateOfRepairApproved;
	private Set<PictureDTO> images;

	public GlitchDTO() {

	}

	public GlitchDTO(Glitch glitch) {
		this(glitch.getId(), glitch.getDescription(), glitch.getDateOfReport(), glitch.getState(),
				glitch.getDateOfRepair(), glitch.isDateOfRepairApproved());

		if (glitch.getResponsiblePerson() != null) {
			this.responsiblePerson = new UserDTO(glitch.getResponsiblePerson());
		}

		if (glitch.getApartment() != null) {
			this.apartment = new ApartmentDTO(glitch.getApartment());
		}

		if (glitch.getType() != null) {
			this.type = new GlitchTypeDTO(glitch.getType());
		}
		
		if (glitch.getBill() != null) {
			this.bill = new BillDTO(glitch.getBill());
		}
		if (glitch.getCompany() != null) {
			this.companyID= glitch.getCompany().getId();
		}
		
		if (glitch.getImages() != null) {
			this.images= new HashSet<PictureDTO>();
		}
		else{
			for (Picture picture : glitch.getImages()) {
				this.images.add(new PictureDTO(picture.getId(), picture.getImages(), new GlitchDTO(picture.getGlitch())));
			}
		}
		
	}

	public GlitchDTO(Long companyID, String description, GlitchTypeDTO type) {
		super();
		this.companyID = companyID;
		this.description = description;
		this.type = type;
	}

	public GlitchDTO(Long companyID, String description) {
		super();
		this.companyID = companyID;
		this.description = description;
	}

	public GlitchDTO(Long id, String description, Date dateOfReport, GlitchTypeDTO type, GlitchState state,
			Date dateOfRepair, boolean dateOfRepairapproved) {
		super();
		this.id = id;
		this.description = description;
		this.dateOfReport = dateOfReport;
		this.type = type;
		this.state = state;
		this.dateOfRepair = dateOfRepair;
		this.dateOfRepairApproved=dateOfRepairapproved;
	}

	public GlitchDTO(Long id, String description, Date dateOfReport, GlitchState state, Date dateOfRepair,boolean dateOfRepairapproved) {
		super();
		this.id = id;
		this.description = description;
		this.dateOfReport = dateOfReport;
		this.state = state;
		this.dateOfRepair = dateOfRepair;
		this.dateOfRepairApproved=dateOfRepairapproved;
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

	public GlitchTypeDTO getType() {
		return type;
	}

	public void setType(GlitchTypeDTO type) {
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

	public BillDTO getBill() {
		return bill;
	}

	public void setBill(BillDTO bill) {
		this.bill = bill;
	}

	
	
	public boolean isDateOfRepairApproved() {
		return dateOfRepairApproved;
	}

	public void setDateOfRepairApproved(boolean dateOfRepairApproved) {
		this.dateOfRepairApproved = dateOfRepairApproved;
	}
	
	

	public Set<PictureDTO> getImages() {
		return images;
	}

	public void setImages(Set<PictureDTO> images) {
		this.images = images;
	}

	public static Glitch getGlitch(GlitchDTO glitchDTO) {
		Glitch glitch = new Glitch(glitchDTO.getId(), glitchDTO.getDescription(), glitchDTO.getDateOfReport(),
				glitchDTO.getState(), glitchDTO.getDateOfRepair(), glitchDTO.isDateOfRepairApproved());

		if (glitchDTO.getResponsiblePerson() != null) {
			glitch.setResponsiblePerson(UserDTO.getUser(glitchDTO.getResponsiblePerson()));
		}

		if (glitchDTO.getApartment() != null) {
			glitch.setApartment(ApartmentDTO.getApartment(glitchDTO.getApartment()));
		}

		if (glitchDTO.getType() != null) {
			glitch.setType(GlitchTypeDTO.getGlitchType(glitchDTO.getType()));
		}

		if (glitchDTO.getBill() != null) {
			glitch.setBill(BillDTO.getBill(glitchDTO.getBill()));
		}
		if (glitchDTO.getImages() == null) {
			glitch.setImages( new HashSet<Picture>());
		}
		else{
			Set<Picture> pics= new HashSet<Picture>();
			
			for (PictureDTO picture : glitchDTO.getImages()) {
				pics.add(PictureDTO.getPicture(picture));
				}
			glitch.setImages(pics);
		}
		
		return glitch;
	}

}
