package com.unifacisa.ouvidoria.entities;

import com.unifacisa.ouvidoria.enums.FeedbackTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa os feedbacks da aplicação
 * 
 * @author Darllinson Azevedo
 */
@Entity
@Table(name = "tb_feedbacks")
@Getter @Setter
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private User author;

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private FeedbackTypes type;

	@Column(name = "description", nullable = false)
	private String description;
	
	public Feedback() {}

	public Feedback(Integer id, User author, FeedbackTypes type, String description) {
		this.id = id;
		this.author = author;
		this.type = type;
		this.description = description;
	}

	@Override
	public String toString() {
		return this.id + " | " +
				this.type.toString() + " | " +
				this.author + " | " +
				this.description;
	}
}
