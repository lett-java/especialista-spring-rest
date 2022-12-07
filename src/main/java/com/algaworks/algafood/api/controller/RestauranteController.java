package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.dto.RestauranteDTO;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		return ResponseEntity.ok(restauranteService.listar());
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id) {
		Restaurante restaurante = restauranteService.buscar(id);
		return (restaurante == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(restaurante);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody RestauranteDTO restauranteDTO) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.salvar(restauranteDTO));			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable("restauranteId") Long id, @RequestBody RestauranteDTO restauranteDTO) {
		try {
			return ResponseEntity.ok(restauranteService.atualizar(id, restauranteDTO));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long restauranteId) {
		try {
			restauranteService.excluir(restauranteId);	
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
}