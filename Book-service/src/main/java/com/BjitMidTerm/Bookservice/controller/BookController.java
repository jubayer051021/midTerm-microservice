package com.BjitMidTerm.Bookservice.controller;

import com.BjitMidTerm.Bookservice.model.BookBuyModel;
import com.BjitMidTerm.Bookservice.model.BookRequestModel;
import com.BjitMidTerm.Bookservice.model.BookResponseModel;
import com.BjitMidTerm.Bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/create")
    public BookResponseModel createBook(@RequestBody BookRequestModel bookRequestModel) {
        return bookService.createBook(bookRequestModel);
    }

    @PutMapping("/update/{bookId}")
    public BookResponseModel updateBookInventory(@PathVariable("bookId") Long bookId, @RequestBody BookRequestModel bookRequestModel){
        return bookService.updateBookInventory(bookId,bookRequestModel);
    }
    @DeleteMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Long bookId){
        return bookService.deleteBook(bookId);
    }
    @GetMapping("/allBook")
    public List<BookResponseModel> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public BookResponseModel getBookDetails(@PathVariable Long bookId){
        return bookService.getBookDetails(bookId);
    }

    @PutMapping("/buy")
    public String buyBook(@RequestBody BookBuyModel bookBuyModel){
        return bookService.buyBook(bookBuyModel);
    }
}
