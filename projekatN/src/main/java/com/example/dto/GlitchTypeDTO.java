package com.example.dto;

import com.example.model.GlitchType;

public class GlitchTypeDTO {
	private Long id;
	private String type;

	public GlitchTypeDTO() {

	}

	public GlitchTypeDTO(Long id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public GlitchTypeDTO(GlitchType glitchType) {
		this(glitchType.getId(), glitchType.getType());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static GlitchType getGlitchType(GlitchTypeDTO glitchTypeDTO) {
		return new GlitchType(glitchTypeDTO.getId(), glitchTypeDTO.getType());
	}

}
