package com.example.fenixveiculos.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponseDTO extends UserDTO {

	private Long id;
}