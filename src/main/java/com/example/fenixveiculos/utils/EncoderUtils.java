package com.example.fenixveiculos.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncoderUtils {

	private EncoderUtils() {
	}

	private static final PasswordEncoder encoder = new BCryptPasswordEncoder(
			10);

	public static String encode(String str) {
		return encoder.encode(str);
	}

	public static boolean isEquals(String rawStr, String encodedStr) {
		return encoder.matches(rawStr, encodedStr);
	}

	public static PasswordEncoder getInstance() {
		return encoder;
	}
}
