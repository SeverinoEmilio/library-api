package com.cursodseverino.libraryapi.api.resource;

import com.cursodseverino.libraryapi.api.dto.BookDTO;
import com.cursodseverino.libraryapi.api.exception.ApiErrors;
import com.cursodseverino.libraryapi.exception.BusinessException;
import com.cursodseverino.libraryapi.model.entity.Book;
import com.cursodseverino.libraryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;
    private ModelMapper modelMapper;

    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO dto){
        Book entity = modelMapper.map(dto, Book.class);

        entity = bookService.save(entity);
       return modelMapper.map(entity, BookDTO.class);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExceptions(MethodArgumentNotValidException ex ){
        BindingResult bindingResult = ex.getBindingResult();
        return new ApiErrors(bindingResult);


    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException(BusinessException businessException){
        return new ApiErrors(businessException);

    }


}
