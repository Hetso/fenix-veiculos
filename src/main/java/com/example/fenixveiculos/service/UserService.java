package com.example.fenixveiculos.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fenixveiculos.dto.user.UserRequestDTO;
import com.example.fenixveiculos.dto.user.UserResponseDTO;
import com.example.fenixveiculos.model.UserModel;
import com.example.fenixveiculos.repository.UserRepository;
import com.example.fenixveiculos.utils.EncoderUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	@Transactional
	public UserResponseDTO createUser(UserRequestDTO dto) {
		dto.setPassword(EncoderUtils.encode(dto.getPassword()));

		UserModel user = userRepository
				.save(modelMapper.map(dto, UserModel.class));

		return modelMapper.map(user, UserResponseDTO.class);
	}

	public boolean existUser(String username, String email) {
		return userRepository.findByUsernameOrEmail(username, email)
				.isPresent();
	}

}
