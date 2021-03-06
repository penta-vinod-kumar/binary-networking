package com.vinod.binarytree.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Slf4j
public class User {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String parent;
    private String leftChild;
    private String rightChild;
    private long noOfLeftChilds;
    private long noOfLeftChildsSatisfiesCondition;
    private long noOfRightChilds;
    private long noOfRightChildsSatisfiesCondition;
    private boolean parentsLeftChild;
    private boolean firstChequeReceived;
    private Boolean firstChequeReceivedFromLeftChild;
    private int noOfCheque;
    private boolean isParentUpdated;
}
