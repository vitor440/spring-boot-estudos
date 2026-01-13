package com.libraryapi.libraryapi.controller;

import com.libraryapi.libraryapi.controller.dto.CadastroLivroDTO;
import com.libraryapi.libraryapi.controller.dto.ErroResposta;
import com.libraryapi.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.libraryapi.libraryapi.controller.mappers.LivroMapper;
import com.libraryapi.libraryapi.exceptions.RegistroDuplicadoException;
import com.libraryapi.libraryapi.model.GeneroLivro;
import com.libraryapi.libraryapi.model.Livro;
import com.libraryapi.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

    private final LivroService livroService;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO livroDTO) {

            Livro livro = mapper.toEntity(livroDTO);
            livroService.salvar(livro);
            URI location = createURI(livro.getId());
            return ResponseEntity.created(location).build();

    }

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "nome-autor", required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false)GeneroLivro genero,
            @RequestParam(value = "num-pagina", defaultValue = "0") Integer numPagina,
            @RequestParam(value = "tam-pagina", defaultValue = "10") Integer tamPagina
            ){

        Page<Livro> resultado = livroService.pesquisa(isbn, titulo, ano, nomeAutor, genero, numPagina, tamPagina);

        Page<ResultadoPesquisaLivroDTO> resultadoDTO = resultado.map(mapper::toDTO);
        return ResponseEntity.ok(resultadoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);

        Optional<Livro> livroOptional = livroService.obterDetalhes(uuid);

        if(livroOptional.isPresent()) {
            Livro livro = livroOptional.get();

            ResultadoPesquisaLivroDTO dto = mapper.toDTO(livro);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);

        Optional<Livro> livroOptional = livroService.obterDetalhes(uuid);

        if(livroOptional.isPresent()) {
            livroService.deletar(livroOptional.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id,
                                          @RequestBody CadastroLivroDTO dto){

        return livroService.obterDetalhes(UUID.fromString(id))
                .map(livro ->  {
                    Livro entidadeAux = mapper.toEntity(dto);

                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setGeneroLivro(entidadeAux.getGeneroLivro());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setAutor(entidadeAux.getAutor());

                    livroService.atualizar(livro);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
