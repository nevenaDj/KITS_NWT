package com.example.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Glitch {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	private User tenant;

	@ManyToOne(fetch = FetchType.EAGER)
	private User responsiblePerson;

	@ManyToOne(fetch = FetchType.EAGER)
	private User company;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfReport;

	@ManyToOne(fetch = FetchType.EAGER)
	private Apartment apartment;

	@OneToOne(fetch = FetchType.EAGER)
	private GlitchType type;

	@OneToOne(fetch = FetchType.EAGER)
	private GlitchState state;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfRepair;
	
	private boolean dateOfRepairApproved;

	@OneToMany(mappedBy = "glitch", fetch = FetchType.LAZY)
	private Set<Comment> comments = new HashSet<Comment>();
	
	@OneToOne(fetch = FetchType.EAGER)
	private Bill bill;

	public Glitch() {

	}

	public Glitch(Long id, String description, Date dateOfReport, GlitchType type, GlitchState state,
			Date dateOfRepair, boolean dateOfRepairApproved) {
		super();
		this.id = id;
		this.description = description;
		this.dateOfReport = dateOfReport;
		this.type = type;
		this.state = state;
		this.dateOfRepair = dateOfRepair;
		this.dateOfRepairApproved=dateOfRepairApproved;
	}

	public Glitch(Long id, String description, Date dateOfReport, GlitchState state, Date dateOfRepair, boolean dateOfRepairApproved) {
		super();
		this.id = id;
		this.description = description;
		this.dateOfReport = dateOfReport;
		this.state = state;
		this.dateOfRepair = dateOfRepair;
		this.dateOfRepairApproved=dateOfRepairApproved;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getTenant() {
		return tenant;
	}

	public void setTenant(User tenant) {
		this.tenant = tenant;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public User getCompany() {
		return company;
	}

	public void setCompany(User company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
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

	public Date getDateOfReport() {
		return dateOfReport;
	}

	public void setDateOfReport(Date dateOfReport) {
		this.dateOfReport = dateOfReport;
	}

	public Date getDateOfRepair() {
		return dateOfRepair;
	}

	public void setDateOfRepair(Date dateOfRepair) {
		this.dateOfRepair = dateOfRepair;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public boolean isDateOfRepairApproved() {
		return dateOfRepairApproved;
	}

	public void setDateOfRepairApproved(boolean dateOfRepairApproved) {
		this.dateOfRepairApproved = dateOfRepairApproved;
	}
	
	

	
}
