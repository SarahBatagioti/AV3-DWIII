package com.autobots.automanager.entidades;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Venda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date cadastro;
	@Column(nullable = false, unique = true)
	private String identificacao;
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Usuario cliente;
	@ManyToOne
	@JoinColumn(name = "funcionario_id", nullable = false)
	private Usuario funcionario;
	@ManyToOne
	@JoinColumn(name = "veiculo_id")
	private Veiculo veiculo;
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "venda_mercadoria", joinColumns = @JoinColumn(name = "venda_id"), inverseJoinColumns = @JoinColumn(name = "mercadoria_id"))
	private Set<Mercadoria> mercadorias = new LinkedHashSet<>();
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "venda_servico", joinColumns = @JoinColumn(name = "venda_id"), inverseJoinColumns = @JoinColumn(name = "servico_id"))
	private Set<Servico> servicos = new LinkedHashSet<>();
}
