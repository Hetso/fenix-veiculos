package com.example.fenixveiculos.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.fenixveiculos.dto.UserRequestDTO;
import com.example.fenixveiculos.dto.UserResponseDTO;
import com.example.fenixveiculos.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Users")
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	@Operation(summary = "Create user")
	public ResponseEntity<UserResponseDTO> createUser(
			@RequestBody UserRequestDTO dto) {

		if (userService.existUser(dto.getUsername(), dto.getEmail())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"User alreay exist");
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(userService.createUser(dto));
	}

}
