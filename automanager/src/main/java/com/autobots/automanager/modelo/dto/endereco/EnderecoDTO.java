package com.autobots.automanager.modelo.dto.endereco;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EnderecoDTO {
    private String estado;
    @NotBlank(message = "cidade deve ser informada")
    private String cidade;
    private String bairro;
    @NotBlank(message = "rua deve ser informada")
    private String rua;
    @NotBlank(message = "numero deve ser informado")
    private String numero;
    private String codigoPostal;
    private String informacoesAdicionais;
}
