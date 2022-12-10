package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	@Query("FROM Restaurante WHERE nome LIKE %:nome% AND cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") long cozinhaId);

	Optional<Restaurante> findFirstRestauranteByNomeContainingIgnoreCase(String nome);
	List<Restaurante> findTop2ByNomeContainingIgnoreCase(String nome);
	Boolean existsByNomeIgnoreCase(String nome);
	Integer countByCozinhaId(Long CozinhaId);
	
}
