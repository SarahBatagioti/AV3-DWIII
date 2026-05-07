package com.autobots.automanager.servicos.cliente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.dto.cliente.ClienteCadastroDTO;
import com.autobots.automanager.modelo.dto.documento.DocumentoDTO;
import com.autobots.automanager.modelo.dto.endereco.EnderecoDTO;
import com.autobots.automanager.modelo.dto.telefone.TelefoneDTO;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class CadastradorClienteServico {

    @Autowired
    private ClienteRepositorio repositorio;

    private Endereco mapearEndereco(EnderecoDTO dto) {
        if (dto == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setEstado(dto.getEstado());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setCodigoPostal(dto.getCodigoPostal());
        endereco.setInformacoesAdicionais(dto.getInformacoesAdicionais());
        return endereco;
    }

    private List<Documento> mapearDocumentos(List<DocumentoDTO> dtos) {
        List<Documento> documentos = new ArrayList<>();
        if (dtos == null) {
            return documentos;
        }
        for (DocumentoDTO dto : dtos) {
            Documento documento = new Documento();
            documento.setTipo(dto.getTipo());
            documento.setNumero(dto.getNumero());
            documentos.add(documento);
        }
        return documentos;
    }

    private List<Telefone> mapearTelefones(List<TelefoneDTO> dtos) {
        List<Telefone> telefones = new ArrayList<>();
        if (dtos == null) {
            return telefones;
        }
        for (TelefoneDTO dto : dtos) {
            Telefone telefone = new Telefone();
            telefone.setDdd(dto.getDdd());
            telefone.setNumero(dto.getNumero());
            telefones.add(telefone);
        }
        return telefones;
    }

    public Cliente cadastrar(ClienteCadastroDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setNomeSocial(dto.getNomeSocial());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setDataCadastro(new Date());
        cliente.setEndereco(mapearEndereco(dto.getEndereco()));
        cliente.setDocumentos(mapearDocumentos(dto.getDocumentos()));
        cliente.setTelefones(mapearTelefones(dto.getTelefones()));
        return repositorio.saveAndFlush(cliente);
    }
}