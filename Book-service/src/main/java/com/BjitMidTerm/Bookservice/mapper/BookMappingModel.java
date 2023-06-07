package com.BjitMidTerm.Bookservice.mapper;

import com.BjitMidTerm.Bookservice.model.BookRequestModel;
import com.BjitMidTerm.Bookservice.model.BookResponseModel;
import com.BjitMidTerm.Bookservice.model.InventoryResModel;
import com.BjitMidTerm.Bookservice.entity.BookEntity;

public class BookMappingModel {
    public static BookEntity bookRequestDtoToBookEntity(BookRequestModel bookRequestModel){
        return BookEntity.builder()
                .bookName(bookRequestModel.getBookName())
                .authorName(bookRequestModel.getAuthorName())
                .genre(bookRequestModel.getGenre())
                .build();
    }
    public static InventoryResModel bookRequestModelToInventoryModel(BookRequestModel bookRequestModel, Long bookId){
        return InventoryResModel.builder()
                .inventoryId(bookId)
                .price(bookRequestModel.getPrice())
                .quantity(bookRequestModel.getQuantity())
                .build();
    }
    public static BookResponseModel bookRequestModelToResponseModel(BookEntity bookEntity, InventoryResModel inventoryResModel){
        return BookResponseModel.builder()
                .bookName(bookEntity.getBookName())
                .genre(bookEntity.getGenre())
                .authorName(bookEntity.getAuthorName())
                .bookId(bookEntity.getBookId())
                .price(inventoryResModel.getPrice())
                .quantity(inventoryResModel.getQuantity())
                .build();
    }
}
