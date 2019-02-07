package com.sep.scientificcentre.scientificcentre.services;

import com.sep.scientificcentre.scientificcentre.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    
    User create(User user);
    List<User> getAll();
    User getOne(Long id);
    User getByUsername(String username);
    User login(String username, String password);
}
