package com.example.fenixveiculos.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.fenixveiculos.exception.FileStorageException;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class FileService {

	@Value("${file.local.upload-dir}")
	private String localUploadDir;

	private Path fileStorageLocation;

	private final String LOCAL_IMAGE_PATH = "/img/";

	public List<String> uploadImages(MultipartFile[] files) {
		return uploadImages(files, null);
	}

	public List<String> uploadImages(MultipartFile[] files, String pathSufix) {
		if (isImagesValid(files)) {
			return uploadLocalFiles(LOCAL_IMAGE_PATH + pathRefactor(pathSufix),
					files);
		}

		throw new FileStorageException(
				"Invalid type files, should be all images");
	}

	public String uploadImage(MultipartFile file) {
		return uploadImage(file, null);
	}

	public String uploadImage(MultipartFile file, String pathSufix) {
		if (isImageValid(file)) {
			return uploadLocalFile(LOCAL_IMAGE_PATH + pathRefactor(pathSufix),
					file);
		}

		throw new FileStorageException(
				"Invalid type file, should be image");
	}

	public Resource getImageAsResource(String fileName) {
		return getImageAsResource(fileName, null);
	}

	public Resource getImageAsResource(String fileName, String pathSufix) {
		return loadLocalFileAsResource(
				LOCAL_IMAGE_PATH + pathRefactor(pathSufix), fileName);
	}

	private List<String> uploadLocalFiles(String path,
			MultipartFile[] files) {
		return Arrays.asList(files)
				.stream()
				.map(file -> uploadLocalFile(path, file))
				.collect(Collectors.toList());
	}

	private String uploadLocalFile(String path,
			MultipartFile file) {
		innitializeLocalPath(path);

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// verify filename
			if (fileName.contains("..")) {
				throw new FileStorageException(
						"Filename contains invalid path sequence "
								+ fileName);
			}

			fileName = generateFileName(fileName);

			Path targetLocation = this.fileStorageLocation
					.resolve(fileName);

			Files.copy(file.getInputStream(), targetLocation,
					StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException(
					"Could not store file " + fileName,
					ex);
		}
	}

	private Resource loadLocalFileAsResource(String path,
			String filename) {
		innitializeLocalPath(path);

		try {
			Path filePath = this.fileStorageLocation
					.resolve(filename)
					.normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				return resource;
			}
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	private String generateFileName(String originalFilename) {
		return System.currentTimeMillis() + "_" + originalFilename;
	}

	private void innitializeLocalPath(String path) {

		this.fileStorageLocation = Paths
				.get(this.localUploadDir + pathRefactor(path))
				.toAbsolutePath().normalize();

		try {
			Files.createDirectory(fileStorageLocation);
		} catch (Exception e) {
		}
	}

	private String pathRefactor(String path) {
		if (path == null) {
			return "";
		}

		// remove last "/"
		if (!path.endsWith("/")) {
			path = path.concat("/");
		}

		if (!path.startsWith("/")) {
			path = "/".concat(path);
		}

		return path;
	}

	public boolean isImagesValid(MultipartFile[] files) {
		return Arrays.asList(files).stream()
				.allMatch(file -> isImageValid(file));
	}

	public boolean isImageValid(MultipartFile file) {
		return file != null && file.getContentType().startsWith("image/");
	}

}
