package com.autobots.automanager.entidades;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.autobots.automanager.enumeracoes.PerfilUsuario;

import lombok.Data;

@Data
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column
	private String nomeSocial;
	@Column
	private Date dataNascimento;
	@Column(nullable = false)
	private Date dataCadastro;
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "usuario_perfis", joinColumns = @JoinColumn(name = "usuario_id"))
	@Column(name = "perfil", nullable = false)
	private Set<PerfilUsuario> perfis = new LinkedHashSet<>();
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Endereco endereco;
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Telefone> telefones = new LinkedHashSet<>();
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Documento> documentos = new LinkedHashSet<>();
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Email> emails = new LinkedHashSet<>();
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Credencial> credenciais = new LinkedHashSet<>();
	@OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Veiculo> veiculos = new LinkedHashSet<>();
	@OneToMany(mappedBy = "fornecedor", fetch = FetchType.EAGER)
	private Set<Mercadoria> mercadorias = new LinkedHashSet<>();
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;
}
