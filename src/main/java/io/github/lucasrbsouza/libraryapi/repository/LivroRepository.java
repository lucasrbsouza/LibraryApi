package io.github.lucasrbsouza.libraryapi.repository;

import io.github.lucasrbsouza.libraryapi.model.Autor;
import io.github.lucasrbsouza.libraryapi.model.Livro;
import io.github.lucasrbsouza.libraryapi.model.enums.GeneroEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    //QUERY METHOD
    boolean existsByAutor(Autor autor);
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);
    Optional<Livro> findByIsbn(String titulo);
    List<Livro> findByTituloContainsIgnoreCase(String titulo);

    //consultas JPQL e @QUERRY
    // JPQL referencias a entidades e propriedades
    @Query(" select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdendoPorTituloEPreco();

    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query("""
            select l.genero 
            from Livro  as l 
            join l.autor a 
            where a.nacionalidade = 'brasiliera' 
            order by l.genero
            """)
    List<String> listarGenerosAutoresBr();

    @Query("""
            select l from Livro as l where l.genero = :generoEnum order by :ordenacao
            """)//named param
    List<Livro> encontrarPorGenero(@Param("generoEnum") GeneroEnum generoEnum, @Param("ordenacao") String ordenacao);

    @Modifying // é obrigado colocar quqndo tiver fazendo uma operacao de escrita
    @Transactional // é obrigado colocar quqndo tiver fazendo uma operacao de escrita
    @Query("""
            delete from Livro where genero = ?1
            """)
    void deleteByGenero(GeneroEnum generoEnum);

    @Modifying // é obrigado colocar quqndo tiver fazendo uma operacao de escrita
    @Transactional // é obrigado colocar quqndo tiver fazendo uma operacao de escrita, tudo só irá salvar no banco de dados se der tudo certo
    @Query("""
            update Livro set dataPublicacao = ?1 where id = ?2
            """)
    void updateDataPublicacao(LocalDate date, UUID id);


}
