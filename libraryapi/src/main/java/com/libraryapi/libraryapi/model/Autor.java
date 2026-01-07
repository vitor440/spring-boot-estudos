package com.libraryapi.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor")
@Data
@ToString(exclude = "livros")
@EntityListeners(AuditingEntityListener.class)
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "nacionalidade", length = 30, nullable = false)
    private String nacionalidade;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    private List<Livro> livros;

    @Column(name = "data_cadastro")
    @CreatedDate
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;
}
