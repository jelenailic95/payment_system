package com.sep.bank.bankservice.service;

import com.sep.bank.bankservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User create(User user);
}
