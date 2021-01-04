package com.portifolio.osworkapi.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.portifolio.osworkapi.api.dto.request.OrdemServicoRequestDto;
import com.portifolio.osworkapi.api.dto.response.ComentarioResponseDto;
import com.portifolio.osworkapi.api.dto.response.OrdemServicoResponseDto;
import com.portifolio.osworkapi.domain.exception.EntityNotFoundException;
import com.portifolio.osworkapi.domain.exception.NegocioException;
import com.portifolio.osworkapi.domain.model.Cliente;
import com.portifolio.osworkapi.domain.model.Comentario;
import com.portifolio.osworkapi.domain.model.OrdemServico;
import com.portifolio.osworkapi.domain.model.StatusOrdemServico;
import com.portifolio.osworkapi.domain.repository.ClienteRepository;
import com.portifolio.osworkapi.domain.repository.ComentarioRepository;
import com.portifolio.osworkapi.domain.repository.OrdemServicoRepository;

@Service
public class OrdemServicoService {

	@Autowired
	private final OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ModelMapper modelmapper;

	@Autowired
	private final ClienteRepository clienteRepository;

	@Autowired
	private final ComentarioRepository comentarioRepository;

	public OrdemServicoService(OrdemServicoRepository ordemServicoRepository, ClienteRepository clienteRepository,
			ComentarioRepository comentarioRepository) {

		this.ordemServicoRepository = ordemServicoRepository;
		this.clienteRepository = clienteRepository;
		this.comentarioRepository = comentarioRepository;
	}

	public OrdemServicoResponseDto create(OrdemServicoRequestDto ordemServicoRequestDto) {
		OrdemServico ordemServico = ordemServicotoEntity(ordemServicoRequestDto);

		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));

		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());

		return ordemServicoToResponseDto(ordemServicoRepository.save(ordemServico));
	}

	public List<OrdemServicoResponseDto> findAll() {
		return ordemServicoToListResponseDto(ordemServicoRepository.findAll());
	}

	public ResponseEntity<OrdemServicoResponseDto> findById(Long ordemServicoId) {
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
		if (ordemServico.isPresent()) {
			OrdemServicoResponseDto ordemServicoDto = ordemServicoToResponseDto(ordemServico.get());
			return ResponseEntity.ok(ordemServicoDto);
		}
		return ResponseEntity.notFound().build();

	}
	
	public void finalizar(Long ordemServicoId) {
		
		OrdemServico ordemServico = searchOrdemServico(ordemServicoId);
		ordemServico.finalizar();
		ordemServicoRepository.save(ordemServico);
	}

	public List<ComentarioResponseDto> findAllComentarios(Long ordemServicoId) {
		OrdemServico ordemServico = searchOrdemServico(ordemServicoId);
		
 		return comentarioToListResponseDto(ordemServico.getComentarios());
	}


	public ComentarioResponseDto adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada"));

		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		return comentarioToResponseDto(comentarioRepository.save(comentario));
	}

	private OrdemServico searchOrdemServico(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada."));
	}
	
	private OrdemServicoResponseDto ordemServicoToResponseDto(OrdemServico ordemServico) {
		return modelmapper.map(ordemServico, OrdemServicoResponseDto.class);
	}

	private List<OrdemServicoResponseDto> ordemServicoToListResponseDto(List<OrdemServico> ordemServicos) {

		return ordemServicos.stream().map(o -> ordemServicoToResponseDto(o)).collect(Collectors.toList());
	}

	private OrdemServico ordemServicotoEntity(OrdemServicoRequestDto ordemServicoDto) {
		return modelmapper.map(ordemServicoDto, OrdemServico.class);
	}

	private ComentarioResponseDto comentarioToResponseDto(Comentario comentario) {
		return modelmapper.map(comentario, ComentarioResponseDto.class);
	}
	
	private List<ComentarioResponseDto> comentarioToListResponseDto(List<Comentario> comentarios) {
		return comentarios.stream()
				.map(c -> comentarioToResponseDto(c))
				.collect(Collectors.toList());
	}

}
