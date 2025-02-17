package io.github.lucasrbsouza.libraryapi.repository.spec;

import io.github.lucasrbsouza.libraryapi.model.Livro;
import io.github.lucasrbsouza.libraryapi.model.enums.GeneroEnum;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn));
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder
                .like(criteriaBuilder
                        .upper(root
                                .get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroEnum genero) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genero"), genero));
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer ano) {
        return ((root, query, criteriaBuilder)
                -> criteriaBuilder
                .equal(criteriaBuilder.function(
                        "to_char", String.class,
                        root.get("dataPublicacao"),
                        criteriaBuilder.literal("YYYY")), ano.toString()));
    }

    public static Specification<Livro> autorLike(String autor){
        return (root, query, criteriaBuilder) ->{
            Join<Object, Object> joinAutor = root.join("autor", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.upper(joinAutor.get("nome")), "%" + autor.toUpperCase() + "%");
        };
    }
}
