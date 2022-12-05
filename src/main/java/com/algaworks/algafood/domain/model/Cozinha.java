package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

//@JsonRootName("gastronomia") usado para alterar o nome no retorno do XML.
//@JsonIgnoreProperties("titulo") usado para esconder o atributo durante o retorno.
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
//	@JsonProperty("titulo") usado para alterar o nome do atributo durante o retorno
	@Column(nullable = false)
	private String nome;

}
