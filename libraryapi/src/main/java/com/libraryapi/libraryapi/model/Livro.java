package com.libraryapi.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
@ToString(exclude = "autor")
@EntityListeners(AuditingEntityListener.class)
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "isbn", length = 80, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro generoLivro;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_autor")
    private  Autor autor;

    @Column(name = "data_cadastro")
    @CreatedDate
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;
}
