package com.unifacisa.ouvidoria.entities;

import java.util.List;

import com.unifacisa.ouvidoria.enums.UserTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_users")
@Getter @Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "user_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserTypes userType;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@OneToMany(mappedBy = "author")
	private List<Feedback> feedbacks;

	public User() {}

	public User(Integer id, String name, UserTypes userType, String username, String password) {
		this.id = id;
		this.name = name;
		this.userType = userType;
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "@" + this.username;
	}
}
