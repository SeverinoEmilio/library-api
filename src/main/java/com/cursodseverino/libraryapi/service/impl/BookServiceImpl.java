package com.cursodseverino.libraryapi.service.impl;

import com.cursodseverino.libraryapi.exception.BusinessException;
import com.cursodseverino.libraryapi.model.entity.Book;
import com.cursodseverino.libraryapi.model.repository.BookRepository;
import com.cursodseverino.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    /**
     * @param book
     * @return
     */
    @Override
    public Book save(Book book) {
        if(bookRepository.existsByIsbn(book.getIsbn())){
            throw new BusinessException("Isbn já cadastro.");
        }

        return bookRepository.save(book);
    }
}
