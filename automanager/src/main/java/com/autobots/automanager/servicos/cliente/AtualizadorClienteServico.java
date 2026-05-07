package com.autobots.automanager.servicos.cliente;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.excecoes.RequisicaoInvalidaException;
import com.autobots.automanager.modelo.atualizadores.cliente.ClienteAtualizador;
import com.autobots.automanager.modelo.dto.cliente.ClienteAtualizacaoDTO;
import com.autobots.automanager.modelo.dto.documento.DocumentoDTO;
import com.autobots.automanager.modelo.dto.endereco.EnderecoDTO;
import com.autobots.automanager.modelo.dto.telefone.TelefoneDTO;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class AtualizadorClienteServico {

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

    private List<Documento> mapearDocumentosParaAtualizacao(List<DocumentoDTO> dtos) {
        List<Documento> documentos = new ArrayList<>();
        if (dtos == null) {
            return documentos;
        }
        for (DocumentoDTO dto : dtos) {
            Documento documento = new Documento();
            documento.setId(dto.getId());
            documento.setTipo(dto.getTipo());
            documento.setNumero(dto.getNumero());
            documentos.add(documento);
        }
        return documentos;
    }

    private List<Telefone> mapearTelefonesParaAtualizacao(List<TelefoneDTO> dtos) {
        List<Telefone> telefones = new ArrayList<>();
        if (dtos == null) {
            return telefones;
        }
        for (TelefoneDTO dto : dtos) {
            Telefone telefone = new Telefone();
            telefone.setId(dto.getId());
            telefone.setDdd(dto.getDdd());
            telefone.setNumero(dto.getNumero());
            telefones.add(telefone);
        }
        return telefones;
    }

    private void adicionarNovosDocumentos(Cliente cliente, List<DocumentoDTO> documentosDto) {
        if (documentosDto == null) {
            return;
        }
        for (DocumentoDTO dto : documentosDto) {
            if (dto.getId() == null) {
                Documento novo = new Documento();
                novo.setTipo(dto.getTipo());
                novo.setNumero(dto.getNumero());
                cliente.getDocumentos().add(novo);
            }
        }
    }

    private void adicionarNovosTelefones(Cliente cliente, List<TelefoneDTO> telefonesDto) {
        if (telefonesDto == null) {
            return;
        }
        for (TelefoneDTO dto : telefonesDto) {
            if (dto.getId() == null) {
                Telefone novo = new Telefone();
                novo.setDdd(dto.getDdd());
                novo.setNumero(dto.getNumero());
                cliente.getTelefones().add(novo);
            }
        }
    }

    public Cliente atualizar(ClienteAtualizacaoDTO dto) {
        if (dto.getId() == null) {
            throw new RequisicaoInvalidaException("Id obrigatorio para atualizacao");
        }

        Cliente cliente = repositorio.findById(dto.getId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

        Cliente entidadeAtualizacao = new Cliente();
        entidadeAtualizacao.setNome(dto.getNome());
        entidadeAtualizacao.setNomeSocial(dto.getNomeSocial());
        entidadeAtualizacao.setDataNascimento(dto.getDataNascimento());
        entidadeAtualizacao.setEndereco(mapearEndereco(dto.getEndereco()));
        entidadeAtualizacao.setDocumentos(mapearDocumentosParaAtualizacao(dto.getDocumentos()));
        entidadeAtualizacao.setTelefones(mapearTelefonesParaAtualizacao(dto.getTelefones()));

        if (cliente.getEndereco() == null && dto.getEndereco() != null) {
            cliente.setEndereco(mapearEndereco(dto.getEndereco()));
        }

        adicionarNovosDocumentos(cliente, dto.getDocumentos());
        adicionarNovosTelefones(cliente, dto.getTelefones());

        ClienteAtualizador atualizador = new ClienteAtualizador();
        atualizador.atualizar(cliente, entidadeAtualizacao);

        return repositorio.saveAndFlush(cliente);
    }
}
