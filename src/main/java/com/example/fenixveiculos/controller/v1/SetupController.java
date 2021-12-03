package com.example.fenixveiculos.controller.v1;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.fenixveiculos.dto.setup.SetupRequestDTO;
import com.example.fenixveiculos.dto.setup.SetupResponseDTO;
import com.example.fenixveiculos.dto.user.UserRequestDTO;
import com.example.fenixveiculos.model.ConfigurationModel.ConfigurationKey;
import com.example.fenixveiculos.model.UserModel.UserGender;
import com.example.fenixveiculos.service.ConfigurationService;
import com.example.fenixveiculos.service.JwtTokenService;
import com.example.fenixveiculos.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Index")
@RestController
@RequestMapping(value = "/setup")
@RequiredArgsConstructor
public class SetupController {

	private final ConfigurationService configurationService;
	private final JwtTokenService tokenService;
	private final AuthenticationManager authManager;
	private final UserService userService;

	@PostMapping(value = "/complete")
	public ResponseEntity<SetupResponseDTO> completeSetup(
			@Valid @RequestBody SetupRequestDTO dto) {

		if (configurationService.isConfigured()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Setup is already completed");
		}

		createUserAdmin(dto);

		configurationService.insertConfiguration(
				ConfigurationKey.SETUP_COMPLETED, "TRUE");

		try {
			UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(
					dto.getUser().getUsername(), dto.getUser().getPassword());

			Authentication authentication = authManager
					.authenticate(userPassAuthToken);

			return ResponseEntity
					.ok(SetupResponseDTO.builder()
							.token(tokenService.generateToken(authentication))
							.build());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	private void createUserAdmin(SetupRequestDTO dto) {
		if (!userService.existUser(dto.getUser().getUsername(),
				dto.getUser().getEmail())) {
			userService.createUser(UserRequestDTO.builder()
					.email(dto.getUser().getEmail())
					.username(dto.getUser().getUsername())
					.password(dto.getUser().getPassword())
					.firstname("Admin")
					.lastname("Master")
					.gender(UserGender.UNDEFINED)
					.build());
		}
	}

}
