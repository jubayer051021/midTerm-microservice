package com.BjitMidTerm.Bookservice.client;

import com.BjitMidTerm.Bookservice.model.InventoryResModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.*;

import java.util.List;

@HttpExchange
public interface InventoryClient {
    @PutExchange("/book-inventory/update/{bookId}")
    public ResponseEntity<InventoryResModel> updateInventory(@PathVariable("bookId") Long bookId, @RequestBody InventoryResModel inventoryResModel);

    @DeleteExchange("/book-inventory/delete/{bookId}")
    public void deleteInventory(@PathVariable("bookId") Long bookId);

    @GetExchange("/book-inventory/{bookId}")
    public InventoryResModel getInventoryDetails(@PathVariable("bookId") Long id);
    @GetExchange("/book-inventory")
    public  List<InventoryResModel>  getAllInventory(@RequestParam List<Long> ids);
}
