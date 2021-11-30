package com.example.fenixveiculos.dto.user;

import javax.validation.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class UserRequestDTO extends UserDTO {

	@Setter
	@NonFinal
	@NotBlank(message = "Password is required")
	private String password;

}
