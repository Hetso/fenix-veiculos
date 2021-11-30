package com.example.fenixveiculos.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.example.fenixveiculos.dto.user.UserRequestDTO;
import com.example.fenixveiculos.dto.user.UserResponseDTO;
import com.example.fenixveiculos.model.UserModel;
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

	@Transactional
	public UserResponseDTO createUser(@Valid UserRequestDTO dto) {
		dto.setPassword(EncoderUtils.encode(dto.getPassword()));

		UserModel user = userRepository
				.save(MapperUtils.convert(dto, UserModel.class));

		return MapperUtils.convert(user, UserResponseDTO.class);
	}

	public boolean existUser(String username, String email) {
		return userRepository.findByUsernameOrEmail(username, email)
				.isPresent();
	}

}
