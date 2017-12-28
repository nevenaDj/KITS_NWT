package com.example.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.model.Bill;
import com.example.model.Glitch;
import com.example.model.ItemInPrincelist;
import com.example.model.Picture;

public class PictureDTO {

	private Long id;

	private byte[] images;
	private GlitchDTO glitchDTO;

	public PictureDTO() {
	}

	public PictureDTO(Picture pic) {
		this(pic.getId(), pic.getImages(), new GlitchDTO( pic.getGlitch()));
	}

	public PictureDTO(Long id, byte[] images, GlitchDTO glitchDTO) {
		super();
		this.id = id;
		this.images=images;
		this.glitchDTO=glitchDTO;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getImages() {
		return images;
	}

	public void setImages(byte[] images) {
		this.images = images;
	}

	public GlitchDTO getGlitchDTO() {
		return glitchDTO;
	}

	public void setGlitchDTO(GlitchDTO glitchDTO) {
		this.glitchDTO = glitchDTO;
	}

	public static Picture getPicture(PictureDTO picDTO) {
		return new Picture(picDTO.getId(), picDTO.getImages(), GlitchDTO.getGlitch(picDTO.getGlitchDTO()));
	}

}
