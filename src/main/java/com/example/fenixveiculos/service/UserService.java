package com.example.fenixveiculos.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.example.fenixveiculos.dto.user.RecoveryPasswordDTO;
import com.example.fenixveiculos.dto.user.UserChangePasswordDTO;
import com.example.fenixveiculos.dto.user.UserDTO;
import com.example.fenixveiculos.dto.user.UserRequestDTO;
import com.example.fenixveiculos.dto.user.UserResponseDTO;
import com.example.fenixveiculos.model.UserForgotPasswordModel;
import com.example.fenixveiculos.model.UserModel;
import com.example.fenixveiculos.repository.UserForgotPasswordRepository;
import com.example.fenixveiculos.repository.UserRepository;
import com.example.fenixveiculos.utils.EncoderUtils;
import com.example.fenixveiculos.utils.MapperUtils;

import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final UserForgotPasswordRepository forgotPasswordRepository;

	@Transactional
	public UserResponseDTO createUser(@Valid UserRequestDTO dto) {
		dto.setPassword(EncoderUtils.encode(dto.getPassword()));

		UserModel userToSave = MapperUtils.convert(dto, UserModel.class);
		userToSave.setId(null);
		userToSave.setActive(true);

		return MapperUtils.convert(userRepository
				.save(userToSave), UserResponseDTO.class);
	}

	public boolean existUser(String username, String email) {
		return userRepository.findByUsernameOrEmail(username, email)
				.isPresent();
	}

	public UserResponseDTO findUserByLogin(String login) {
		return MapperUtils.convert(
				userRepository.findByUsernameOrEmail(login, login),
				UserResponseDTO.class);
	}

	@Transactional
	public UserResponseDTO updateUser(long userId, UserDTO dto) {
		UserModel user = MapperUtils.convert(dto, UserModel.class);
		user.setId(userId);

		return MapperUtils.convert(userRepository.save(user),
				UserResponseDTO.class);
	}

	@Transactional
	public boolean changePassword(long userId, UserChangePasswordDTO dto) {
		Optional<UserModel> user = userRepository.findById(userId);

		if (user.isPresent() && EncoderUtils.isEquals(dto.getCurrentPassword(),
				user.get().getPassword())) {
			return userRepository.updateUserPassword(userId,
					EncoderUtils.encode(dto.getNewPassword())) > 0;
		}

		return false;
	}

	public UserResponseDTO findUserById(long id) {
		return MapperUtils.convert(userRepository.findById(id),
				UserResponseDTO.class);
	}

	public List<UserResponseDTO> findAllUsers() {
		return MapperUtils.convert(userRepository.findAll(),
				UserResponseDTO.class);
	}

	@Transactional
	public boolean inactiveUser(long userId) {
		return userRepository.updateUserActiveStatus(userId, false) > 0;
	}

	@Transactional
	public boolean activeUser(long userId) {
		return userRepository.updateUserActiveStatus(userId, true) > 0;
	}

	@Transactional
	public void saveForgotPasswordToken(long userId, String token) {
		forgotPasswordRepository.save(
				new UserForgotPasswordModel(token, new UserModel(userId)));
	}

	@Transactional
	public UserResponseDTO recoveryPassword(@Valid RecoveryPasswordDTO dto) {
		Optional<UserForgotPasswordModel> data = forgotPasswordRepository
				.findById(dto.getToken());

		if (data.isPresent()) {
			userRepository.updateUserPassword(data.get().getUser().getId(),
					EncoderUtils.encode(dto.getPassword()));
			forgotPasswordRepository.deleteById(dto.getToken());

			return MapperUtils.convert(data.get().getUser(),
					UserResponseDTO.class);
		}

		return null;
	}

}
