package com.BjitMidTerm.Inventoryservice.service.Imp;

import com.BjitMidTerm.Inventoryservice.entity.InventoryEntity;
import com.BjitMidTerm.Inventoryservice.exception.InventoryNotFoundException;
import com.BjitMidTerm.Inventoryservice.mapper.InventoryMapperModel;
import com.BjitMidTerm.Inventoryservice.model.InventoryReqModel;
import com.BjitMidTerm.Inventoryservice.model.InventoryResModel;
import com.BjitMidTerm.Inventoryservice.repository.InventoryRepository;
import com.BjitMidTerm.Inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImp implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public ResponseEntity<InventoryResModel> updateInventory(long bookId,InventoryReqModel inventoryReqModel){
        InventoryEntity existInventory=inventoryRepository.findByInventoryId(bookId);
        InventoryEntity updateInventory = inventoryRepository.save(InventoryMapperModel.inventoryReqModelToEntity(inventoryReqModel));
        InventoryResModel responseInventory= InventoryMapperModel.inventoryEntityToResModel(updateInventory);
        if (existInventory==null){
            return new ResponseEntity<>(responseInventory,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(responseInventory,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteInventory(Long bookId) {
        InventoryEntity inventory=inventoryRepository.findByInventoryId(bookId);
        if (inventory!=null){
            inventoryRepository.delete(inventory);
            return new ResponseEntity<>("SuccessFull Deleted the Inventory",HttpStatus.OK);
        }
        throw new InventoryNotFoundException("Please enter a valid Id then try to delete");
    }

    @Override
    public ResponseEntity<InventoryResModel> getInventoryDetails(Long id) {
        InventoryEntity inventory=inventoryRepository.findByInventoryId(id);
        if (inventory!=null){
            return new ResponseEntity<>(InventoryMapperModel.inventoryEntityToResModel(inventory),HttpStatus.OK);
        }
        throw new InventoryNotFoundException("Please enter a valid Id then try to fetch the details");
    }
    @Override
    public  ResponseEntity<Object> getAllInventory(List<Long> ids) {
        List<InventoryResModel> inventoryLists=new ArrayList<>();
        for (Long id:ids){
            InventoryEntity inventoryEntity=inventoryRepository.findByInventoryId(id);
            inventoryLists.add(InventoryMapperModel.inventoryEntityToResModel(inventoryEntity));
        }
       return new ResponseEntity<>(inventoryLists,HttpStatus.OK);
    }
}
