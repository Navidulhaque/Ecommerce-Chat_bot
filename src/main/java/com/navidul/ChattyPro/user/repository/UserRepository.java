package com.navidul.ChattyPro.user.repository;

import com.navidul.ChattyPro.user.entity.Status;
import com.navidul.ChattyPro.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {

    List<User> findAllByStatus(Status status);
}
