package com.example.fenixveiculos.controller.v1;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Index")
@Controller
public class IndexController {

	@GetMapping(value = { "/",
			"/login",
			"/admin",
			"/admin/brands",
			"/admin/users" })
	public String handleIndex(HttpServletResponse response) throws IOException {
		return "/index.html";
	}
}
