package com.portifolio.osworkapi.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.portifolio.osworkapi.domain.exception.NegocioException;
import com.portifolio.osworkapi.domain.model.Cliente;
import com.portifolio.osworkapi.domain.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public ResponseEntity<Cliente> findById(Long clienteId) {

		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
	}

	public Cliente save(Cliente cliente) {
		Cliente clienteExistente = findByEmail(cliente.getEmail());
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
		}
		return clienteRepository.save(cliente);
	}

	public ResponseEntity<Void> deleteById(Long clienteId) {
		if(!clienteExists(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		clienteRepository.deleteById(clienteId);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<Cliente> update(Long clienteId, Cliente cliente) {
		if(!clienteExists(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteId);
		cliente = save(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	private boolean clienteExists(Long clienteId) {
		return clienteRepository.existsById(clienteId);
	}
	private Cliente findByEmail(String email) {
		return clienteRepository.findByEmail(email);
	}

}
