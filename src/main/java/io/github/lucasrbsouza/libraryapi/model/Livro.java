package io.github.lucasrbsouza.libraryapi.model;

import io.github.lucasrbsouza.libraryapi.model.enums.GeneroEnum;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
@ToString(exclude = "autor")
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
private GeneroEnum genero;

    @Column(name = "preco", precision = 18,scale = 2)
    private BigDecimal preco;

    @JoinColumn(name = "id_autor")
    @ManyToOne(
            //cascade = CascadeType.ALL
            fetch = FetchType.LAZY //vai trazer só os dados do livro e não do autor
    )
    private Autor autor;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Livro() {
    }
}
