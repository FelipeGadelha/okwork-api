package com.portifolio.osworkapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.portifolio.osworkapi.domain.model.Cliente;
import com.portifolio.osworkapi.domain.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
//	@PersistenceContext
//	private EntityManager manager;
	
	@Autowired
	private final ClienteService clienteService;
	
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@GetMapping()
	public List<Cliente> findAll(){
		return clienteService.findAll();
//		return manager.createQuery("from Cliente", Cliente.class)
//				.getResultList();
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> findById(@PathVariable Long clienteId) {
		return clienteService.findById(clienteId);
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@Valid @RequestBody Cliente cliente) {
		return clienteService.save(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> update(@Valid @PathVariable Long clienteId,
			@RequestBody Cliente cliente){
		return clienteService.update(clienteId, cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> deleteById(@PathVariable Long clienteId){
		return clienteService.deleteById(clienteId);
		
	}

}
