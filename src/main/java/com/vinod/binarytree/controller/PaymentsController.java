package com.vinod.binarytree.controller;

import com.vinod.binarytree.model.cheque;
import com.vinod.binarytree.services.CheckService;
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
@RequestMapping(value = "/binary/payments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class PaymentsController {

    @Autowired
    private CheckService checkService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<cheque>> getAll() {
        return new ResponseEntity(checkService.retriveAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<cheque>> getCheck(@PathVariable(value = "id")  String userId) {
        return new ResponseEntity(checkService.retriveCheck(userId), HttpStatus.OK);
    }

}
