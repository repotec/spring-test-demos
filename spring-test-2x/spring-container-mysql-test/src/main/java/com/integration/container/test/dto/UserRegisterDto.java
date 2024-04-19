package com.integration.container.test.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDto {
	@Size(min = 5)
	private String username;
	
	@Size(min = 5, max = 16)
	private String password;
	
	@Size(min = 5, max = 16)
	private String repassword;
	
	@Size(min = 5, max = 20)
	private String firstName;
	
	@Size(min = 5, max = 20)
	private String lastName;
	
	@Email
	private String email;
}
