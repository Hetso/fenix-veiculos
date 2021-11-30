package com.example.fenixveiculos.dto.file;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@SuperBuilder
@NoArgsConstructor(force = true)
public class FileUploadResponseDTO {

	private final String fileName;

}
