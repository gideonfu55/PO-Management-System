package com.example.OJTPO.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.OJTPO.model.PurchaseOrder;
import com.example.OJTPO.service.PurchaseOrderService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "http://localhost:3000", "http://127.0.0.1:5555" })
public class POcontroller {

    @Autowired
    PurchaseOrderService purchaseOrderService;

    // For sales team to create PO:
    @PostMapping("/po/create")
    public ResponseEntity<PurchaseOrder> createPO(@RequestBody PurchaseOrder purchaseOrder) throws Exception {
      PurchaseOrder purchaseOrderResponse = purchaseOrderService.createPO(purchaseOrder);
      if (purchaseOrder != null) {
        return new ResponseEntity<>(purchaseOrderResponse, HttpStatus.CREATED);
      }
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // For sales team to forward PO to finance:
    @PostMapping("/po/forward")
    public ResponseEntity<PurchaseOrder> forwardPO(@RequestBody PurchaseOrder purchaseOrder) throws ExecutionException, InterruptedException {
      PurchaseOrder purchaseOrderResponse = purchaseOrderService.forwardPO(purchaseOrder);
      if (purchaseOrder != null) {
        return new ResponseEntity<>(purchaseOrderResponse, HttpStatus.OK);
      }
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // For finance team to get all billable POs:
    @GetMapping("/po/billable")
    public ResponseEntity<List<PurchaseOrder>> getBillablePOs() {
        List<PurchaseOrder> billablePOs = purchaseOrderService.getBillablePOs();
        if (!billablePOs.isEmpty()) {
            return new ResponseEntity<>(billablePOs, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // To populate fields of one PO in the update/delete form:
    @GetMapping("/po/{id}")
    public ResponseEntity<PurchaseOrder> getPOById(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
        PurchaseOrder purchaseOrder = purchaseOrderService.getPOById(id);
        if (purchaseOrder != null) {
            return new ResponseEntity<>(purchaseOrder, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // For finance team to update PO:
    @PatchMapping("/po/update")
    public ResponseEntity<PurchaseOrder> updatePO(@RequestBody PurchaseOrder purchaseOrder) throws ExecutionException, InterruptedException {
      PurchaseOrder purchaseOrderResponse = purchaseOrderService.updatePO(purchaseOrder);
      if (purchaseOrder != null) {
        return new ResponseEntity<>(purchaseOrderResponse, HttpStatus.OK);
      }
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // For finance team to delete PO:
    @DeleteMapping("/po/delete/{id}")
    public ResponseEntity<String> deletePO(@PathVariable("id") Long id) {
      String purchaseOrderResponse = purchaseOrderService.deletePO(id);
      if (purchaseOrderResponse != null) {
        return new ResponseEntity<String>("PO has been deleted successfully.", HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
