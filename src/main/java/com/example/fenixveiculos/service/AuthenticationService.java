package com.example.fenixveiculos.service;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fenixveiculos.model.UserModel;
import com.example.fenixveiculos.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService implements UserDetailsService {

	private final UserRepository userRepository;

	public static UserModel getCurrentUser() {

		if (!isAuthenticated()) {
			return null;
		}

		return (UserModel) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Optional<UserModel> user = userRepository
				.findByUsernameOrEmail(username, username);

		if (user.isPresent()) {
			return user.get();
		}

		throw new UsernameNotFoundException("User not found");
	}

	public static boolean isAuthenticated() {
		return !(SecurityContextHolder.getContext()
				.getAuthentication() instanceof AnonymousAuthenticationToken);
	}

}
