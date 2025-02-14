package io.github.lucasrbsouza.libraryapi.service;

import io.github.lucasrbsouza.libraryapi.model.Autor;
import io.github.lucasrbsouza.libraryapi.model.Livro;
import io.github.lucasrbsouza.libraryapi.model.enums.GeneroEnum;
import io.github.lucasrbsouza.libraryapi.repository.AutorRepository;
import io.github.lucasrbsouza.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransacaoService {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void excutar() {
        Livro livro = new Livro();
        livro.setIsbn("977565454212");
        livro.setPreco(BigDecimal.valueOf(120.20));
        livro.setGenero(GeneroEnum.CIENCIA);
        livro.setTitulo("O mundo animal- o ser humano");
        livro.setDataPublicacao(LocalDate.of(2013, 7, 6));

        Autor autor = new Autor();
        autor.setNome("Lucas");
        autor.setNacionalidade("brasiliera");
        autor.setDataNascimento(LocalDate.of(1950, 2, 4));
        autorRepository.save(autor);
        livro.setAutor(autor);
        livroRepository.save(livro);
    }
}
