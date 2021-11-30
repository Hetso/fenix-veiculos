package com.example.fenixveiculos.controller.v1;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fenixveiculos.dto.auth.AuthenticationRequestDTO;
import com.example.fenixveiculos.dto.auth.AuthenticationResponseDTO;
import com.example.fenixveiculos.dto.user.UserResponseDTO;
import com.example.fenixveiculos.model.UserModel;
import com.example.fenixveiculos.service.AuthenticationService;
import com.example.fenixveiculos.service.JwtTokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Authentication")
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationManager authManager;
	private final JwtTokenService tokenService;
	private final AuthenticationService authService;
	private final ModelMapper modelMapper;

	@GetMapping("/currentUser")
	@Operation(summary = "Get current user authenticated")
	public ResponseEntity<UserResponseDTO> getCurrentUser() {
		UserModel currentUser = authService.getCurrentUser();

		if (currentUser != null) {
			return ResponseEntity
					.ok(modelMapper.map(currentUser, UserResponseDTO.class));
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@PostMapping("/login")
	@Operation(summary = "Login with username/email and password")
	public ResponseEntity<AuthenticationResponseDTO> login(
			@RequestBody AuthenticationRequestDTO dto) {

		UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(
				dto.getLogin(), dto.getPassword());

		try {
			Authentication authentication = authManager
					.authenticate(userPassAuthToken);

			String token = tokenService.generateToken(authentication);

			return ResponseEntity
					.ok(new AuthenticationResponseDTO(token));
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}

}
