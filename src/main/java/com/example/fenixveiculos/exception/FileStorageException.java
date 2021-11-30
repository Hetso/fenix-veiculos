package com.example.fenixveiculos.exception;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = -7764891829995375783L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
