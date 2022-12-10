package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> findByNomeContainingIgnoreCaseAndCozinhaId(String nome, Long cozinhaId);
	Optional<Restaurante> findFirstRestauranteByNomeContainingIgnoreCase(String nome);
	List<Restaurante> findTop2ByNomeContainingIgnoreCase(String nome);
	Boolean existsByNomeIgnoreCase(String nome);
	Integer countByCozinhaId(Long CozinhaId);
	
}
