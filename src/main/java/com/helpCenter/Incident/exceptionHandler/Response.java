package com.helpCenter.Incident.exceptionHandler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Response {

	private String message;

	public Response(String message) {
		
		this.message=message;
	}

}
