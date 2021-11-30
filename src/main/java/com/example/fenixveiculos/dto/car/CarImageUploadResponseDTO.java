package com.example.fenixveiculos.dto.car;

import java.util.List;

import com.example.fenixveiculos.dto.file.FileUploadResponseDTO;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
public class CarImageUploadResponseDTO {

	private final List<FileUploadResponseDTO> images;
	private final String coverImage;

}
