package com.oficina.controller;

import com.oficina.dto.OrdemServicoRequestDTO;
import com.oficina.dto.OrdemServicoResponseDTO;
import com.oficina.model.StatusOrdem;
import com.oficina.service.OrdemServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordens")
public class OrdemServicoController {

    private final OrdemServicoService service;

    public OrdemServicoController(OrdemServicoService service) {
        this.service = service;
    }

    // POST /ordens — Criar nova ordem de serviço
    @PostMapping
    public ResponseEntity<OrdemServicoResponseDTO> criar(@RequestBody OrdemServicoRequestDTO dto) {
        OrdemServicoResponseDTO resposta = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    // GET /ordens — Listar todas as ordens
    @GetMapping
    public ResponseEntity<List<OrdemServicoResponseDTO>> listarTodos() {
        List<OrdemServicoResponseDTO> ordens = service.listarTodos();
        return ResponseEntity.ok(ordens);
    }

    // GET /ordens/{id} — Buscar ordem por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> buscarPorId(@PathVariable Long id) {
        OrdemServicoResponseDTO ordem = service.buscarPorId(id);
        return ResponseEntity.ok(ordem);
    }

    // PUT /ordens/{id} — Atualizar ordem existente
    @PutMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody OrdemServicoRequestDTO dto) {
        OrdemServicoResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE /ordens/{id} — Deletar ordem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // GET /ordens/status/{status} — Buscar ordens por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdemServicoResponseDTO>> buscarPorStatus(
            @PathVariable StatusOrdem status) {
        List<OrdemServicoResponseDTO> ordens = service.buscarPorStatus(status);
        return ResponseEntity.ok(ordens);
    }
}
