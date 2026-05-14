package com.oficina.dto;

import com.oficina.model.StatusOrdem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServicoRequestDTO {

    private String cliente;
    private String veiculo;
    private String problema;
    private StatusOrdem status;
    private BigDecimal valor;
}
