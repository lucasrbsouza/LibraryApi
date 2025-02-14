package io.github.lucasrbsouza.libraryapi.repository;

import io.github.lucasrbsouza.libraryapi.model.Autor;
import io.github.lucasrbsouza.libraryapi.model.Livro;
import io.github.lucasrbsouza.libraryapi.model.enums.GeneroEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {
    @Autowired
    private AutorRepository repository;
    @Autowired
    LivroRepository livroRepository;


    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Clarice");
        autor.setNacionalidade("brasiliera");
        autor.setDataNascimento(LocalDate.of(1950, 2, 4));
        repository.save(autor);
        System.out.println("Salvando Autor(a): " + autor.toString());
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("32ff2f0d-7160-46dc-b363-31d7cae30f2a");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor: " + "\n" + autorEncontrado);

            autorEncontrado.setNome("Lucas Test");
            repository.save(autorEncontrado);
        }
    }

    @Test
    @Transactional
    public void listarTest(){
        List<Autor> lista = repository.findAll();

        lista.forEach(System.out::println);
    }

    public void countTest(){
        System.out.println("contagem dos autores: " + repository.count());
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("32ff2f0d-7160-46dc-b363-31d7cae30f2a");

        Optional<Autor> autorOptional = repository.findById(id);

        if (autorOptional.isPresent()){
            Autor autorEncontrado = autorOptional.get();
            System.out.println("Deletando autor da base de dados: " + autorEncontrado.toString());

            repository.delete(autorEncontrado);
        }else {
            System.out.println("Nenhum autor com Id " + id + " encontrado");
        }
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("J.K Rowling");
        autor.setNacionalidade("Inglês");
        autor.setDataNascimento(LocalDate.of(1950, 2, 4));

        Livro livro = new Livro();
        livro.setIsbn("OHGWE9273");
        livro.setPreco(BigDecimal.valueOf(150.0));
        livro.setGenero(GeneroEnum.FANTASIA);
        livro.setTitulo("Harry Potter e ordem da fenix");
        livro.setDataPublicacao(LocalDate.of(2010, 7, 6));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("OHJH9273");
        livro2.setPreco(BigDecimal.valueOf(150.0));
        livro2.setGenero(GeneroEnum.FANTASIA);
        livro2.setTitulo("Harry Potter e a camara secreta");
        livro2.setDataPublicacao(LocalDate.of(2006, 7, 6));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());

    }
    @Test
    void listarLivroAutor(){
        UUID id = UUID.fromString("609901af-a231-4fd1-bb45-aee79c7a8950");
        Autor autor = repository.findById(id).orElse(null);

        List<Livro> listaLivro = livroRepository.findByAutor(autor);
        autor.setLivros(listaLivro);
        autor.getLivros().forEach(System.out::println);
    }
}

