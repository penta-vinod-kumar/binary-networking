package com.vinod.binarytree.repository;

import com.vinod.binarytree.model.cheque;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChequeRepository extends MongoRepository<cheque, String> {

    List<cheque> findAllByUserId(String userId);
}
