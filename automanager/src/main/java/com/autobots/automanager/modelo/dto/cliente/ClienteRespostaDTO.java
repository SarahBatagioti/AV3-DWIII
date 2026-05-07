package com.autobots.automanager.modelo.dto.cliente;

import java.util.Date;
import java.util.List;
import lombok.Data;
import com.autobots.automanager.modelo.dto.documento.DocumentoDTO;
import com.autobots.automanager.modelo.dto.endereco.EnderecoDTO;
import com.autobots.automanager.modelo.dto.telefone.TelefoneDTO;

@Data
public class ClienteRespostaDTO {
    private Long id;
    private String nome;
    private String nomeSocial;
    private Date dataNascimento;
    private Date dataCadastro;
    private EnderecoDTO endereco;
    private List<DocumentoDTO> documentos;
    private List<TelefoneDTO> telefones;
}
