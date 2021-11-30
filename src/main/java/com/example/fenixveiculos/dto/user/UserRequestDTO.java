package com.example.fenixveiculos.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRequestDTO extends UserDTO {

	private String password;

}
