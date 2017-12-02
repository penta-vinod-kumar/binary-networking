package com.vinod.binarytree.taskexecutor;

import com.vinod.binarytree.model.Payment;
import com.vinod.binarytree.model.User;
import com.vinod.binarytree.repository.PaymentRepository;
import com.vinod.binarytree.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentTaskExecutor {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Async
    public void updateParentAndProcessPayment(User user) {
        if (user.getParent() != null) {
            User parentUser = userRepository.findOne(user.getParent());
            if (user.isParentsLeftChild()) {
                parentUser.setLeftChild(user.getId());
                parentUser.setNoOfLeftChilds(parentUser.getNoOfLeftChilds() + 1);
                processPayment(parentUser, true);

            } else {
                parentUser.setRightChild(user.getId());
                parentUser.setNoOfRightChilds(parentUser.getNoOfRightChilds() + 1);
                processPayment(parentUser, false);
            }
            userRepository.save(parentUser);
            updateParentAndProcessPayment(parentUser);
        }
    }

    private void processPayment(User user, boolean isLeftChild) {
        //long noOfLeftChildsExcludingFirst3, noOfRightChildsExcludingFirst3;
        long noOfPairs;
        if (!user.isFirstChequeReceived()) {
            if (isLeftChild && user.getNoOfLeftChilds() == 2 && user.getNoOfRightChilds() == 1) {
                createCheque(user);
                user.setFirstChequeReceived(true);
                user.setFirstChequeReceivedFromLeftChild(true);

            } else if (user.getNoOfLeftChilds() == 1 && user.getNoOfRightChilds() == 2) {
                createCheque(user);
                user.setFirstChequeReceived(true);
                user.setFirstChequeReceivedFromLeftChild(false);

            }
        } else {
            if (user.getNoOfLeftChildsSatisfiesCondition() > user.getNoOfRightChildsSatisfiesCondition()) {
                noOfPairs = user.getNoOfRightChildsSatisfiesCondition();
            } else {
                noOfPairs = user.getNoOfLeftChildsSatisfiesCondition();
            }
            if (noOfPairs > user.getNoOfCheque() - 1) {
                createCheque(user);
            }
        }

        if (user.getParent() != null && user.getLeftChild() != null && user.getRightChild() != null)
            updateParent(user);
    }

    private void updateParent(User user) {
        User parentUser = userRepository.findOne(user.getParent());
        if (user.isParentsLeftChild()) {
            parentUser.setNoOfLeftChildsSatisfiesCondition(parentUser.getNoOfLeftChildsSatisfiesCondition() + 1);
        } else {
            parentUser.setNoOfRightChildsSatisfiesCondition(parentUser.getNoOfRightChildsSatisfiesCondition() + 1);
        }
        userRepository.save(parentUser);
        if (parentUser.getParent() != null) {
            updateParent(parentUser);
        }
    }

    private void createCheque(User parentUser) {
        Payment Payment = new Payment();
        Payment.setCreatedOn(new Date());
        Payment.setUserId(parentUser.getId());
        Payment.setNumber(parentUser.getNoOfCheque() + 1);
        paymentRepository.save(Payment);
        parentUser.setNoOfCheque(parentUser.getNoOfCheque() + 1);
    }
}
