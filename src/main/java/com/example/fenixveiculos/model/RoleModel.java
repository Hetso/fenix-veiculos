package com.example.fenixveiculos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel implements GrantedAuthority {

	private static final long serialVersionUID = -1235183527154202646L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role name;

	@Override
	public String getAuthority() {
		return name.toString();
	}

	public enum Role {
		ADMINISTRATOR
	}
}
