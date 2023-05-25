package com.helpCenter.Incident.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.helpCenter.comment.entity.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ImageCreation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

//	@Lob
	@Column( length = 32 )
	private byte[] image;

	@JsonBackReference(value="image")
	@ManyToOne
	private Incident incident;

	@JsonBackReference(value = "images")
	@ManyToOne
	private Comment comment;

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public ImageCreation(int id, byte[] image) {
		super();
		this.id = id;
		this.image = image;
	}

	public ImageCreation() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "image [id=" + id + ", image=" + Arrays.toString(image) + ", incident=" + incident + "]";
	}

}
