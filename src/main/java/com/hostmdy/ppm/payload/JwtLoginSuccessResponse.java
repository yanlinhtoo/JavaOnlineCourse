package com.hostmdy.ppm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtLoginSuccessResponse {
	
	private boolean success;
	private String token;

}
