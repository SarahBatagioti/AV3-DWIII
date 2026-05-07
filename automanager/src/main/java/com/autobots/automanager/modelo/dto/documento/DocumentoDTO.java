package com.autobots.automanager.modelo.dto.documento;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DocumentoDTO {
    private Long id;
    @NotBlank(message = "tipo deve ser informado")
    private String tipo;
    @NotBlank(message = "numero deve ser informado")
    private String numero;
}
