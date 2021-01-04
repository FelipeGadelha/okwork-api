package com.portifolio.osworkapi.api.dto.request;

import javax.validation.constraints.NotNull;

public class ClienteIdRequestDto {
	
	@NotNull
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

}
