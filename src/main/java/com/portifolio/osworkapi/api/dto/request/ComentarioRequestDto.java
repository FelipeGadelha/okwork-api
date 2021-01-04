package com.portifolio.osworkapi.api.dto.request;

import javax.validation.constraints.NotBlank;

public class ComentarioRequestDto {
	
	@NotBlank
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
