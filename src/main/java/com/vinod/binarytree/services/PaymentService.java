package com.vinod.binarytree.services;

import com.vinod.binarytree.model.Payment;
import com.vinod.binarytree.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> retriveAll() {
        return paymentRepository.findAll();
    }

    public List<Payment> retriveCheck(String userId) {
        return paymentRepository.findAllByUserId(userId);
    }
}
