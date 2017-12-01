package com.vinod.binarytree.services;

import com.vinod.binarytree.model.User;
import com.vinod.binarytree.repository.UserRepository;
import com.vinod.binarytree.taskexecutor.PaymentTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentTaskExecutor paymentTaskExecutor;

    public User saveUser(User user) {
        user = userRepository.save(user);
        paymentTaskExecutor.updateParentAndProcessPayment(user);
        return userRepository.save(user);
    }

    public User getUserByName(String name) {
        return userRepository.findByFirstName(name);
    }
}
