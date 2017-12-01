package com.vinod.binarytree.controller;

import com.vinod.binarytree.model.Payment;
import com.vinod.binarytree.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/binary/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class PaymentsController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Payment>> getAll() {
        return new ResponseEntity(paymentService.retriveAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Payment>> getCheck(@PathVariable(value = "id") String userId) {
        return new ResponseEntity(paymentService.retriveCheck(userId), HttpStatus.OK);
    }

}
