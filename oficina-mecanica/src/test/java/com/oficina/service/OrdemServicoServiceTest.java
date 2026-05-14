package com.oficina.service;

import com.oficina.dto.OrdemServicoRequestDTO;
import com.oficina.dto.OrdemServicoResponseDTO;
import com.oficina.exception.ResourceNotFoundException;
import com.oficina.model.OrdemServico;
import com.oficina.model.StatusOrdem;
import com.oficina.repository.OrdemServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemServicoServiceTest {

    @Mock
    private OrdemServicoRepository repository;

    @InjectMocks
    private OrdemServicoService service;

    private OrdemServico ordemExemplo;
    private OrdemServicoRequestDTO requestExemplo;

    @BeforeEach
    void setUp() {
        ordemExemplo = OrdemServico.builder()
                .id(1L)
                .cliente("João Silva")
                .veiculo("Honda Civic 2020")
                .problema("Troca de óleo")
                .status(StatusOrdem.ABERTO)
                .valor(new BigDecimal("150.00"))
                .build();

        requestExemplo = OrdemServicoRequestDTO.builder()
                .cliente("João Silva")
                .veiculo("Honda Civic 2020")
                .problema("Troca de óleo")
                .status(StatusOrdem.ABERTO)
                .valor(new BigDecimal("150.00"))
                .build();
    }

    @Test
    void deveCriarOrdemComSucesso() {
        when(repository.save(any(OrdemServico.class))).thenReturn(ordemExemplo);

        OrdemServicoResponseDTO resultado = service.criar(requestExemplo);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getCliente());
        assertEquals(StatusOrdem.ABERTO, resultado.getStatus());
        verify(repository, times(1)).save(any(OrdemServico.class));
    }

    @Test
    void deveBuscarOrdemPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(ordemExemplo));

        OrdemServicoResponseDTO resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Honda Civic 2020", resultado.getVeiculo());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.buscarPorId(99L));

        verify(repository, times(1)).findById(99L);
    }

    @Test
    void deveListarTodasAsOrdens() {
        OrdemServico outraOrdem = OrdemServico.builder()
                .id(2L)
                .cliente("Maria Souza")
                .veiculo("Fiat Uno 2018")
                .problema("Freios")
                .status(StatusOrdem.EM_ANDAMENTO)
                .valor(new BigDecimal("300.00"))
                .build();

        when(repository.findAll()).thenReturn(List.of(ordemExemplo, outraOrdem));

        List<OrdemServicoResponseDTO> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveAtualizarOrdemComSucesso() {
        OrdemServico ordemAtualizada = OrdemServico.builder()
                .id(1L)
                .cliente("João Silva")
                .veiculo("Honda Civic 2020")
                .problema("Troca de óleo e filtro")
                .status(StatusOrdem.EM_ANDAMENTO)
                .valor(new BigDecimal("250.00"))
                .build();

        OrdemServicoRequestDTO requestAtualizado = OrdemServicoRequestDTO.builder()
                .cliente("João Silva")
                .veiculo("Honda Civic 2020")
                .problema("Troca de óleo e filtro")
                .status(StatusOrdem.EM_ANDAMENTO)
                .valor(new BigDecimal("250.00"))
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(ordemExemplo));
        when(repository.save(any(OrdemServico.class))).thenReturn(ordemAtualizada);

        OrdemServicoResponseDTO resultado = service.atualizar(1L, requestAtualizado);

        assertNotNull(resultado);
        assertEquals(StatusOrdem.EM_ANDAMENTO, resultado.getStatus());
        assertEquals(new BigDecimal("250.00"), resultado.getValor());
        verify(repository, times(1)).save(any(OrdemServico.class));
    }

    @Test
    void deveDeletarOrdemComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(ordemExemplo));
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.deletar(1L));

        verify(repository, times(1)).deleteById(1L);
    }
}
