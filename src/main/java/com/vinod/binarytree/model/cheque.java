package com.vinod.binarytree.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@Slf4j
public class cheque {
    @Id
    private String id;

    private String userId;
    private int number;
    private Date createdOn;
}
