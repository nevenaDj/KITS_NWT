package com.example.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Picture {

	@Id
	@GeneratedValue
	private Long id;

	@Lob
	private byte[] images;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "glitch_id")
	private Glitch glitch;

	public Picture() {

	}

	public Picture(Long id, byte[] images, Glitch glitch) {
		super();
		this.id = id;
		this.images = images;
		this.glitch = glitch;
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

}
