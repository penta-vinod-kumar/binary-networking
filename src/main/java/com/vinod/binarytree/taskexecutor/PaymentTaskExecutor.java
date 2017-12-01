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

    private void processPayment(User parentUser, boolean isLeftChild) {
        long noOfLeftChildsExcludingFirst3, noOfRightChildsExcludingFirst3;
        long noOfPairs;
        if (!parentUser.isFirstChequeReceived()) {
            if (isLeftChild && parentUser.getNoOfLeftChilds() == 2 && parentUser.getNoOfRightChilds() == 1) {
                createCheque(parentUser);
                parentUser.setFirstChequeReceived(true);
                parentUser.setFirstChequeReceivedFromLeftChild(true);
            } else if (parentUser.getNoOfLeftChilds() == 1 && parentUser.getNoOfRightChilds() == 2) {
                createCheque(parentUser);
                parentUser.setFirstChequeReceived(true);
                parentUser.setFirstChequeReceivedFromLeftChild(false);
            }
        } else if (parentUser.getFirstChequeReceivedFromLeftChild() != null) {
            if (parentUser.getFirstChequeReceivedFromLeftChild()) {
                noOfLeftChildsExcludingFirst3 = parentUser.getNoOfLeftChilds() - 2;
                noOfRightChildsExcludingFirst3 = parentUser.getNoOfRightChilds() - 1;
                // need to check with stupid business about second check
            } else {
                noOfLeftChildsExcludingFirst3 = parentUser.getNoOfLeftChilds() - 2;
                noOfRightChildsExcludingFirst3 = parentUser.getNoOfRightChilds() - 2;
            }
            //if (parentUser.getNoOfRightChilds() > parentUser.getNoOfLeftChilds()) {
            if (noOfRightChildsExcludingFirst3 > noOfLeftChildsExcludingFirst3) {
                noOfPairs = parentUser.getNoOfLeftChilds();
            } else {
                noOfPairs = parentUser.getNoOfRightChilds();
            }
            if (noOfPairs > parentUser.getNoOfCheque()) {
                createCheque(parentUser);
            }

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
