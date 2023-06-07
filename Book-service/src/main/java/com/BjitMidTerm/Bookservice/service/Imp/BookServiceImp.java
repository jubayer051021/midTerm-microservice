package com.BjitMidTerm.Bookservice.service.Imp;

import com.BjitMidTerm.Bookservice.client.InventoryClient;
import com.BjitMidTerm.Bookservice.exception.BookExceptionHandler;
import com.BjitMidTerm.Bookservice.exception.InventoryExceptionHandler;
import com.BjitMidTerm.Bookservice.model.*;
import com.BjitMidTerm.Bookservice.entity.BookEntity;
import com.BjitMidTerm.Bookservice.mapper.BookMappingModel;
import com.BjitMidTerm.Bookservice.repository.BookRepository;
import com.BjitMidTerm.Bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService {
    private final BookRepository bookRepository;
    @Autowired
    private InventoryClient inventoryClient;
    private final PlatformTransactionManager transactionManager;
    private final Logger logger = LoggerFactory.getLogger(BookServiceImp.class);

    @Override
    public BookResponseModel createBook(BookRequestModel bookRequestModel) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            BookEntity book = bookRepository.save(BookMappingModel.bookRequestDtoToBookEntity(bookRequestModel));
            Long bookId = book.getBookId();
            ResponseEntity<InventoryResModel> savedInventory = inventoryClient.updateInventory(bookId, BookMappingModel.bookRequestModelToInventoryModel(bookRequestModel, bookId));
            if (savedInventory.getStatusCode() != HttpStatus.CREATED) {
                throw new InventoryExceptionHandler("Inventory service is not available");
            }
            transactionManager.commit(status);
            return BookMappingModel.bookRequestModelToResponseModel(book, Objects.requireNonNull(savedInventory.getBody()));
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw new BookExceptionHandler("Book can not be created");
        }
    }

    @Override
    public BookResponseModel updateBookInventory(Long bookId, BookRequestModel bookRequestModel) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            BookEntity book = bookRepository.findByBookId(bookId);
            BookEntity newBook = BookEntity.builder()
                    .bookId(book.getBookId())
                    .authorName(bookRequestModel.getAuthorName())
                    .bookName(bookRequestModel.getBookName())
                    .genre(bookRequestModel.getGenre())
                    .build();
            BookEntity updateBook = bookRepository.save(newBook);
            Long bookID = book.getBookId();
            ResponseEntity<InventoryResModel> updateInventory = inventoryClient.updateInventory(bookID, BookMappingModel.bookRequestModelToInventoryModel(bookRequestModel, bookID));
            transactionManager.commit(status);
            return BookMappingModel.bookRequestModelToResponseModel(updateBook, Objects.requireNonNull(updateInventory.getBody()));
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw new BookExceptionHandler("Book can not updated");
        }
    }

    @Override
    public String deleteBook(Long bookId) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            BookEntity book = bookRepository.findByBookId(bookId);
            bookRepository.delete(book);
            inventoryClient.deleteInventory(bookId);
            transactionManager.commit(status);
            return "Successfully deleted";
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw new BookExceptionHandler("Book can not deleted");
        }
    }

    @Override
    public List<BookResponseModel> getAllBooks() {
        List<BookEntity> books = bookRepository.findAll();
        List<Long> ids = books.stream().map(BookEntity::getBookId).toList();
        List<InventoryResModel> inventoryList = inventoryClient.getAllInventory(ids);
        List<BookResponseModel> booksWithDetails = new ArrayList<>();
        for (int i = 0; i < inventoryList.size(); i++) {
            BookResponseModel book = BookMappingModel.bookRequestModelToResponseModel(books.get(i), inventoryList.get(i));
            booksWithDetails.add(book);
        }
        return booksWithDetails;
    }

    @Override
    public String buyBook(BookBuyModel bookBuyModel) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            InventoryResModel existingInventory = inventoryClient.getInventoryDetails(bookBuyModel.getInventoryId());
            if (existingInventory.getQuantity() >= bookBuyModel.getQuantity()) {
                existingInventory.setQuantity(existingInventory.getQuantity() - bookBuyModel.getQuantity());
                inventoryClient.updateInventory(bookBuyModel.getInventoryId(), existingInventory);
                transactionManager.commit(status);
                return "SuccessFully buy";
            }
            return "Can't buy";
        } catch (Exception ex) {
            transactionManager.rollback(status);
            return "Can't  buy";
        }
    }

    @Override
    public BookResponseModel getBookDetails(Long bookId) {
        try{
            InventoryResModel inventory = inventoryClient.getInventoryDetails(bookId);
            BookEntity bookEntity = bookRepository.findByBookId(bookId);
            return BookMappingModel.bookRequestModelToResponseModel(bookEntity, inventory);
        }catch (Exception ex){
            throw new InventoryExceptionHandler("Inventory service is off ");
        }
    }
}
