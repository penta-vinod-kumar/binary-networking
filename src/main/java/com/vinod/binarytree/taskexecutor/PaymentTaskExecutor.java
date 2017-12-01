package com.vinod.binarytree.taskexecutor;

import com.vinod.binarytree.model.User;
import com.vinod.binarytree.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PaymentTaskExecutor {

    @Autowired
    private UserRepository userRepository;

    @Async
    public void updateParentAndProcessPayment(User user) {
        if (user.getParent() != null) {
            User parentUser = userRepository.findOne(user.getParent());
            if (user.isParentsLeftChild()) {
                parentUser.setLeftChild(user.getId());
                parentUser.setNoOfLeftChilds(parentUser.getNoOfLeftChilds() + 1);
                //if(parentUser.)
            } else {
                parentUser.setRightChild(user.getId());
                parentUser.setNoOfRightChilds(parentUser.getNoOfRightChilds() + 1);
            }
            updateParentAndProcessPayment(parentUser);
        }
    }
}
