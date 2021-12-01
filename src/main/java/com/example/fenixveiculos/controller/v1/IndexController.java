package com.example.fenixveiculos.controller.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Index")
@Controller
public class IndexController {

	@GetMapping(value = { "/", "/login" })
	public String handleIndex() {
		return "index.html";
	}

}
