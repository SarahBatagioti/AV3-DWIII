package com.autobots.automanager.modelo.dto.cliente;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import com.autobots.automanager.modelo.dto.documento.DocumentoDTO;
import com.autobots.automanager.modelo.dto.endereco.EnderecoDTO;
import com.autobots.automanager.modelo.dto.telefone.TelefoneDTO;

@Data
public class ClienteCadastroDTO {
    @NotBlank(message = "nome deve ser informado")
    private String nome;
    @NotBlank(message = "nomeSocial deve ser informado")
    private String nomeSocial;
    @NotNull(message = "dataNascimento deve ser informada")
    private Date dataNascimento;
    @Valid
    private EnderecoDTO endereco;
    @Valid
    private List<DocumentoDTO> documentos;
    @Valid
    private List<TelefoneDTO> telefones;
}
