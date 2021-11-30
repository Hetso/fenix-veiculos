package com.example.fenixveiculos.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponseDTO extends UserDTO {

	private Long id;
}
