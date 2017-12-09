package com.example.dto;

import java.util.HashSet;
import java.util.Set;

import com.example.model.AgendaItem;
import com.example.model.ItemComment;
import com.example.model.ItemType;

public class AgendaItemDTO {
	private Long id;
	private String title;

	private String content;
	private MeetingDTO meeting;

	private String conclusion;
	private Set<ItemCommentDTO> comments = new HashSet<>();
	
	private int number;

	private NotificationDTO notification;
	private GlitchDTO glitch;
	private CommunalProblemDTO communalProblem;
	private ItemType type;

	public AgendaItemDTO() {

	}

	public AgendaItemDTO(AgendaItem agendaPoint) {
		this.id= agendaPoint.getId();
		this.number= agendaPoint.getNumber();
		this.type= agendaPoint.getType();
		this.title=agendaPoint.getTitle();
		if (agendaPoint.getComments().isEmpty()){
			for (ItemComment itemComment : agendaPoint.getComments()) {
				this.comments.add(new ItemCommentDTO(itemComment));
			}
		}
		if (agendaPoint.getCommunalProblem()!=null)
			this.communalProblem= new CommunalProblemDTO(agendaPoint.getCommunalProblem());
		if (agendaPoint.getConclusion()!=null)
			this.conclusion= agendaPoint.getConclusion();
		if (agendaPoint.getContent()!=null)
			this.content = agendaPoint.getContent();
		if (agendaPoint.getGlitch()!=null)
			this.glitch = new GlitchDTO(agendaPoint.getGlitch());
		if (agendaPoint.getMeeting()!=null)
			this.meeting = new MeetingDTO(agendaPoint.getMeeting());
		if (agendaPoint.getNotification()!=null)
			this.notification = new NotificationDTO(agendaPoint.getNotification());
		
	}

	public AgendaItemDTO(Long id, int number, ItemType type, String title) {
		super();
		this.id = id;
		this.number = number;
		this.type = type;
		this.title=title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MeetingDTO getMeeting() {
		return meeting;
	}

	public void setMeeting(MeetingDTO meeting) {
		this.meeting = meeting;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public Set<ItemCommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<ItemCommentDTO> comments) {
		this.comments = comments;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public NotificationDTO getNotification() {
		return notification;
	}

	public void setNotification(NotificationDTO notification) {
		this.notification = notification;
	}

	public GlitchDTO getGlitch() {
		return glitch;
	}

	public void setGlitch(GlitchDTO glitch) {
		this.glitch = glitch;
	}

	public CommunalProblemDTO getCommunalProblem() {
		return communalProblem;
	}

	public void setCommunalProblem(CommunalProblemDTO communalProblem) {
		this.communalProblem = communalProblem;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static AgendaItem getAgendaPoint(AgendaItemDTO agendaPointDTO) {
		AgendaItem item= new AgendaItem(agendaPointDTO.getId(), agendaPointDTO.getNumber(), agendaPointDTO.getType(), agendaPointDTO.getTitle());
		Set<ItemComment> comments= new HashSet<>();
		if (agendaPointDTO.getComments().isEmpty()){
			for (ItemCommentDTO comment : agendaPointDTO.getComments()) {
				comments.add(ItemCommentDTO.getComment(comment));
			}
		}
		item.setComments(comments);
		if (agendaPointDTO.getCommunalProblem()!=null)
			item.setCommunalProblem(CommunalProblemDTO.getCommunalProblem (agendaPointDTO.getCommunalProblem()));
		if (agendaPointDTO.getConclusion()!=null)
			item.setConclusion( agendaPointDTO.getConclusion());
		if (agendaPointDTO.getContent()!=null)
			item.setContent(agendaPointDTO.getContent());
		if (agendaPointDTO.getGlitch()!=null)
			item.setGlitch(GlitchDTO.getGlitch(agendaPointDTO.getGlitch()));
		if (agendaPointDTO.getMeeting()!=null)
			item.setMeeting(MeetingDTO.getMeeting(agendaPointDTO.getMeeting()));
		if (agendaPointDTO.getNotification()!=null)
			item.setNotification(NotificationDTO.getNotification(agendaPointDTO.getNotification()));
		return item;
	}

}
