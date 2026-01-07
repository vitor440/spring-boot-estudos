package com.libraryapi.libraryapi.controller;

import com.libraryapi.libraryapi.controller.dto.AutorDTO;
import com.libraryapi.libraryapi.controller.dto.ErroResposta;
import com.libraryapi.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.libraryapi.libraryapi.exceptions.RegistroDuplicadoException;
import com.libraryapi.libraryapi.model.Autor;
import com.libraryapi.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {


    private final AutorService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autor) {
        try {
            Autor autorEntidade = autor.mapearAutor();

            service.salvar(autorEntidade);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable("id") String id) {
        var uuid = UUID.fromString(id);

        Optional<Autor> autorOptional = service.buscarPorId(uuid);

        if(autorOptional.isPresent()) {
            Autor autor = autorOptional.get();

            AutorDTO dto = new AutorDTO(autor.getId(), autor.getNome(), autor.getNacionalidade(), autor.getDataNascimento());
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        try {
            var uuid = UUID.fromString(id);

            Optional<Autor> autorOptional = service.buscarPorId(uuid);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
        }catch (OperacaoNaoPermitidaException e) {
            var erro = ErroResposta.erroPadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> buscaFiltrada(@RequestParam(value = "nome", required = false) String nome,
                                                        @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        // List<Autor> resultado = service.buscaFiltrada(nome, nacionalidade);
           List<Autor> resultado = service.pesquisaExample(nome, nacionalidade);

        List<AutorDTO> lista = resultado.stream().map(autor -> new AutorDTO(autor.getId(),
                                                           autor.getNome(),
                                                           autor.getNacionalidade(),
                                                           autor.getDataNascimento()))
                                                          .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
        var uuid = UUID.fromString(id);

        Optional<Autor> autorOptional = service.buscarPorId(uuid);

        if(autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();

        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}
