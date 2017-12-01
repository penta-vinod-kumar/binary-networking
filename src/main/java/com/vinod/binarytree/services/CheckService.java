package com.vinod.binarytree.services;

import com.vinod.binarytree.model.cheque;
import com.vinod.binarytree.repository.ChequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckService {

    @Autowired
    private ChequeRepository chequeRepository;

    public List<cheque> retriveAll() {
        return chequeRepository.findAll();
    }

    public List<cheque> retriveCheck(String userId) {
        return chequeRepository.findAllByUserId(userId);
    }
}
