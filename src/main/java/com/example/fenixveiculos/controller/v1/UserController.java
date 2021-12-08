package com.example.fenixveiculos.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.fenixveiculos.dto.user.ForgotPasswordDTO;
import com.example.fenixveiculos.dto.user.RecoveryPasswordDTO;
import com.example.fenixveiculos.dto.user.UserChangePasswordDTO;
import com.example.fenixveiculos.dto.user.UserDTO;
import com.example.fenixveiculos.dto.user.UserRequestDTO;
import com.example.fenixveiculos.dto.user.UserResponseDTO;
import com.example.fenixveiculos.model.UserModel;
import com.example.fenixveiculos.service.AuthenticationService;
import com.example.fenixveiculos.service.MailService;
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
	private final MailService mailService;

	@PostMapping
	@Operation(summary = "Create user")
	public ResponseEntity<UserResponseDTO> createUser(
			@Validated @RequestBody UserRequestDTO dto) {

		if (userService.existUser(dto.getUsername(), dto.getEmail())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"User alreay exist");
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(userService.createUser(dto));
	}

	@PutMapping
	@Operation(summary = "Update current user info")
	public ResponseEntity<UserResponseDTO> updateCurrentUser(
			@Validated @RequestBody UserDTO dto) {

		UserModel currentUser = AuthenticationService.getCurrentUser();

		if (currentUser != null) {

			return ResponseEntity.status(HttpStatus.OK)
					.body(userService.updateUser(currentUser.getId(), dto));
		}

		throw new ResponseStatusException(HttpStatus.FORBIDDEN);

	}

	@PutMapping(value = "/changePassword")
	@Operation(summary = "Change current user password")
	public ResponseEntity<UserResponseDTO> changeCurrentUserPassword(
			@Validated @RequestBody UserChangePasswordDTO dto) {

		UserModel currentUser = AuthenticationService.getCurrentUser();

		if (currentUser != null) {

			if (!userService.changePassword(currentUser.getId(), dto)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid current password");
			}

			return ResponseEntity.noContent().build();
		}

		throw new ResponseStatusException(HttpStatus.FORBIDDEN);

	}

	@PostMapping(value = "/forgotPassword")
	@Operation(summary = "Create a recovery password token and send email to user")
	public ResponseEntity<String> forgotPassword(
			@RequestBody @Validated ForgotPasswordDTO dto) {

		UserResponseDTO user = userService.findUserByLogin(dto.getLogin());

		if (user != null) {
			String token = UUID.randomUUID().toString() + "-" + user.getId();

			userService.saveForgotPasswordToken(user.getId(), token);

			mailService.sendRecoveryPassword(user.getEmail(), token);

			return ResponseEntity.noContent().build();
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Invalid user");
	}

	@PostMapping(value = "/recoveryPassword")
	@Operation(summary = "Recovery user password")
	public ResponseEntity<String> recoveryPassword(
			@RequestBody @Validated RecoveryPasswordDTO dto) {

		if (userService.recoveryPassword(dto) != null) {
			return ResponseEntity.noContent().build();
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Invalid token");
	}

	@GetMapping
	@Operation(summary = "Get all users")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		return ResponseEntity.ok(userService.findAllUsers());
	}

	@GetMapping(value = "/{id:[0-9]+}")
	@Operation(summary = "Get user by id")
	public ResponseEntity<UserResponseDTO> getUserById(
			@PathVariable("id") Long id) {

		UserResponseDTO user = userService.findUserById(id);
		if (user != null) {
			return ResponseEntity.ok(user);
		}

		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id:[0-9]+}")
	@Operation(summary = "Update a user by id")
	public ResponseEntity<UserResponseDTO> updateUser(
			@Validated @RequestBody UserDTO dto,
			@PathVariable("id") Long id) {

		if (userService.findUserById(id) != null) {

			return ResponseEntity.status(HttpStatus.OK)
					.body(userService.updateUser(id, dto));
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Invalid user");

	}

	@PutMapping(value = "/{id:[0-9]+}/active")
	@Operation(summary = "Enable a user by id")
	public ResponseEntity<String> activeUser(@PathVariable("id") Long id) {
		if (userService.findUserById(id) != null) {
			userService.activeUser(id);
			return ResponseEntity.noContent().build();
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Invalid user");
	}

	@PutMapping(value = "/{id:[0-9]+}/inactive")
	@Operation(summary = "Enable a user by id")
	public ResponseEntity<String> inactiveUser(@PathVariable("id") Long id) {
		if (userService.findUserById(id) != null) {
			userService.inactiveUser(id);
			return ResponseEntity.noContent().build();
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Invalid user");
	}

}
