package com.cursodseverino.libraryapi.service;

import com.cursodseverino.libraryapi.exception.BusinessException;
import com.cursodseverino.libraryapi.model.entity.Book;
import com.cursodseverino.libraryapi.model.repository.BookRepository;
import com.cursodseverino.libraryapi.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {
    BookService bookService;
    @MockBean
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp(){
        this.bookService = new BookServiceImpl(bookRepository);

    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest(){
        //Cenario
        Book book = createValidBook();
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(false);

        Mockito.when(bookRepository.save(book)).thenReturn(
                                            Book.builder()
                                                    .id(1l)
                                                    .author("Fulano")
                                                    .title("As aventuras")
                                                    .isbn("123")
                                                    .build()
                                    );

        //Execução
        Book saveBook = bookService.save(book);

        //Verificação
        assertThat(saveBook.getId()).isNotNull();
        assertThat(saveBook.getAuthor()).isEqualTo("Fulano");
        assertThat(saveBook.getTitle()).isEqualTo("As aventuras");
        assertThat(saveBook.getIsbn()).isEqualTo("123");

    }



    @Test
    @DisplayName("Deve lançar erro de negocio ao tentar salvar um livro com isbn duplicado")
    public void shouldNotSaveABookWithDuplicateISBN(){
        //Cenario
        Book book = createValidBook();
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(true);
        //execução
        Throwable exception = Assertions.catchThrowable(() -> bookService.save(book));

        //Verificação
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Isbn já cadastro.");

        Mockito.verify(bookRepository, Mockito.never()).save(book);
    }

    private Book createValidBook() {
        return Book.builder().author("Fulano").title("As aventuras").isbn("123").build();
    }
}
