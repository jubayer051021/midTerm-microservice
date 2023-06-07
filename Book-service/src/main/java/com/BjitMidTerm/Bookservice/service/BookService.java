package com.BjitMidTerm.Bookservice.service;

import com.BjitMidTerm.Bookservice.model.BookBuyModel;
import com.BjitMidTerm.Bookservice.model.BookRequestModel;
import com.BjitMidTerm.Bookservice.model.BookResponseModel;

import java.util.List;

public interface BookService {
    BookResponseModel createBook(BookRequestModel bookRequestDto);

    BookResponseModel updateBookInventory(Long bookId, BookRequestModel bookRequestDto);

    String deleteBook(Long bookId);

    BookResponseModel getBookDetails(Long bookId);

    List<BookResponseModel> getAllBooks();

    String buyBook(BookBuyModel bookBuyModel);
}
