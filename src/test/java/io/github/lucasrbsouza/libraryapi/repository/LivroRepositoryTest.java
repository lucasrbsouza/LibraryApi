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
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    private LivroRepository repository;
    @Autowired
    private AutorRepository autorrepository;

    @Test
    public void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("87768763");
        livro.setPreco(BigDecimal.valueOf(120.20));
        livro.setGenero(GeneroEnum.BIOGRAFIA);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(2013, 7, 6));

        Autor autor = autorrepository.findById(UUID.fromString("60d81f17-4d0e-416b-843b-d3ae392de4d9")).orElse(null);

        repository.save(livro);
    }

    @Test
    public void salvarLivroEAutorTest() {
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
        autorrepository.save(autor);
        livro.setAutor(autor);
        repository.save(livro);
    }

    @Test
    public void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("87768763");
        livro.setPreco(BigDecimal.valueOf(120.20));
        livro.setGenero(GeneroEnum.BIOGRAFIA);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(2013, 7, 6));

        Autor autor = new Autor();
        autor.setNome("Lucas");
        autor.setNacionalidade("brasiliera");
        autor.setDataNascimento(LocalDate.of(1950, 2, 4));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarLivro() {
        var livroAtualizar = repository.findById(UUID.fromString("94a4df34-3af2-4eb8-81ea-3ae3da9c448a")).orElse(null);

        Autor autor = autorrepository.findById(UUID.fromString("60d81f17-4d0e-416b-843b-d3ae392de4d9")).orElse(null);

        livroAtualizar.setAutor(autor);
        livroAtualizar.setTitulo("Caminhos");

        repository.save(livroAtualizar);
    }

    @Test
    void deletar() {
        var livroAtualizar = repository.findById(UUID.fromString("94a4df34-3af2-4eb8-81ea-3ae3da9c448a")).orElse(null);

        repository.delete(livroAtualizar);
    }

    @Test
    @Transactional
        //como o fetch é lazy precisamos dessa anotacion para trazer os dados do autor
    void buscarLivroTest() {
        UUID id = UUID.fromString("d694b692-a737-435d-af78-68f937e36da1");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Titulo: " + livro.getTitulo());

        //aqui a anotacion @Transactional vai funcionar
        System.out.println("Autor: " + livro.getAutor().getNome());

    }

    @Test
    void pesquisarPorTituloTest() {
        List<Livro> list = repository.findByTitulo("Vida em The");
        list.forEach(System.out::println);
    }

    @Test
    void pesquisarPorIsbnoTest() {
        List<Livro> list = repository.findByIsbn("OHGWE9273");
        list.forEach(System.out::println);
    }

    @Test
    void pesquisarPorLikeTest(){
        List<Livro> list = repository.findByTituloContainsIgnoreCase("pot");
        list.forEach(System.out::println);
        if (list.isEmpty()){
            System.out.println("nenhum livro encontrado");
        }
    }
    @Test
    void ListarLivrosComQueryJPQL(){
        List<Livro> list = repository.listarTodosOrdendoPorTituloEPreco();
        list.forEach(System.out::println);
    }

    @Test
    void ListarLivrosAutoresDosLivros() {
        List<Autor> list = repository.listarAutoresDosLivros();
        list.forEach(System.out::println);
    }

    @Test
    void ListarGenroAutoresBr() {
        List<String> list = repository.listarGenerosAutoresBr();
        list.forEach(System.out::println);
    }

    @Test
    void ListarGenroQueryParam() {
        List<?> list = repository.encontrarPorGenero(GeneroEnum.FANTASIA, "preco");
        list.forEach(System.out::println);
    }

   @Test
    void deleteTestByGenero(){
        repository.deleteByGenero(GeneroEnum.CIENCIA);
   }

    @Test
    void updateTestByDataPublicacao() {
        repository.updateDataPublicacao(LocalDate.of(2002, 2, 24), UUID.fromString("d694b692-a737-435d-af78-68f937e36da1"));
    }

}
