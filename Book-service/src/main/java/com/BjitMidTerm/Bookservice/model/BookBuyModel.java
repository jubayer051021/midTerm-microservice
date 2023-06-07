package com.BjitMidTerm.Bookservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookBuyModel {
    private Long inventoryId;
    private Integer quantity;
}
