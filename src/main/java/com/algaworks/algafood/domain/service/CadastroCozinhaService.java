package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.salvar(cozinha);
	}

	public CozinhasXmlWrapper listarXml() {
		return new CozinhasXmlWrapper(cozinhaRepository.listar());
	}

	public Cozinha buscar(Long id) {
		return cozinhaRepository.buscar(id);
	}
	
	public List<Cozinha> listar() {
		return cozinhaRepository.listar();
	}

	public Cozinha atualizar(Long id, CozinhaDTO cozinhaDTO) {
		Cozinha entityToUpdate = buscar(id);
		BeanUtils.copyProperties(cozinhaDTO, entityToUpdate);

		return cozinhaRepository.salvar(entityToUpdate);
	}

	public ResponseEntity<Cozinha> remover(Long id) {
		Cozinha entityToRemove = buscar(id);
		if (entityToRemove == null) {
			return ResponseEntity.notFound().build();
		} else {
			cozinhaRepository.remover(entityToRemove);
			return ResponseEntity.noContent().build();
		}
		
	}
}
