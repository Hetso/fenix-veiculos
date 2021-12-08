package com.example.fenixveiculos.controller.v1;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.fenixveiculos.service.ConfigurationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Index")
@Controller
@RequiredArgsConstructor
public class IndexController {

	private final ConfigurationService configurationService;

	private final String INDEX_LOCATION = "/index.html";
	private final String SETUP_INDEX_LOCATION = "/index.html";

	@GetMapping(value = { "/",
			"/auth/login",
			"/auth/forgotPassword",
			"/auth/recoveryPassword/{token}",
			"/admin",
			"/admin/brands",
			"/admin/users",
			"car/{id:[0-9]+}" })
	public String handleIndex(HttpServletResponse response) throws IOException {
		if (!configurationService.isConfigured()) {
			response.sendRedirect("/setup");
			return SETUP_INDEX_LOCATION;
		}

		return "/index.html";
	}

	@GetMapping(value = "/login")
	public String handleLoginRedirect(HttpServletResponse response)
			throws IOException {
		response.sendRedirect("/auth/login");

		return "/index.html";
	}

	@GetMapping(value = { "/setup" })
	public String handleSetupIndex(HttpServletResponse response)
			throws IOException {
		if (configurationService.isConfigured()) {
			response.sendRedirect("/");
			return INDEX_LOCATION;
		}

		return "/setup/index.html";
	}

}
