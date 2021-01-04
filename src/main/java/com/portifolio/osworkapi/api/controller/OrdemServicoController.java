package com.portifolio.osworkapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.portifolio.osworkapi.api.dto.request.OrdemServicoRequestDto;
import com.portifolio.osworkapi.api.dto.response.OrdemServicoResponseDto;
import com.portifolio.osworkapi.domain.service.OrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private final OrdemServicoService ordemServicoService;
	
	public OrdemServicoController(OrdemServicoService ordemServicoService) {
		this.ordemServicoService = ordemServicoService;
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoResponseDto create(@Valid @RequestBody OrdemServicoRequestDto ordemServicoDto) {
		return ordemServicoService.create(ordemServicoDto);
	}
	
	@GetMapping
	public List<OrdemServicoResponseDto> listAll() {
		return ordemServicoService.findAll();
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoResponseDto> findById(@PathVariable Long ordemServicoId){
		return ordemServicoService.findById(ordemServicoId);
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		ordemServicoService.finalizar(ordemServicoId);
	}
	
	
}
