package com.example.fenixveiculos.utils;

import org.springframework.stereotype.Component;

@Component
public class RegexUtils {

	public final static String EMAIL = "^\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b$";
}
