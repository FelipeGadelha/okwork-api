package com.portifolio.osworkapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.portifolio.osworkapi.api.dto.request.ComentarioRequestDto;
import com.portifolio.osworkapi.api.dto.response.ComentarioResponseDto;
import com.portifolio.osworkapi.domain.service.OrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private final OrdemServicoService ordemServicoService;
	
	public ComentarioController(OrdemServicoService ordemServicoService) {
		this.ordemServicoService = ordemServicoService;
	}
	
	@GetMapping
	public List<ComentarioResponseDto> listAll(@PathVariable Long ordemServicoId){
		return ordemServicoService.findAllComentarios(ordemServicoId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioResponseDto add(@PathVariable Long ordemServicoId,
			@Valid @RequestBody ComentarioRequestDto comentarioRequestDto) {
		
		return ordemServicoService.adicionarComentario(ordemServicoId, 
				comentarioRequestDto.getDescricao());
	}
	

}
