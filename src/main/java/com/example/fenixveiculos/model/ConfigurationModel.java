package com.example.fenixveiculos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "configuration")
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationModel {

	@Id
	@Column(name = "key")
	@Enumerated(EnumType.STRING)
	private ConfigurationKey key;

	@Column(name = "value", nullable = false)
	private String value;

	public enum ConfigurationKey {
		SETUP_COMPLETED
	}
}
