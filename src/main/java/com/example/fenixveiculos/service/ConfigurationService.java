package com.example.fenixveiculos.service;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.example.fenixveiculos.model.ConfigurationModel;
import com.example.fenixveiculos.model.ConfigurationModel.ConfigurationKey;
import com.example.fenixveiculos.repository.ConfigurationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Validated
public class ConfigurationService {
	private final ConfigurationRepository configuratioRepository;

	public boolean isConfigured() {
		Optional<ConfigurationModel> config = configuratioRepository
				.findById(ConfigurationKey.SETUP_COMPLETED);

		return config.isPresent()
				&& config.get().getValue() != null
				&& config.get().getValue().equalsIgnoreCase("TRUE");
	}

	@Transactional
	public void insertConfiguration(@NotNull ConfigurationKey key,
			@NotBlank String value) {

		configuratioRepository.insert(key.toString(), value);
	}

}
