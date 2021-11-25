package com.example.fenixveiculos.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.fenixveiculos.model.UserModel;
import com.example.fenixveiculos.repository.UserRepository;
import com.example.fenixveiculos.service.JwtTokenService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	private final UserRepository userRepository;
	private final JwtTokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = getToken(request);

		if (tokenService.isValid(token)) {
			this.authenticate(token);
		}

		filterChain.doFilter(request, response);
	}

	private void authenticate(String token) {
		Optional<UserModel> user = userRepository
				.findById(tokenService.getTokenId(token));

		if (user.isPresent()) {

			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					user.get(), null, null);
			SecurityContextHolder.getContext()
					.setAuthentication(usernamePasswordAuthenticationToken);
		}
	}

	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}

		return token.split(" ")[1];
	}

}
