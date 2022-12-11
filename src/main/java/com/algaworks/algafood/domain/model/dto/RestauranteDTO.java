package com.algaworks.algafood.domain.model.dto;

import java.math.BigDecimal;

import com.algaworks.algafood.domain.model.Cozinha;

import lombok.Data;

@Data
public class RestauranteDTO {

	private String nome;
	private BigDecimal taxaFrete;
	private Cozinha cozinha;
}
