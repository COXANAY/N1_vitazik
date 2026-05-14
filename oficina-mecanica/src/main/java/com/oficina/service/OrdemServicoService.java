package com.oficina.service;

import com.oficina.dto.OrdemServicoRequestDTO;
import com.oficina.dto.OrdemServicoResponseDTO;
import com.oficina.exception.ResourceNotFoundException;
import com.oficina.model.OrdemServico;
import com.oficina.model.StatusOrdem;
import com.oficina.repository.OrdemServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository repository;

    public OrdemServicoService(OrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponseDTO criar(OrdemServicoRequestDTO dto) {
        OrdemServico ordem = toEntity(dto);
        OrdemServico salvo = repository.save(ordem);
        return toDTO(salvo);
    }

    public List<OrdemServicoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public OrdemServicoResponseDTO buscarPorId(Long id) {
        OrdemServico ordem = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ordem de serviço não encontrada com id: " + id));
        return toDTO(ordem);
    }

    public OrdemServicoResponseDTO atualizar(Long id, OrdemServicoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ordem de serviço não encontrada com id: " + id));

        OrdemServico ordem = toEntity(dto);
        ordem.setId(id);
        OrdemServico atualizado = repository.save(ordem);
        return toDTO(atualizado);
    }

    public void deletar(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ordem de serviço não encontrada com id: " + id));
        repository.deleteById(id);
    }

    public List<OrdemServicoResponseDTO> buscarPorStatus(StatusOrdem status) {
        return repository.findByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --------------------------------
    // Métodos auxiliares de conversão
    // --------------------------------

    private OrdemServico toEntity(OrdemServicoRequestDTO dto) {
        return OrdemServico.builder()
                .cliente(dto.getCliente())
                .veiculo(dto.getVeiculo())
                .problema(dto.getProblema())
                .status(dto.getStatus())
                .valor(dto.getValor())
                .build();
    }

    private OrdemServicoResponseDTO toDTO(OrdemServico ordem) {
        return OrdemServicoResponseDTO.builder()
                .id(ordem.getId())
                .cliente(ordem.getCliente())
                .veiculo(ordem.getVeiculo())
                .problema(ordem.getProblema())
                .status(ordem.getStatus())
                .valor(ordem.getValor())
                .build();
    }
}
